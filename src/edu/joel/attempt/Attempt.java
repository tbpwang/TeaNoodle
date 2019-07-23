/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.attempt;

import edu.joel.*;
import edu.joel.model.MiddleArcSurfaceTriangle;
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
//        System.out.println(Constant.getGlobe().getRadius());
        LatLon p1, p2, p3;
        p1 = LatLon.fromDegrees(90, 0);
        p2 = LatLon.fromDegrees(0, 0);
        p3 = LatLon.fromDegrees(0, 90);
//        List<LatLon> list = new ArrayList<>();
//        list.add(p1);
//        list.add(p2);
//        list.add(p3);

        MiddleArcSurfaceTriangle triangle = new MiddleArcSurfaceTriangle(p1, p2, p3, new Geocode("A"));
        //MiddleArcTriangle[] sub = triangle.binaryRefine();
//        ZhaoQTM triangle = new ZhaoQTM(list,new Geocode("A"));
//        ZhaoQTM[] sub = triangle.binaryRefine();
        int level = 2;

        List<MiddleArcSurfaceTriangle> triangleList;
        triangleList = new ArrayList<>(Collections.singletonList(triangle));
        refineTriangle(level, triangleList);

        triangleList.sort((o1, o2) -> {
            int diff1 = o1.getGeocode().getRow() - o2.getGeocode().getRow();
            int diff2 = o1.getGeocode().getColumn() - o2.getGeocode().getColumn();
            if (diff1 == 0)
            {
                return diff2;
            }
            return diff1;
        });

        for (MiddleArcSurfaceTriangle tri : triangleList)
        {
            System.out.println(tri.getGeocode().toString());
        }
        System.out.println("total: " + triangleList.size());

        int j = OctahedronBaseID.H.getIndex();
        for (int i = 0; i < j; i++)
        {
            System.out.println(OctahedronBaseID.indexOf(i));
        }
        System.out.println();
        System.out.println(OctahedronBaseID.indexOf(10));
        System.out.println(OctahedronBaseID.valueOf("A").ordinal());
        System.out.println(OctahedronBaseID.valueOf("A").name());
        System.out.println(OctahedronBaseID.valueOf("A"));

        System.out.println("==");
        String A = "A";
        String B = "B";
        System.out.println(A.compareTo(B));

    }

    static void refineTriangle(int level, List<MiddleArcSurfaceTriangle> triangles)
    {
        List<MiddleArcSurfaceTriangle> tempList = new ArrayList<>();
        for (int i = 0; i < level; i++)
        {
            for (MiddleArcSurfaceTriangle tri : triangles)
            {
                tempList.addAll(Arrays.asList(tri.refine()));
            }
            triangles.clear();
            triangles.addAll(tempList);
            tempList.clear();
        }
    }
}
