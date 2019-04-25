/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import edu.zhenger.util.IO;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.util.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/9/18
 */
public class MidArcCal extends ApplicationTemplate
{
    public static void main(String[] args)
    {
        int level = 10;

        SpTriangle triangle = SphereIcosahedron.getInstance().getSpFacets(7);

        String outTxt = "icosa1";

//        String outTxt = "octCore1";
        IO.write(outTxt, triangle.getArea());

        List<SpTriangle> temp = new ArrayList<>();
        List<SpTriangle> cells = new ArrayList<>();

        for (int i = 0; i < 4; i++)
        {
            cells.add(triangle.getChildren()[i]);
        }

        for (int i = 0; i < level; i++)
        {
//            outTxt = "octCore" + String.valueOf(i + 2);
            outTxt = "icosa" + (i + 2);
            for (SpTriangle cell : cells)
            {
                IO.write(outTxt, cell.getArea());
                for (int k = 0; k < 4; k++)
                {
                    temp.add(cell.getChildren()[k]);
                }
            }
            cells.clear();
            cells.addAll(temp);
            temp.clear();
        }
    }
}
