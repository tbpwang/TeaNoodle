/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.globes.*;

/**
 * @Author: WANG Zheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2019/2/28
 */
public class UnitSphere extends EllipsoidalGlobe
{
    private static UnitSphere ourInstance = new UnitSphere();

    public static UnitSphere getInstance(){return ourInstance;}

    private UnitSphere()
    {
        super(1.0,1.0,0.0,null, Vec4.ZERO);
    }

}
