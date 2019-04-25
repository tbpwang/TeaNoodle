/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.globes.EllipsoidalGlobe;

/**
 * @Author: WANG Zheng
 * @Email: tbpwang@gmail.com
 * @Function: EllipsoidalGlobe as CGCS2000
 * @Date: 2019/3/7
 */
public class Spheroid extends EllipsoidalGlobe
{
    private static Spheroid ourInstance = new Spheroid();

    public static final double CGCS2000_EQUATORIAL_RADIUS = 6378137.0; // ellipsoid equatorial getRadius, in meters
    public static final double CGCS2000_POLAR_RADIUS = 6356752.3141; // ellipsoid polar getRadius, in meters
    public static final double CGCS2000_ES = 0.00669438002290; // eccentricity squared, semi-major axis

//    public static final double ELEVATION_MIN = -11000d; // Depth of Marianas trench
//    public static final double ELEVATION_MAX = 8500d; // Height of Mt. Everest.

    public static Spheroid getInstance()
    {
        return ourInstance;
    }

    private Spheroid()
    {
        super(CGCS2000_EQUATORIAL_RADIUS,CGCS2000_POLAR_RADIUS,CGCS2000_ES,EllipsoidalGlobe.makeElevationModel(AVKey.EARTH_ELEVATION_MODEL_CONFIG_FILE,
            "config/Earth/EarthElevations2.xml"), Vec4.ZERO);
    }
}
