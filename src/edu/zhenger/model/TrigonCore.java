/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.geom.LatLon;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function: the core sub-triangle in a triangle
 * @Date: 2018/11/19
 *
 */
public class TrigonCore
{
    Trigon core;

    public TrigonCore(Trigon trigon)
    {
        LatLon a = LatLon.interpolateGreatCircle(0.5, trigon.getLeft(), trigon.getRight());
        LatLon b = LatLon.interpolateGreatCircle(0.5, trigon.getTop(), trigon.getLeft());
        LatLon c = LatLon.interpolateGreatCircle(0.5, trigon.getTop(), trigon.getRight());

        core = new Trigon(a, b, c, trigon.getGeocode() + "0");
    }

    public Trigon getCore()
    {
        return core;
    }
}
