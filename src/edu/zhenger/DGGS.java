/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger;

import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.*;

import java.io.Serializable;

//public interface DGGS extends Serializable
public abstract class DGGS implements Serializable
{
    private Vec4 center = Vec4.ZERO;
    // 长半轴a＝6378137(m), Earth.WGS84_EQUATORIAL_RADIUS
    // 与CGCS2000或WGS84相同表面积的球半径近似为：R2= 6371007.18092(m)
    private double radius = 6371007.18092;
    Sphere sphere = new Sphere(center,radius);

}
