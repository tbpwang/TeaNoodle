/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.attempt;

import edu.joel.impl.*;
import edu.joel.io.*;
import edu.joel.model.SphereStatisticsPoint;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/8/30
 * @description
 */
public class DispersionMeasure
{
    public static void main(String[] args)
    {
//        String polyhedron = "Icosahedron";
        String polyhedron = "Octahedron";
        int level = 7;
//        List<Vec4> points = initIcosahedron();
//        List<Vec4> points = initOctahedron();
//        List<LatLon> points = initIcosahedron("");
        List<Vec4> points = subdivision(sample(polyhedron),level);
        polyhedron += "Triangle";

        //Octahedron OR Icosahedron
//        List<Vec4> points = new ArrayList<>();
        // Icosahedron
//        for (int i = 0; i < 20; i++)
//        {
//            points.addAll(subdivision(sample("Icosahedron", i), level));
//        }

//        // OR Octahedron
//        String polyhedron = "Octahedron";
//        for (int i = 0; i < 8; i++)
//        {
//            points.addAll(subdivision(sample("Octahedron", i), level));
//        }

        System.out.println("BeforeSize = " + points.size());
        double fromTime = System.nanoTime();
        removeDuplicatePoints(points);
        double endTime = System.nanoTime();
        double timeUsed = endTime - fromTime;
        System.out.println("Time = " + timeUsed);
        System.out.println("AfterSize = " + points.size());

        for (Vec4 p :
            points)
        {
//            IO.write("IcosahedronDispersion",String.valueOf(level),p.toString());
            IO.write(polyhedron + "Dispersion", "point" + level, p.toString());
//            System.out.print("Point ");
//            System.out.println(p.toString());
//            System.out.println(" <--> " + Constant.latLonToVec4(Constant.vec4ToLatLon(p)).toString());
//            System.out.println(p.toString() + " -" + Constant.vec4ToLatLon(Constant.latLonToVec4(p)));
        }
//        System.out.println("Points" + points.toString());
        SphereStatisticsPoint statistics = new SphereStatisticsPoint(points);

        IO.write(polyhedron + "Dispersion", "point" + level, "Dispersion " + statistics.getDispersion());
        IO.write(polyhedron + "Dispersion", "point" + level, "DispersionUnit " + statistics.getDispersionUnit());
//        IO.write(polyhedron + "Dispersion", "point" + level,
//            "1/DispersionUnit " + (1.0 / statistics.getDispersionUnit()));
        IO.write(polyhedron + "Dispersion", "point" + level, "Resultant " + statistics.getResultant());
        IO.write(polyhedron + "Dispersion", "point" + level, "MassCenter " + statistics.getMassCenter());
        IO.write(polyhedron + "Dispersion", "point" + level, "SphericalMean " + statistics.getSphericalMean());
        IO.write(polyhedron + "Dispersion", "point" + level, "MatrixT " + statistics.getMatrixT());
    }

//    private static void removeDuplicatePoints2(List<Vec4> points)
//    {
//        List<Vec4> temp = new ArrayList<>();
//        boolean changed = false;
//        for (Vec4 p :
//            points)
//        {
//            if (!temp.contains(p))
//            {
//                temp.add(p);
//            }
//            else
//            {
//                changed = true;
//            }
//        }
//        System.out.println("Changed = " + changed);
//        if (changed)
//        {
//            points.clear();
//            points.addAll(temp);
//        }
//    }

    private static void removeDuplicatePoints(List<Vec4> points)
    {
        List<Vec4> temp = new ArrayList<>();
        boolean changed = false;
        Set<Vec4> set = new HashSet<>();
        for (Vec4 p :
            points)
        {
            if (set.add(p))
            {
                temp.add(p);
                changed = true;
            }
        }
        System.out.println("Changed = " + changed);
        if (changed)
        {
            points.clear();
            points.addAll(temp);
        }
    }

    private static Triangle sample(String polyhedron)
    {
        return sample(polyhedron, 0);
    }

    private static Triangle sample(String polyhedron, int facet)
    {
        Triangle triangle;
        switch (polyhedron)
        {
            case "Octahedron":
                OctahedronInscribed octahedron = OctahedronInscribed.getInstance();
                triangle = octahedron.getFacetList().get(facet);
                break;
            case "Icosahedron":
                IcosahedronInscribed icosahedron = IcosahedronInscribed.getInstance();
                triangle = icosahedron.getFacets()[facet];
                break;
            default:
                triangle = new Triangle(new Vec4(1.0, 0.0, 0.0), new Vec4(0, 1.0, 0), new Vec4(0, 0, 1.0));
                break;
        }
        return triangle;
    }

    private static List<Vec4> subdivision(Triangle triangle, int level)
    {
        String pathType = AVKey.GREAT_CIRCLE;
        List<Triangle> triangleList = new ArrayList<>();
        List<Triangle> temp = new ArrayList<>();
        List<Vec4> vec4List = new ArrayList<>();

        triangleList.add(triangle);

        Vec4 vA = triangle.getA(), vB = triangle.getB(), vC = triangle.getC();
        vec4List.add(vA);
        vec4List.add(vB);
        vec4List.add(vC);
        LatLon lA, lB, lC;
        Vec4 vASub, vBSub, vCSub;
        LatLon lASub, lBSub, lCSub;
        for (int i = 0; i < level; i++)
        {
            for (Triangle t :
                triangleList)
            {
                vA = t.getA();
                vB = t.getB();
                vC = t.getC();
                lA = Constant.vec4ToLatLon(vA);
                lB = Constant.vec4ToLatLon(vB);
                lC = Constant.vec4ToLatLon(vC);
                lASub = LatLon.interpolate(pathType, 0.5, lB, lC);
                lBSub = LatLon.interpolate(pathType, 0.5, lA, lC);
                lCSub = LatLon.interpolate(pathType, 0.5, lA, lB);
                vASub = Constant.check(Constant.latLonToVec4(lASub));
                vBSub = Constant.check(Constant.latLonToVec4(lBSub));
                vCSub = Constant.check(Constant.latLonToVec4(lCSub));
                vec4List.add(vASub);
                vec4List.add(vBSub);
                vec4List.add(vCSub);

                temp.add(new Triangle(vASub, vBSub, vCSub));
                temp.add(new Triangle(vA, vCSub, vBSub));
                temp.add(new Triangle(vB, vCSub, vASub));
                temp.add(new Triangle(vC, vBSub, vASub));
            }
            triangleList.clear();
            triangleList.addAll(temp);
            temp.clear();
        }

        return vec4List;
    }

    private static List<Vec4> initOctahedron()
    {
        OctahedronInscribed octahedron = OctahedronInscribed.getInstance();
        return octahedron.getPoints();
    }

    private static List<LatLon> initIcosahedron(String source)
    {
        IcosahedronInscribed icosahedron = IcosahedronInscribed.getInstance();
        return Arrays.asList(icosahedron.getVertices());
    }

    private static List<Vec4> initIcosahedron()
    {
        IcosahedronInscribed icosahedron = IcosahedronInscribed.getInstance();
        return Arrays.asList(icosahedron.getPoints());
    }
}
