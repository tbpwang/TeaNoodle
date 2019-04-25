/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.test;

import edu.zhenger.model.*;
import edu.zhenger.util.*;
import edu.zhenger.util.Term;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.util.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/6/27
 */
public class Test //extends ApplicationTemplate
{
//    public static class TestFrame extends AppFrame
//    {
//        public TestFrame()
//        {
//            LatLon p1, p2, p3;
//            p1 = LatLon.fromDegrees(0.0, 0.0);
//            p2 = LatLon.fromDegrees(90.0, 0.0);
//            p3 = LatLon.fromDegrees(0.0, 90.0);
//
//            SpTriangle spTriangle = new SpTriangle(p2, p1, p3, "0");
//            System.out.println("spTriangle" + spTriangle.getArea());
//            System.out.println(spTriangle.getArea() * Math.pow(Term.getSphere().getRadius(), 2));
//
//            Trigon triangle = new Trigon(p2, p1, p3, "");
//            System.out.println("SphereTriangle: " + triangle.getArea());
//
//            List<Position> vertex = new ArrayList<>();
//            vertex.add(Position.fromDegrees(0.0, 0.0));
//            vertex.add(Position.fromDegrees(90.0, 0.0));
//            vertex.add(Position.fromDegrees(0.0, 90.0));
//
//            List<Vec4> vec4List = new ArrayList<>();
//            vec4List.add(Term.fromLatLon(p1));
//            vec4List.add(Term.fromLatLon(p2));
//            vec4List.add(Term.fromLatLon(p3));
//
//            System.out.println("PolygonArea:");
//            System.out.println(WWMath.computePolygonAreaFromVertices(vec4List));
//
//            System.out.println("PrjArea:");
//            System.out.println(WWMath.computeSphereProjectedArea(getWwd().getView(), Vec4.computeAveragePoint(vec4List),
//                Term.getSphere().getRadius()));
//        }
//    }

    public static void main(String[] args)
    {
        LatLon p1, p2, p3;
        p1 = LatLon.fromDegrees(45.0, 0.0);
        p2 = LatLon.fromDegrees( 45.0, 90.0);
        p3 = LatLon.fromDegrees(45.0, 45.0);

        SpTriangle spTriangle = new SpTriangle(p2, p1, p3, "0");
        System.out.println("spTriangle  " );
        System.out.println(spTriangle.getArea());

//
//        Trigon triangle = new Trigon(p2, p1, p3, "");
//        System.out.println("SphereTriangle: " );
//        System.out.println(triangle.getArea());

        List<Position> vertex = new ArrayList<>();
        vertex.add(Position.fromDegrees(0.0, 0.0));
        vertex.add(Position.fromDegrees(90.0, 0.0));
        vertex.add(Position.fromDegrees(0.0, 90.0));

        List<Vec4> vec4List = new ArrayList<>();
        vec4List.add(Term.fromLatLon(p1));
        vec4List.add(Term.fromLatLon(p2));
        vec4List.add(Term.fromLatLon(p3));

        System.out.println("PolygonArea:");
        System.out.println(WWMath.computePolygonAreaFromVertices(vec4List));

//        System.out.println("PrjArea:");
//        System.out.println(WWMath.computeSphereProjectedArea(getWwd().getView(), Vec4.computeAveragePoint(vec4List),
//            Term.getSphere().getRadius()));
//


//        start("Area",TestFrame.class);

//        double r = Term.getSphere().getRadius();
//        //start("TestView", TestFrame.class);
//
//        System.out.println("==============");
//        LatLon[] vertices = new LatLon[6];
//        vertices[0] = LatLon.fromDegrees(90.0, 0.0);
//        vertices[1] = LatLon.fromDegrees(0.0, 0.0);
//        vertices[2] = LatLon.fromDegrees(0.0, 90.0);
//        vertices[3] = LatLon.fromDegrees(0.0, 180.0);
//        vertices[4] = LatLon.fromDegrees(0.0, -90.0);
//        vertices[5] = LatLon.fromDegrees(-90.0, 0.0);
//
//        Vec4[] vertexes = new Vec4[6];
//        for (int i = 0; i < 6; i++)
//        {
//            vertexes[i] = Term.fromLatLon(vertices[i]);
//        }
//
//        for (int i = 0; i < 6; i++)
//        {
//            System.out.println(i + ":");
//            System.out.println(vertexes[i]);
//            System.out.println(vertices[i]);
//        }
//
////        Earth earth = new Earth();
////        Vec4 center = earth.getCenter();
////        System.out.println(center.toString());
//
////        SphereIcosahedron icosahedron = SphereIcosahedron.getInstance();
////
////        double sum = 0.0;
////        for (int i = 0; i < 20; i++)
////        {
////            sum += icosahedron.getSpFacets(i).getUnitArea();
//////            sum += icosahedron.getSpFacets(i).getArea();
////            System.out.println(i + "Area = " + icosahedron.getSpFacets(i).getUnitArea());
//////            System.out.println(i + "Area = " + icosahedron.getSpFacets(i).getArea());
////        }
////        System.out.println("SumArea = " + sum);
//////        System.out.println("Sphere:" + 4 * Math.PI * r * r);
////        System.out.println("Sphere:" + 4 * Math.PI );
//
////        System.out.println("Average:" + 4 * Math.PI * r * r/20.0);
////        System.out.println("Average:" + 4 * Math.PI / 20.0);
//
////        LatLon pA, pB, pC;
//////        pA = LatLon.fromRadians(0.0,0.5);
//////        pB = LatLon.fromRadians(0.0,1.5);
////        pA = LatLon.fromDegrees(90.0, 0.0);
////        pB = LatLon.fromDegrees(0.0, 0.0);
////        pC = LatLon.fromDegrees(0.0, 90.0);
////        SpTriangle first = new SpTriangle(pA, pB, pC, "");
//        SpTriangle first = SphereIcosahedron.getInstance().getSpFacets(7);
//        List<SpTriangle> triangles = new ArrayList<>();
//        List<SpTriangle> temps = new ArrayList<>();
//
////        LatLon a,b,c,aa,bb,cc;
////        a = LatLon.interpolateGreatCircle(0.5,pC,pB);
////        b = LatLon.interpolateGreatCircle(0.5,pA,pC);
////        c = LatLon.interpolateGreatCircle(0.5,pA,pB);
////        bb = LatLon.interpolateGreatCircle(0.5,a,c);
////        cc = LatLon.interpolateGreatCircle(0.5,a,b);
////
////        SpTriangle tri = new SpTriangle(a,bb,cc,"0");
////        SpTriangle[] tris = tri.getChildren();
//
////        System.out.println(pA);
////        System.out.println("a-bb: " + LatLon.greatCircleDistance(bb,a));
////        System.out.println("a-cc: " + LatLon.greatCircleDistance(cc,a));
////        System.out.println("bb-cc: " + LatLon.greatCircleDistance(cc,bb));
////        System.out.println("------");
//
////        System.out.println("ca = "+LatLon.greatCircleDistance(c,a));
////        System.out.println("cB = "+LatLon.greatCircleDistance(c,pB));
////        System.out.println("Ba = "+LatLon.greatCircleDistance(pB,a));
////        System.out.println("-------------");
////        System.out.println("Cb = "+LatLon.greatCircleDistance(pC,b));
////        System.out.println("Ca = "+LatLon.greatCircleDistance(pC,a));
////        System.out.println("ba = "+LatLon.greatCircleDistance(b,a));
////        System.out.println("--");
////        System.out.println((LatLon.greatCircleDistance(c,a).degrees-LatLon.greatCircleDistance(c,pB).degrees)/LatLon.greatCircleDistance(c,pB).degrees);
//
//        for (int level = 1; level < 11; level++)
//        {
//            triangles.add(first);
//            for (int i = 0; i < level; i++)
//            {
//                SpTriangle[] children;
//                for (SpTriangle triangle : triangles)
//                {
//                    children = triangle.getChildren();
//                    temps.addAll(Arrays.asList(children).subList(0, 4));
//                }
//                triangles.clear();
//                triangles.addAll(temps);
//                temps.clear();
//            }
//            for (SpTriangle triangle : triangles)
//            {
//                //IO.write("IcoArea" + level, triangle.getID() + " " + triangle.getArea() + " " + triangle.getUnitArea());
//            }
//
//            triangles.clear();
//        }

//        SurfacePolygon tri = new SurfacePolygon();
//        tri.setLocations(vertex);

//        AreaMeasurer areaMeasurer = new AreaMeasurer((ArrayList<? extends Position>) vertex);
//
//        System.out.println("SpAreaComputer: " + areaMeasurer.getSurfaceArea(Term.getSphere()));
//        System.out.println("PrjArea: " + areaMeasurer.getProjectedArea(Term.getSphere()));

//        System.out.println(Sphere.getInstance());
//
//
//        LatLon a = LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f,0f), LatLon.fromDegrees(0f,90f));
//        LatLon b = LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f,0f), LatLon.fromDegrees(90f,0f));
//        LatLon c = LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f,90f), LatLon.fromDegrees(90f,90f));
//        Trigon octan = new Trigon(a,b,c,"0");
//        List<Trigon> coreList = new ArrayList<>();
//        coreList.add(octan);
//        for (int i = 0; i < 30; i++)
//        {
//            coreList.add(new TrigonCore(coreList.get(i)).getCore());
//        }
//        for (Trigon aCore : coreList)
//        {
////            System.out.println(aCore.getArea());
//            //IO.write("coreTrigon",aCore.getArea());
//            IO.write("corePerimeter",aCore.getPerimeter());
//        }

//        double r, x, z;
//        r = Term.getSphere().getRadius();
//        x = r * Math.sqrt(50.0 - 10.0 * Math.sqrt(5.0)) / 10.0;
//        z = r * Math.sqrt(50.0 + 10.0 * Math.sqrt(5.0)) / 10.0;
//        Vec4[] vertexs = new Vec4[12];
//        vertexs[0] = new Vec4(-0.894427188999916, 0, 0.447213599499958);
//        vertexs[1] = new Vec4(-0.723606798171796, -0.525731108671732, -0.447213598870108);
//        vertexs[2] = new Vec4(-0.723606798171796, 0.525731108671732, -0.447213598870108);
//        vertexs[3] = new Vec4(-0.276393199228192, -0.850650807624619, 0.447213598751188);
//        vertexs[4] = new Vec4(-0.276393199228192, 0.850650807624619, 0.447213598751188);
//        vertexs[5] = new Vec4(0, 0, -1);
//        vertexs[6] = new Vec4(0, 0, 1);
//        vertexs[7] = new Vec4(0.276393199228192, -0.850650807624619, -0.447213598751188);
//        vertexs[8] = new Vec4(0.276393199228192, 0.850650807624619, -0.447213598751188);
//        vertexs[9] = new Vec4(0.723606798171796, -0.525731108671732, 0.447213598870108);
//        vertexs[10] = new Vec4(0.723606798171796, 0.525731108671732, 0.447213598870108);
//        vertexs[11] = new Vec4(0.894427188999916, 0, -0.447213599499958);
//
//        LatLon A,B,C;
//        A = Term.fromVec4(vertexs[3]);
//        B = Term.fromVec4(vertexs[6]);
//        C = Term.fromVec4(vertexs[0]);
//        System.out.println("Distance:");
//        System.out.println(LatLon.greatCircleDistance(A,B));
//        System.out.println(LatLon.greatCircleDistance(A,C));
//        System.out.println(LatLon.greatCircleDistance(C,B));
//        SpTriangle sp = new SpTriangle(A,C,B,"");
//        SpTriangle[] sps = sp.getChildren();
//        System.out.println("Area:");
//        System.out.println(sp.getArea());
//        System.out.println("二十面体面:");
//        System.out.println(4*Math.PI*r*r/20.0);
//        System.out.println("Children");
//        for (int i = 0; i < 4; i++)
//        {
//            System.out.println(sps[i].getID());
//            System.out.println(sps[i].getArea());
//        }

//        for (int i = 0; i < 12; i++)
//        {
//            System.out.println(i + ":" + Term.fromVec4(vertexs[i]));
//        }
//
//        System.out.println("---------------");
//        Vec4 v1 = new Vec4(-0.894427188999916*r, 0, 0.447213599499958*r);
//        System.out.println(Term.fromVec4(v1));

//        double r = Term.getSphere().getRadius();
//        int level = 7;
//        int num = (int) Math.pow(4, level);
//        double area = 0.5 * Math.PI * r * r / num;
//        System.out.println(area);
//
//        System.out.println("======================");
//        double m, n;
//        m = Math.sqrt(50.0 - 10.0 * Math.sqrt(5.0)) / 10.0;
//        n = Math.sqrt(50.0 + 10.0 * Math.sqrt(5.0)) / 10.0;
//        Vec4 vA = new Vec4(0, r * n, r * m);
//        Vec4 vB = new Vec4(-r * m, 0, r * n);
//        Vec4 vC = new Vec4(r * m, 0, r * n);
//        LatLon latLonA = Term.fromVec4(vA);
//        LatLon latLonB = Term.fromVec4(vB);
//        LatLon latLonC = Term.fromVec4(vC);
//        SphericalTriangleCell cell = new SphericalTriangleCell(latLonA, latLonB, latLonC, "1");
//        double icosaArea = 4.0 * Math.PI * r * r / 20.0;
//        System.out.println("IcosaArea = " + icosaArea);
//        System.out.println("Area = " + cell.getArea());
//        System.out.println(" / " + icosaArea / cell.getArea());
//        System.out.println("A: " + latLonA.latitude.degrees + "," + latLonA.longitude.degrees);
//        System.out.println("B: " + latLonB.latitude.degrees + "," + latLonB.longitude.degrees);
//        System.out.println("C: " + latLonC.latitude.degrees + "," + latLonC.longitude.degrees);
//        System.out.println("======================");
//
//        Vec4 vec4 = new Vec4(0, 0, 0);
//        new LatLon(Term.getSphere().computePositionFromPoint(vec4));
//
//        LatLon A = LatLon.fromDegrees(45, 0);
//        LatLon B = LatLon.fromDegrees(45, 90);
//        LatLon Cgc = LatLon.interpolateGreatCircle(0.5, A, B);
//        System.out.println(Cgc);
//        LatLon Drh = LatLon.interpolateRhumb(0.5, A, B);
//        System.out.println(Drh);
//
//        //LatLon E = LatLon.i
//        System.out.println(Arrays.toString(LatLon.greatCircleArcExtremeLocations(A, B)));
//        System.out.println("====================");
//        Angle a1 = LatLon.rhumbAzimuth(A, B);
//        Angle a11 = LatLon.rhumbAzimuth(A, B);
//        System.out.println(a1);
//        System.out.println("A11: " + a11);
//        a1 = LatLon.greatCircleDistance(A, B);
//        System.out.println(a1);
//        System.out.println("A11= " + a11);
//
//        Angle a2 = LatLon.rhumbDistance(A, B);
//        System.out.println(a2);
//        Angle a3 = LatLon.greatCircleAzimuth(A, B);
//        System.out.println(a3);
//        Angle a4 = LatLon.greatCircleDistance(A, B);
//        System.out.println(a4);
//        Angle a5 = LatLon.linearAzimuth(A, B);
//        System.out.println(a5);
//        Angle a6 = LatLon.linearDistance(A, B);
//        System.out.println(a6);
    }
}

