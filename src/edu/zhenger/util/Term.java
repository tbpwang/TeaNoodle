/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.util;

import edu.zhenger.model.*;
import edu.zhenger.model.Sphere;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.Logging;


import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/10
 */
public final class Term //implements DGGS
{
//    private static Term ourInstance = new Term();
//
//    public static Term getInstance()
//    {
//        return ourInstance;
//    }
//
//    private Term()
//    {
//    }


    public static Globe getUnitSphere(){return UnitSphere.getInstance();    }


    public static Globe getSphere()
    {
        return Sphere.getInstance();
    }


    public static Vec4 fromLatLon(LatLon latLon)
    {
        //return getSphere().computePointFromLocation(latLon);
        double epsilon = 1e-8;
//        Vec4 vec4 = getSphere().computePointFromLocation(latLon);
        Vec4 vec4 = getUnitSphere().computePointFromLocation(latLon);
        double x, y, z;
        x = Math.abs(vec4.x) <= epsilon ? 0.0 : vec4.x;
        y = Math.abs(vec4.y) <= epsilon ? 0.0 : vec4.y;
        z = Math.abs(vec4.z) <= epsilon ? 0.0 : vec4.z;

        return new Vec4(x, y, z);
    }

    public static LatLon fromVec4(Vec4 vec4)
    {
        return new LatLon(getSphere().computePositionFromPoint(vec4));
    }

    public static LatLon approximate(LatLon source, LatLon target)
    {
        if (source == null || target == null)
        {
            String message = Logging.getMessage("valueMissing.PointMissing");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        double sLat, sLon, tLat, tLon;
        sLat = source.latitude.degrees;
        sLon = source.longitude.degrees;
        tLat = target.latitude.degrees;
        tLon = target.longitude.degrees;

        double sigma = 1e-6;
        sLat = (sLat - tLat) / tLat < sigma && (sLat - tLat) / tLat > -sigma ? tLat : sLat;
        sLon = (sLon - tLon) / tLon < sigma && (sLon - tLon) / tLon > -sigma ? tLon : sLon;
        return sLat == tLat && sLon == tLon ? target : source;
    }

    public static LatLon approximate(LatLon latLon)
    {
        double lat, lon, sigma;
        sigma = 1E-6;
        lat = latLon.getLatitude().degrees;
        lon = latLon.getLongitude().degrees;

        // Approximate to 0
        //lat = lat > 0 ? lat < sigma ? 0 : lat : -lat < sigma ? 0 : lat;
        lat = lat < sigma && lat > -sigma ? 0.0 : lat;
        lon = lon < sigma && lon > -sigma ? 0.0 : lon;

        // Approximate to 90
        lat = ((90.0 - lat) < sigma && (90.0 - lat) > -sigma) ? 90.0 : lat;
        lat = ((-90.0 - lat) < sigma && (-90.0 - lat) > -sigma) ? -90.0 : lat;
        lon = ((180.0 - lon) < sigma && (180.0 - lon) > -sigma) ? 180.0 : lon;

        return LatLon.fromDegrees(lat, lon);
    }

    public static double radian2Degree(double radian)
    {
        return radian * 180.0 / Math.PI;
    }

    public static double degree2Radian(double degree)
    {
        return degree * Math.PI / 180.0;
    }

    public static Path defaultLine(Position posA, Position posB)
    {
        Path line = new Path(posA, posB);
        line.setAttributes(defaultAttribute(true));
        line.setPathType(AVKey.GREAT_CIRCLE);
        line.setFollowTerrain(true);
        line.setTerrainConformance(1);
        line.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);

        return line;
    }

    public static ShapeAttributes defaultAttribute(boolean isSolid)
    {
        ShapeAttributes attributes = new BasicShapeAttributes();
        attributes.setDrawOutline(true);
//        attributes.setOutlineMaterial(new Material(new Color(0,200,0)));
        attributes.setOutlineMaterial(new Material(new Color(255, 255, 255)));
        attributes.setOutlineWidth(1.0);
//        attributes.setOutlineWidth(2.5);



        //点画线
        if (!isSolid)
        {
            attributes.setOutlineStippleFactor(1);
            attributes.setOutlineStipplePattern((short) 100);
        }

        return attributes;
    }


    public static IllegalArgumentException nullError(String info)
    {
        String message = Logging.getMessage(info);
        Logging.logger().severe(message);
        return new IllegalArgumentException(message);
    }
    public static List<Position> positionList(LatLon top, LatLon left, LatLon right)
    {
        List<Position> list = new ArrayList<>();
        list.add(new Position(top, 0));
        list.add(new Position(left, 0));
        list.add(new Position(right, 0));
        return list;
    }

}
