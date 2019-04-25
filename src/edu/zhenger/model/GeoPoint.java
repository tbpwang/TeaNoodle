/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.util.Term;
import gov.nasa.worldwind.geom.*;

/**
 * @Author: WANG Zheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2019/3/7
 */
public class GeoPoint extends Position
{
    double elevation;
    public GeoPoint(LatLon latLon)
    {
        super(latLon,Term.getSphere().getRadiusAt(latLon));

    }
}
