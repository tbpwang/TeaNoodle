/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.model;

import edu.joel.io.Constant;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.util.Logging;

/**
 * @author Zheng WANG
 * @create 2019/9/22
 * @description
 */
public class Area
{
    public static double getPlaneArea(Vec4 a, Vec4 b, Vec4 c)
    {
        if (a == null || b == null || c == null)
        {
            String msg = Logging.getMessage("NullValue.空值");
            Logging.logger().severe(msg);
            throw new IllegalArgumentException(msg);
        }

        Vec4 p1 = a.normalize3();
        Vec4 p2 = b.normalize3();
        Vec4 p3 = c.normalize3();

        double lab = p1.distanceTo3(p2);
        double lac = p1.distanceTo3(p3);
        double lbc = p2.distanceTo3(p3);
        double half = (lab + lac + lbc) / 2.0;

        return Math.sqrt(half * (half - lab) * (half - lac) * (half - lbc)) * Math.pow(Constant.radius, 2);
    }

    public static double getPlaneArea(Triangle triangle)
    {
        if (triangle == null)
        {
            String msg = Logging.getMessage("NullValue.空值");
            Logging.logger().severe(msg);
            throw new IllegalArgumentException(msg);
        }
//        Vec4 a, b, c;
//        a = triangle.getA();
//        b = triangle.getB();
//        c = triangle.getC();
        return getPlaneArea(triangle.getA(), triangle.getB(), triangle.getC());
    }

    public static double getSphereArea(LatLon a, LatLon b, LatLon c)
    {
        if (a == null || b == null || c == null)
        {
            String msg = Logging.getMessage("NullValue.空值");
            Logging.logger().severe(msg);
            throw new IllegalArgumentException(msg);
        }
        double p, la, lb, lc;
        double top, left, right;
        la = LatLon.greatCircleDistance(b, c).radians;
        lc = LatLon.greatCircleDistance(a, b).radians;
        lb = LatLon.greatCircleDistance(a, c).radians;
        p = (la + lb + lc) / 2.0;
        top = 2 * Math.asin(Math.sqrt(Math.sin(p - lc) * Math.sin(p - lb) / Math.sin(lc) / Math.sin(lb)));
        left = 2 * Math.asin(Math.sqrt(Math.sin(p - lc) * Math.sin(p - la) / Math.sin(lc) / Math.sin(la)));
        right = 2 * Math.asin(Math.sqrt(Math.sin(p - la) * Math.sin(p - lb) / Math.sin(la) / Math.sin(lb)));
        return (top + left + right - Math.PI) * Math.pow(Constant.radius, 2);
    }
}
