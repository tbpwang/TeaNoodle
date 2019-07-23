/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.attempt;

import edu.joel.*;
import edu.joel.io.Constant;
import edu.joel.model.*;
import gov.nasa.worldwind.geom.LatLon;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/6/5
 * @description
 */
public class SimpleTest
{
    public static void main(String[] args)
    {
        double lat = 85.0;
        LatLon top = LatLon.fromDegrees(90.0, 0.0);
        LatLon left = LatLon.fromDegrees(lat, 30.0);
        LatLon right = LatLon.fromDegrees(lat, 30.01);
        ZhaoSurfaceTriangle trianglez = new ZhaoSurfaceTriangle(top, left, right, new Geocode());
        SurfaceTriangle tri = new SurfaceTriangle(top, left, right, "");
        System.out.println("Unit " + tri.getUnitArea());

        double radius = Constant.getGlobe().getRadius();
        System.out.println("Area " + tri.getUnitArea() * radius * radius);
//
        double s, s0;
        double radLat = lat * Math.PI / 180.0;
        double parts = (right.longitude.degrees - left.longitude.degrees) / 360;
        s0 = 2 * Math.PI * (1 - Math.sin(radLat)) * radius * radius * parts;
        s = trianglez.computeArea();
        System.out.println("S " + s);
        System.out.println("S0 " + s0);
        double rate = s / s0;
        System.out.println(rate);
        StringBuilder builder = new StringBuilder();
        builder.append(123).append("how area you!").append(" ");
        builder.append(789).append("HOW OLD ARE YOU?");
        System.out.println(builder);

        //        String id = "120349807";
//        char[] chars = id.toCharArray();
//        int counter = 0;
//        char centerCode = '0';
//        for (char c : chars)
//        {
//            if (c == centerCode)
//                counter++;
//        }
//        System.out.println(counter);
//        System.out.println(counter % 2 == 0);

        System.out.println("========================");
        List<LatLon> latLonList = new ArrayList<>();
        latLonList.add(top);
        latLonList.add(left);
        latLonList.add(right);
        LatLon center = LatLon.getCenter(latLonList);
        LatLon center0 = LatLon.getCenter(Constant.getGlobe(),latLonList);
        LatLon pt = new LatLon(center0.latitude, center0.longitude);
        System.out.println("Pt:" + pt);
        System.out.println("Center0: "+center0);
        System.out.println("Center:" + center);

        System.out.println("SimpleName: "+MidArcTriangleMesh.class.getSimpleName());
        System.out.println("CanonicalName: "+MidArcTriangleMesh.class.getCanonicalName());
        System.out.println("TypeName: "+MidArcTriangleMesh.class.getTypeName());
        LatLon a,b;
        a = LatLon.fromDegrees(-30,80);
        b = LatLon.fromDegrees(20,80);
        System.out.println(a.getLatitude().getDegrees()>b.getLatitude().getDegrees());
    }
}
