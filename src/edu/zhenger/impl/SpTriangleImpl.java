/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.SpTriangle;
import gov.nasa.worldwind.geom.LatLon;

/**
 * @Author: WANG Zheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2019/3/8
 */
public class SpTriangleImpl extends SpTriangle
{
    public SpTriangleImpl(LatLon top, LatLon left,
        LatLon right, String id)
    {
        super(top, left, right, id);
    }
}
