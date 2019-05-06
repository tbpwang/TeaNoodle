/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.attempt;

import edu.joel.*;
import edu.joel.model.*;
import gov.nasa.worldwind.geom.LatLon;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/4/29 16:02
 * @description
 * @parameter
 */
public class Attempt
{
    public static void main(String[] args)
    {
        LatLon p1,p2,p3,p4;
        p1 = LatLon.fromDegrees(90,0);
        p2 = LatLon.fromDegrees(0,0);
        p3 = LatLon.fromDegrees(0,90);
        List<LatLon> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        Cell triangle = new MiddleArcTriangle(p1, p2, p3,"0");
        Cell[] subcells = triangle.refine();

        TriangleMesh mesh = new TriangleMesh(1);
        //mesh.add();


//        System.out.println(triangle.computeArea());
//        System.out.println(triangle.getUnitArea());
//        System.out.println("================");
//        double area = Math.PI*4*Math.pow(DGGS.getGlobe().getRadius(),2)/8.0;
//        System.out.println(area);
//        System.out.println("---------------");
//        System.out.println(triangle.toString());
//        LatLon center = triangle.getCenter();
//        System.out.println("center " + center.toString());

    }
}
