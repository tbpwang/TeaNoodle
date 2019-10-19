/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.attempt;

import edu.joel.io.Constant;
import edu.joel.model.HolhosEqualArea;
import gov.nasa.worldwind.geom.*;

/**
 * @author Zheng WANG
 * @create 2019/6/5
 * @description
 */
public class SimpleTest
{
    public static void main(String[] args)
    {
//        double length = Math.sqrt(Math.PI/Math.sqrt(3));
        double length = Constant.radius;
//        Vec4 v1,v2,v3;
//        v1 = new Vec4(length,0,0);
//        v2 = new Vec4(0,length,0);
//        v3 = new Vec4(0,0,length);
        Vec4 p1 = HolhosEqualArea.planePoint(length, 0, 0);
        Vec4 p2 = HolhosEqualArea.planePoint(0, length, 0);
        Vec4 p3 = HolhosEqualArea.planePoint(0, 0, length);
        System.out.println("P1" + p1);
        System.out.println(HolhosEqualArea.inversePoint(p1));
        System.out.println("P2" + p2);
        System.out.println(HolhosEqualArea.inversePoint(p2));
        System.out.println("P3" + p3);
        System.out.println(HolhosEqualArea.inversePoint(p3));

        LatLon latLon = LatLon.fromDegrees(45, 30);
        Vec4 p4 = HolhosEqualArea.planePoint(latLon);
        System.out.println("P4" + p4);
        System.out.println(HolhosEqualArea.inversePoint(p4));

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
