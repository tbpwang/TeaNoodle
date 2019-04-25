/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import edu.zhenger.util.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/9/13
 */
public class MidArcsQTMCal
{
    public static void main(String[] args)
    {
        OctantMeshMidArcs mesh;

        for (int i = 9; i < 10; i++)
        {
            String title = "MidArcsQTM" + i;
            mesh = new OctantMeshMidArcs(0, i + 1);
//            Trigon[] trigons = mesh.getTrigons();
            // IO.write(title,mesh.getTrigons());
        }
    }
}
