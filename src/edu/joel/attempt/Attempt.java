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

//        Cell triangle = new MiddleArcTriangle(list,"A");
//        Cell[] sub = triangle.refine();
        ZhaoQTM triangle = new ZhaoQTM(list,"A");
        ZhaoQTM[] sub = triangle.refine();

//        for (int i = 0; i < 10; i++)
//        {
//            if (i==4)
//            {
//                list.add(i,p1);
//                continue;
//            }
//            if (i==6)
//            {
//                list.add(i,p2);
//                continue;
//            }
//            if (i==8)
//            {
//                list.add(i,p3);
//                continue;
//            }
//            list.add(null);
//        }
//        list.set(0,p1);
//        list.add(1,p2);

        System.out.println("Size: ");
        System.out.println(list.size());
        System.out.println(Arrays.toString(list.toArray()));

        Mesh mesh = new TriangleMesh(1);
        mesh.add(sub[0],1,2,false);
        mesh.add(sub[1],2,1,false);
        mesh.add(sub[2],1,1,false);
        mesh.add(sub[3],1,3,false);

        System.out.println("Mesh:");
        System.out.println(mesh);

        System.out.println(sub[0].getClass().getSuperclass());
        System.out.println(Cell.class.getName());
        System.out.println(sub[0].getClass().getSuperclass().getName().equals(Cell.class.getName()));

//        double a = 1.00000000000000002;
//        double b = 1.00000000000000001;
//        boolean ab = (a - b) > 0;
//        System.out.println(ab);
//        double c = 1f;
//        double d = 1.0;
//        System.out.println(c);
//        System.out.println(d);
//
//        int[] te = new int[]{1,2,3,4,5};
//        System.out.println(Arrays.toString(te));



//        Cell triangle = new MiddleArcTriangle(p1, p2, p3,"0");
//        Cell[] subcells = triangle.refine();
//
//        TriangleMesh mesh = new TriangleMesh(1);
//        mesh.add(subcells[0],-1,-1);
//        mesh.add(subcells[1],-1,-1);
//        mesh.add(subcells[2],-1,-1);
//        mesh.add(subcells[3],-1,-1);





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
