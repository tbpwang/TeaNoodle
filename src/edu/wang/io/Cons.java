/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.wang.io;

import edu.wang.Geocode;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.Logging;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Zheng WANG
 * @create 2019/4/28 21:58
 * @description superclass class
 * @parameter return Sphere(Geometry) and Globe(Globe)
 */
public final class Cons
{
    public final static double epsilon = 1E-8;
    private static Vec4 center = Vec4.ZERO;
    // 长半轴a＝6378137(m), Earth.WGS84_EQUATORIAL_RADIUS
    // 与CGCS2000或WGS84相同表面积的球半径近似为：R2= 6371007.2(m)
//    public final static double radius = 6371007.2;//6371.007km
    public final static double radius = 6371.0072;

    public static int NEIGHBOR_TYPE_EDGE = 1;

    public static int NEIGHBOR_TYPE_VERTEX = 0;

    public static Sphere getSphere()
    {
        return new Sphere(center, radius);
    }

    public static Sphere getUnitSphere()
    {
        return Sphere.UNIT_SPHERE;
    }

    public static Globe getGlobe()
    {
        return new EllipsoidalGlobe(radius, radius, 0.0, null, center);
    }

    public static Globe getUnitGlobe()
    {
        return new EllipsoidalGlobe(1.0, 1.0, 0.0, null, center);
    }

    public static Globe getEarth()
    {
        return new Earth();
    }

    public static Globe getEarthFlat()
    {
        return new EarthFlat();
    }

    public static ShapeAttributes defaultPolygonAttribute()
    {
        ShapeAttributes attributes = new BasicShapeAttributes();

        // for Path
        attributes.setOutlineMaterial(new Material(new Color(20, 120, 20)));

        // for Polygon
        attributes.setInteriorMaterial(new Material(new Color(20, 100, 20)));
        attributes.setInteriorOpacity(0.008);

        return attributes;
    }

    public static ShapeAttributes defaultPathAttribute()
    {
        ShapeAttributes attributes = new BasicShapeAttributes();
        // for Path
        attributes.setOutlineMaterial(new Material(new Color(20, 150, 20)));
        attributes.setOutlineWidth(2.0);

        return attributes;
    }
    public static ShapeAttributes defaultPathAttribute(Color color)
    {
        ShapeAttributes attributes = new BasicShapeAttributes();
        // for Path
        attributes.setOutlineMaterial(new Material(color));
        attributes.setOutlineWidth(2.0);

        return attributes;
    }
    public static double check(double value)
    {
        return Math.abs(value) <= epsilon ? 0.0 : value;
    }

    public static Vec4 check(double x, double y, double z)
    {
        x = Math.abs(x) <= epsilon ? 0.0 : x;
        y = Math.abs(y) <= epsilon ? 0.0 : y;
        z = Math.abs(z) <= epsilon ? 0.0 : z;
        return new Vec4(x, y, z);
    }

    public static Vec4 check(Vec4 vec4)
    {
        double x, y, z;
        x = vec4.getX();
        y = vec4.getY();
        z = vec4.getZ();
        boolean changed = false;
        if (Math.abs(x) <= epsilon)
        {
            x = 0.0;
            changed = true;
        }
        if (Math.abs(y) <= epsilon)
        {
            y = 0.0;
            changed = true;
        }
        if (Math.abs(z) <= epsilon)
        {
            z = 0.0;
            changed = true;
        }
        return changed ? new Vec4(x, y, z) : vec4;
    }
    public static LatLon vec4ToLatLon(Vec4 vec4)
    {
        double phi = Math.asin(vec4.getZ());
        double lambda;
        lambda = Math.atan2(vec4.getY(), vec4.getX());
//        if (vec4.getX() != 0.0)
//        {
//            lambda = Math.atan2(vec4.getY(), vec4.getX());
//        }
//        else
//        {
//            lambda = vec4.getY() >= 0 ? Math.PI / 2.0 : -Math.PI / 2.0;
//        }

        return LatLon.fromRadians(phi, lambda);
    }

    public static Vec4 latLonToVec4(LatLon latLon)
    {
        double x, y, z;
        double phi = latLon.getLatitude().radians;
        double theta = latLon.getLongitude().radians;
        x = Math.cos(phi) * Math.cos(theta);
        y = Math.cos(phi) * Math.sin(theta);
        z = Math.sin(phi);
        return check(x, y, z);
    }

    public static Vec4 resultant(List<LatLon> points)
    {
        double sumX = 0.0;
        double sumY = 0.0;
        double sumZ = 0.0;
        Vec4 temp;
        for (LatLon p : points)
        {
            temp = latLonToVec4(p);
            sumX += temp.getX();
            sumY += temp.getY();
            sumZ += temp.getZ();
        }
        return check(sumX, sumY, sumZ);
    }

    public static Vec4 sphericalMean(List<LatLon> points)
    {
        // 球面平均位置
        Vec4 resultant = resultant(points);
        double resultantLength = resultant.getLength3();
//        double x, y, z;
//        x = resultant.getX() / resultantLength;
//        y = resultant.getY() / resultantLength;
//        z = resultant.getZ() / resultantLength;
        return check(resultant.getX() / resultantLength, resultant.getY() / resultantLength,
            resultant.getZ() / resultantLength);
    }

    public static Vec4 massCenter(List<LatLon> points)
    {
        // 质心
        double resultantLength = resultant(points).getLength3();
        int pointNumber = points.size() == 0 ? 1 : points.size();
        double r = resultantLength / pointNumber;
        Vec4 mean = sphericalMean(points);
        return check(r * mean.getX(), r * mean.getY(), r * mean.getZ());
    }

    public static List<LatLon> changeTriangleVertexes(Triangle triangle)
    {
        if (triangle == null)
        {
            String msg = Logging.getMessage("NullValue.三角形为空");
            Logging.logger().severe(msg);
            throw new IllegalArgumentException(msg);
        }
        LatLon top,left,right;
        top = vec4ToLatLon(triangle.getA());
        left = vec4ToLatLon(triangle.getB());
        right = vec4ToLatLon(triangle.getC());
        List<LatLon> vertexes = new ArrayList<>();
        vertexes.add(top);
        vertexes.add(left);
        vertexes.add(right);
        return vertexes;
    }
    public static int sortByRow(Geocode code1,Geocode code2)
    {

            int diff1 = code1.getRow() - code2.getRow();
            int diff2 = code1.getColumn() - code2.getColumn();
            if (diff1 == 0)
            {
                return diff2;
            }
            return diff1;
    }

    private static void test()
    {
    }
}
