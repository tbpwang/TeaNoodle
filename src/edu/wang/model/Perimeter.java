/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.wang.model;

import edu.wang.io.Cons;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.util.Logging;

/**
 * @author Zheng WANG
 * @create 2019/9/22
 * @description
 */
public class Perimeter
{
    public static double getPlanePerimeter(Vec4 a, Vec4 b, Vec4 c)
    {
        if (a == null || b == null || c == null)
        {
            String msg = Logging.getMessage("顶点有空值");
            Logging.logger().severe(msg);
            throw new IllegalArgumentException(msg);
        }

        Vec4 p1 = a.normalize3();
        Vec4 p2 = b.normalize3();
        Vec4 p3 = c.normalize3();

        return (p1.distanceTo3(p2) + p1.distanceTo3(p3) + p2.distanceTo3(p3)) * Cons.radius;
    }

    public static double getPlanePerimeter(Triangle triangle)
    {
        if (triangle == null)
        {
            String msg = Logging.getMessage("NullValue.三角形是空值");
            Logging.logger().severe(msg);
            throw new IllegalArgumentException(msg);
        }
        return getPlanePerimeter(triangle.getA(), triangle.getB(), triangle.getC());
    }

    public static double getSpherePerimeter(LatLon a, LatLon b, LatLon c)
    {
        if (a == null || b == null || c == null)
        {
            String msg = Logging.getMessage("顶点有空值");
            Logging.logger().severe(msg);
            throw new IllegalArgumentException(msg);
        }
        return Cons.radius * (LatLon.greatCircleDistance(a, b).radians + LatLon.greatCircleDistance(a, c).radians
            + LatLon.greatCircleDistance(b, c).radians);
    }
}
