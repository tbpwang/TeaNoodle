/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel;

import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.*;

/**
 * @author Zheng WANG
 * @create 2019/4/28 21:58
 * @description superclass class
 * @parameter  return Sphere(Geometry) and Globe(Globe)
 */
public final class DGGS
{
    private static Vec4 center = Vec4.ZERO;
    // 长半轴a＝6378137(m), Earth.WGS84_EQUATORIAL_RADIUS
    // 与CGCS2000或WGS84相同表面积的球半径近似为：R2= 6371007.18092(m)
    private static double radius = 6371007.18092;

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
}
