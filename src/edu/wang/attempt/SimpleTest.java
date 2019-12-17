/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.wang.attempt;

import edu.wang.Geocode;
import edu.wang.impl.OctahedronInscribed;
import edu.wang.io.Cons;
import edu.wang.model.*;
import gov.nasa.worldwind.geom.*;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/6/5
 * @description 临时测试程序
 */
public class SimpleTest
{
    public static void main(String[] args)
    {
        System.out.println("1111111111111111111111111");
        LatLon tp1 = LatLon.fromDegrees(80,20);
        LatLon tp2 = LatLon.fromDegrees(58,68);
        double d1 = LatLon.greatCircleDistance(tp1,tp2).radians*Cons.radius;
        double d2 = Distance.distance(tp1,tp2);
        System.out.println("GCDist = " + d1);
        System.out.println("Dist = " + d2);
        System.out.println("d1/d2 = " + (d1/d2));
        double an1 = LatLon.greatCircleAzimuth(tp1,tp2).degrees;
        double an2 = Azimuth.azimuth(tp1,tp2).degrees;
        System.out.println("GCAzimuth = " +an1);
        System.out.println("Azimuth = " + an2);

        System.out.println("222222222222222222222222");
        int capacity = 8;
        OctahedronInscribed instance = OctahedronInscribed.getInstance();
        // List<List<Vec4>> surfacePoints = new ArrayList<>(8);
        // 初始基本形状
        List<Double> planeAreas = new ArrayList<>(capacity);
        List<List<Double>> planeEdges = new ArrayList<>(capacity);
        //List<Double> facetEdges = new ArrayList<>(3);
        List<Double> sphericalAreas = new ArrayList<>(capacity);
        List<List<Double>> sphericalEdges = new ArrayList<>(capacity);
        List<MiddleArcSurfaceTriangle> spTriangles = new ArrayList<>(capacity);
        //List<Double> spTriEdges = new ArrayList<>(3);
        for (Triangle tri :
            instance.getFacetList())
        {
            Vec4 v1, v2, v3;
            v1 = tri.getA();
            v2 = tri.getB();
            v3 = tri.getC();
            planeAreas.add(Area.planeTriangleArea(v1, v2, v3));
            planeEdges.add(Perimeter.triangleEdges(v1, v2, v3));
            LatLon p1, p2, p3;
            p1 = Cons.vec4ToLatLon(v1);
            p2 = Cons.vec4ToLatLon(v2);
            p3 = Cons.vec4ToLatLon(v3);
            spTriangles.add(new MiddleArcSurfaceTriangle(p1,p2,p3,new Geocode()));
            sphericalEdges.add(Perimeter.sphericalTriangleEdge(p1, p2, p3));
            sphericalAreas.add(Area.sphericalTriangleArea(p1, p2, p3));
        }

        String folderName = "SQT\\level0";
        MiddleArcSurfaceTriangle first, second;
        first = spTriangles.get(0);
        second =spTriangles.get(1);
        LatLon fstCenter = first.getCenter();
        LatLon secCenter = second.getCenter();
        LatLon fstSecMiddle = LatLon.interpolateGreatCircle(0.5,fstCenter,secCenter);
        LatLon fstA,fstB, fstC;
        fstA = LatLon.interpolateGreatCircle(0.5,first.getLeft(),first.getRight());
        fstB = LatLon.interpolateGreatCircle(0.5,first.getRight(),first.getTop());
        fstC = LatLon.interpolateGreatCircle(0.5,first.getLeft(),first.getTop());
        d1 = LatLon.linearDistance(fstSecMiddle,fstA).radians;
        d2 = LatLon.linearDistance(fstSecMiddle,fstB).radians;
        double d3 = LatLon.linearDistance(fstSecMiddle,fstC).radians;
        double middleDistance = Math.min(Math.min(d1,d2),d3)*Cons.radius;
        ///IO.write(folderName,"elong_0",String.valueOf(middleDistance));

        for (int i = 0; i < capacity; i++)
        {
            MiddleArcSurfaceTriangle triangle = spTriangles.get(i);
            LatLon center = triangle.getCenter();
            LatLon top,left,right;
            top = triangle.getTop();
            left = triangle.getLeft();
            right = triangle.getRight();
            // elong
            String strElong = LatLon.greatCircleDistance(center, top).getRadians() * Cons.radius + "\t"
                + LatLon.greatCircleDistance(center, left).getRadians() * Cons.radius + "\t"
                + LatLon.greatCircleDistance(center, right).getRadians() * Cons.radius + "\t";
            ///IO.write(folderName,"elong_0", strElong);
            // area
            ///IO.write(folderName,"area_0",planeAreas.get(i).toString());
            ///IO.write(folderName,"sphereArea_0",sphericalAreas.get(i).toString());
            // edge
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder spStrBuilder = new StringBuilder();
            for (int j = 0; j < 3; j++)
            {
                stringBuilder.append(planeEdges.get(i).get(j)).append("\t");
                spStrBuilder.append(sphericalEdges.get(i).get(j)).append("\t");
            }
            ///IO.write(folderName,"edge_0",stringBuilder.toString());
            ///IO.write(folderName,"sphereEdge_0",spStrBuilder.toString());

        }
        System.out.println("=============");
        double spc = Compactness.getSphereCompactness(sphericalAreas.get(0),sphericalEdges.get(0).get(0)*3);
        System.out.println("SphereCompactness = " + spc);
        System.out.println("Area = " + 4*Math.PI*Math.pow(Cons.radius,2)/8);

//        Triangle triangle = OctahedronInscribed.getInstance().getFacetList().get(0);
//        LatLon top = Cons.vec4ToLatLon(triangle.getA());
//        LatLon left = Cons.vec4ToLatLon(triangle.getB());
//        LatLon right = Cons.vec4ToLatLon(triangle.getC());
//        MiddleArcSurfaceTriangle surfaceTriangle = new MiddleArcSurfaceTriangle(top,left,right,new Geocode("A"));
//        int level  = 1;
//        List<MiddleArcSurfaceTriangle> subTriangles = Arrays.asList(surfaceTriangle.refine());
//        subTriangles.sort((o1, o2) -> Cons.sortByRow(o1.getGeocode(),o2.getGeocode()));
//        Set<LatLon> set = new HashSet<>();
//        List<LatLon> vertices = new ArrayList<>();
//        for (MiddleArcSurfaceTriangle tri :
//            subTriangles)
//        {
////            if (tri.isUp())
////            {
////                if (set.add(tri.getLeft()))
////                {
////                    vertices.add(tri.getLeft());
////                }
////                if (set.add(tri.getRight()))
////                {
////                    vertices.add(tri.getRight());
////                }
////            }
////            if (tri.getGeocode().getRow()==Math.pow(2,level))
////            {
////                if (set.add(tri.getTop()))
////                {
////                    vertices.add(tri.getTop());
////                }
////            }
//        }
//        System.out.println(set.size());
//        for (LatLon vertex :
//            vertices)
//        {
//            System.out.println(vertex);
//        }

//        double a = 12345678901.123456789;
//        System.out.println(IO.formatDouble(a));
//        System.out.println("PI = "+IO.formatDouble(Math.PI));

//        MidArcTriangleMesh fullMesh = new MidArcTriangleMesh(3);
//        MidArcTriangleMesh triangleMesh = fullMesh.cutMesh(1);//1-8
//        triangleMesh.

////        double length = Math.sqrt(Math.PI/Math.sqrt(3));
//        double length = Constant.radius;
//
////        Vec4 p1 = HolhosEqualArea.toPlanePoint(-length, 0, 0);
////        Vec4 p2 = HolhosEqualArea.toPlanePoint(0, -length, 0);
////        Vec4 p3 = HolhosEqualArea.toPlanePoint(0, 0, length);
////        System.out.println("P1" + p1);
////        System.out.println(HolhosEqualArea.toSpherePoint(p1));
////        System.out.println("P2" + p2);
////        System.out.println(HolhosEqualArea.toSpherePoint(p2));
////        System.out.println("P3" + p3);
////        System.out.println(HolhosEqualArea.toSpherePoint(p3));
//
//        LatLon latLon = LatLon.fromDegrees(-75, 30);
//        Vec4 p4 = HolhosEqualArea.toPlanePoint(latLon);
//        System.out.println("P4" + p4);
//        System.out.println(HolhosEqualArea.toSpherePoint(p4));

//        ChoiceFormat fmt = new ChoiceFormat(
//            "-1#is negative| 0#is zero or fraction | 1#is one |1.0<is 1+ |2#is two |2<is more than 2.");
//        System.out.println("Formatter Pattern : " + fmt.toPattern());
//        System.out.println("Format with -INF : " + fmt.format(Double.NEGATIVE_INFINITY));
//        System.out.println("Format with -1.0 : " + fmt.format(-1.0));
//        System.out.println("Format with 0 : " + fmt.format(0));
//        System.out.println("Format with 0.9 : " + fmt.format(0.9));
//        System.out.println("Format with 1.0 : " + fmt.format(1));
//        System.out.println("Format with 1.5 : " + fmt.format(1.5));
//        System.out.println("Format with 2 : " + fmt.format(2));
//        System.out.println("Format with 2.1 : " + fmt.format(2.1));
//        System.out.println("Format with NaN : " + fmt.format(Double.NaN));
//        System.out.println("Format with +INF : " + fmt.format(Double.POSITIVE_INFINITY));

//        Vec4 vx = new Vec4(1, 0, 0);
//        Vec4 vy = new Vec4(0, 1, 0);
//        Vec4 vz = new Vec4(0, 0, 1);
//        Triangle triangle = new Triangle(vx,vy,vz);
//        double lxy,lxz,lyz, triangleArea;
//        lxy = vx.distanceTo3(vy);
//        lxz = vx.distanceTo3(vz);
//        lyz = vy.distanceTo3(vz);
//        // 海伦公式
//        triangleArea = Math.sqrt((lxy+lyz+lxz)*(lxy+lyz-lxz)*(lxy-lyz+lxz)*(0-lxy+lyz+lxz))/4;
//        System.out.println("triangleArea = " + triangleArea);
//
////        System.out.println("vx " + vx);
//        LatLon x = Constant.vec4ToLatLon(vx);
//        LatLon y = Constant.vec4ToLatLon(vy);
//        LatLon z = Constant.vec4ToLatLon(vz);
//        // surface(cell) area
//        SurfaceTriangle surfaceTriangle = new SurfaceTriangle(y,z,x,"");
//        System.out.println("SurfacetriangleUnit = " + surfaceTriangle.getUnitArea());
//        System.out.println("Surfacetriangle = " + surfaceTriangle.getUnitArea()*Constant.radius);
//        System.out.println("=================");
//        System.out.println("x " + x);
//        System.out.println("y " + y);
//        System.out.println("z " + z);
//        Vec4 temp = new Vec4(1,0,0);
//        System.out.println("============? " +temp.equals(vx));
//        LatLon pa = LatLon.fromDegrees(48,67);
//        LatLon pb = LatLon.fromDegrees(21,152);
//
//        System.out.println("rhumbDistance(x,y) = "+LatLon.rhumbDistance(x,y).radians);
//        System.out.println("rhumbAzimuth(x,y) = "+LatLon.rhumbAzimuth(x,y).radians);
//        System.out.println("greatCircleDistance(x,y) = "+LatLon.greatCircleDistance(x,y).radians);
//        System.out.println("greatCircleAzimuth(x,y) = "+LatLon.greatCircleAzimuth(x,y).radians);
//        System.out.println(pa);
//        System.out.println(pb);
//        System.out.println("rhumbDistance(pa,pb) = "+LatLon.rhumbDistance(pa,pb).radians);
//        System.out.println("greatCircleDistance(pa,pb) = "+LatLon.greatCircleDistance(pa,pb).radians);
//        System.out.println("linearDistance(pa,pb) = "+LatLon.linearDistance(pa,pb).radians);
//        System.out.println("rhumbAzimuth(pa,pb) = "+LatLon.rhumbAzimuth(pa,pb).radians);
//        System.out.println("greatCircleAzimuth(pa,pb) = "+LatLon.greatCircleAzimuth(pa,pb).radians);
//        System.out.println("linearAzimuth(pa,pb) = "+LatLon.linearAzimuth(pa,pb).radians);
//        LatLon.interpolate("",0.5,pa,pb);

//        SurfacePolygon

//        LatLon p1 = LatLon.fromDegrees(45.0, 0.0);
//        LatLon p2 = LatLon.fromDegrees(45.0, 180.0);
//        LatLon pm = LatLon.interpolateGreatCircle(0.5, p1, p2);
//        System.out.println(pm);
//        double lat = 85.0;
//        LatLon top = LatLon.fromDegrees(90.0, 0.0);
//        LatLon left = LatLon.fromDegrees(lat, 30.0);
//        LatLon right = LatLon.fromDegrees(lat, 30.01);
//        ZhaoSurfaceTriangle trianglez = new ZhaoSurfaceTriangle(top, left, right, new Geocode());
//        SurfaceTriangle tri = new SurfaceTriangle(top, left, right, "");
//        System.out.println("Unit " + tri.getUnitArea());
//
//        double radius = Constant.getGlobe().getRadius();
//        System.out.println("Area " + tri.getUnitArea() * radius * radius);
////
//        double s, s0;
//        double radLat = lat * Math.PI / 180.0;
//        double parts = (right.longitude.degrees - left.longitude.degrees) / 360;
//        s0 = 2 * Math.PI * (1 - Math.sin(radLat)) * radius * radius * parts;
//        s = trianglez.computeArea();
//        System.out.println("S " + s);
//        System.out.println("S0 " + s0);
//        double rate = s / s0;
//        System.out.println(rate);
//        StringBuilder builder = new StringBuilder();
//        builder.append(123).append("how area you!").append(" ");
//        builder.append(789).append("HOW OLD ARE YOU?");
//        System.out.println(builder);

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

//        System.out.println("========================");
//        List<LatLon> latLonList = new ArrayList<>();
//        latLonList.add(top);
//        latLonList.add(left);
//        latLonList.add(right);
//        LatLon center = LatLon.getCenter(latLonList);
//        LatLon center0 = LatLon.getCenter(Constant.getGlobe(),latLonList);
//        LatLon pt = new LatLon(center0.latitude, center0.longitude);
//        System.out.println("Pt:" + pt);
//        System.out.println("Center0: "+center0);
//        System.out.println("Center:" + center);
//
//        System.out.println("SimpleName: "+MidArcTriangleMesh.class.getSimpleName());
//        System.out.println("CanonicalName: "+MidArcTriangleMesh.class.getCanonicalName());
//        System.out.println("TypeName: "+MidArcTriangleMesh.class.getTypeName());
//        LatLon a,b;
//        a = LatLon.fromDegrees(-30,80);
//        b = LatLon.fromDegrees(20,80);
//        System.out.println(a.getLatitude().getDegrees()>b.getLatitude().getDegrees());
    }
}
