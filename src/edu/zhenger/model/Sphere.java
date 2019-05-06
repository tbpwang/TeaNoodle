/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.globes.EllipsoidalGlobe;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/10
 * @Deprecated replaced by Geometry.Sphere
 */

@Deprecated
public final class Sphere extends EllipsoidalGlobe
{
    private static Sphere ourInstance = new Sphere();

    public static Sphere getInstance()
    {
        return ourInstance;
    }

    private Sphere()
    {
        // 长半轴a＝6378137(m), Earth.WGS84_EQUATORIAL_RADIUS
        // 与CGCS2000或WGS84相同表面积的球半径近似为：R2= 6371007.18092(m)
//        super(Earth.WGS84_EQUATORIAL_RADIUS, Earth.WGS84_EQUATORIAL_RADIUS, 0.0, null, new Earth().getCenter());
        super(6371007.18092, 6371007.18092, 0.0, null, Vec4.ZERO);

    }

    @Override
    public String toString()
    {
        return "Sphere{" +
            "equatorialRadius=" + equatorialRadius +
            ", polarRadius=" + polarRadius +
            ", es=" + es +
            ", egm96=" + egm96 +
            '}';
    }
}
