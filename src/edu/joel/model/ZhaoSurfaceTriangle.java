/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.model;

import edu.joel.SurfaceTriangle;
import edu.joel.*;
import edu.joel.io.Constant;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.Path;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/5/8 16:23
 * @description
 * @parameter
 */
public class ZhaoSurfaceTriangle extends SurfaceTriangle//MiddleArcTriangle
{
    //    private boolean isUp = true;
    private Geocode geocode;

    public ZhaoSurfaceTriangle(LatLon top, LatLon left, LatLon right, Geocode geocode)
    {
        super(top, left, right, geocode.getID());
        this.geocode = geocode;
    }

    public static ZhaoSurfaceTriangle cast (SurfaceTriangle triangle)
    {
        if (triangle == null)
        {
            return null;
        }
        return new ZhaoSurfaceTriangle(triangle.getTop(),triangle.getLeft(),triangle.getRight(),triangle.getGeocode());
    }
    public Geocode getGeocode()
    {
        return geocode;
    }

    public double computeArea()
    {
        int interval = 100;
        double minLon = getLeft().getLongitude().degrees;
        double maxLon = getRight().getLongitude().degrees;
        double lat = getLeft().getLatitude().degrees;
        double delta = (maxLon - minLon) / interval;
        double epslon = 0.01;
        while (delta < epslon)
        {
            interval = interval / 10;
            delta = (maxLon - minLon) / interval;
        }
        double from = minLon;
        double to = minLon + delta;
        double area = 0.0;
        for (int i = 0; i < interval; i++)
        {
            LatLon left = LatLon.fromDegrees(lat, from);
            LatLon right = LatLon.fromDegrees(lat, to);
            from = from + delta;
            if (i == interval - 1)
            {
                to = maxLon;
            }
            else
            {
                to += delta;
            }
            SurfaceTriangle triangle = new SurfaceTriangle(getTop(), left, right, "");
            area += triangle.getUnitArea();
        }
        double radius = Constant.getGlobe().getRadius();
        return area * radius * radius;
    }

    @Override
    public Path[] renderPath()
    {
        List<Position> positions = new ArrayList<>();
        // 两边为大圆弧 AVKey.GREAT_CIRCLE
        // 底边为纬线即恒向线 AVKey.RHUMB_LINE
        Path path1, path2;

        positions.add(new Position(getGeoVertices().get(2), 0));
        positions.add(new Position(getGeoVertices().get(0), 0));
        positions.add(new Position(getGeoVertices().get(1), 0));
        path1 = new Path(positions);
        path1.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        path1.setFollowTerrain(true);
        path1.setPathType(AVKey.GREAT_CIRCLE);
        path1.setAttributes(Constant.defaultPathAttribute());

        path2 = new Path(new Position(getGeoVertices().get(1), 0), new Position(getGeoVertices().get(2), 0));
        path2.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        path2.setFollowTerrain(true);
        path2.setPathType(AVKey.RHUMB_LINE);
        path2.setAttributes(Constant.defaultPathAttribute());

        return new Path[] {path1, path2};
    }

    @Override
    public ZhaoSurfaceTriangle[] refine()
    {
        // 两边是大圆弧 GreatCircle
        // 底边沿着纬线 Rhumb
//        LatLon a = LatLon.fromDegrees(getGeoVertices().get(1).latitude.degrees,
//            Angle.average(getGeoVertices().get(1).longitude, getGeoVertices().get(2).longitude).degrees);
        LatLon a = LatLon.interpolateRhumb(0.5, getGeoVertices().get(1), getGeoVertices().get(2));
        LatLon b = LatLon.interpolateGreatCircle(0.5, getGeoVertices().get(0), getGeoVertices().get(2));
        LatLon c = LatLon.interpolateGreatCircle(0.5, getGeoVertices().get(1), getGeoVertices().get(0));
        String ID = this.getGeocode().getID();
        ZhaoSurfaceTriangle[] subCells = new ZhaoSurfaceTriangle[4];
        // 中心对齐，整体全等
        // 关于过顶点的中心线对称
//        subCells[0] = new ZhaoQTM(a, b, c, ID + "0");
//        subCells[1] = new ZhaoQTM(getGeoVertices().get(0), c, b, ID + "1");
//        subCells[2] = new ZhaoQTM(getGeoVertices().get(1), a, c, ID + "2");
//        subCells[3] = new ZhaoQTM(getGeoVertices().get(2), a, b, ID + "3");
//        // Goodchild 编码法
        Geocode[] geocodes = this.geocode.binaryRefine();
        subCells[0] = new ZhaoSurfaceTriangle(a, c, b, geocodes[0]);
        subCells[1] = new ZhaoSurfaceTriangle(getGeoVertices().get(0), c, b, geocodes[1]);
        subCells[2] = new ZhaoSurfaceTriangle(c, getGeoVertices().get(1), a, geocodes[2]);
        subCells[3] = new ZhaoSurfaceTriangle(b, a, getGeoVertices().get(2), geocodes[3]);
        return subCells;
    }
}
