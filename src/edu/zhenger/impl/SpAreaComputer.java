/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.SpTriangle;
import edu.zhenger.util.IO;
import gov.nasa.worldwind.geom.LatLon;

import java.util.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function: 基于SpTriangle类的球面三角形的面积， 并输出到文本 title
 * @Date: 2018/12/20
 */
public class SpAreaComputer
{
    public static void main(String[] args)
    {
        LatLon pA, pB, pC;
        String title;

        // 正八面体的一个基面
        title = "OctArea";
        pA = LatLon.fromDegrees(90.0, 0.0);
        pB = LatLon.fromDegrees(0.0, 0.0);
        pC = LatLon.fromDegrees(0.0, 90.0);

//        // 正二十面体的一个基面
//        title = "IcoArea";
//        pA = LatLon.fromDegrees(90.0, 0.0);
//        pB = LatLon.fromDegrees(0.0, 0.0);
//        pC = LatLon.fromDegrees(0.0, 90.0);

        SpTriangle first = new SpTriangle(pA, pB, pC, "");
        List<SpTriangle> triangles = new ArrayList<>();
        List<SpTriangle> temps = new ArrayList<>();

        for (int level = 1; level < 11; level++)
        {
            triangles.add(first);
            for (int i = 0; i < level; i++)
            {
                SpTriangle[] children;
                for (SpTriangle triangle : triangles)
                {
                    children = triangle.getChildren();
                    temps.addAll(Arrays.asList(children).subList(0, 4));
                }
                triangles.clear();
                triangles.addAll(temps);
                temps.clear();
            }

            for (SpTriangle triangle : triangles)
            {
                //IO.write(title + level, triangle.getID() + " " + triangle.getArea() + " " + triangle.getUnitArea());
            }

            triangles.clear();
        }
    }
}
