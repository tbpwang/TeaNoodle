/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.impl;

import edu.joel.io.Constant;
import edu.joel.model.*;
import gov.nasa.worldwind.geom.LatLon;

import java.text.Format;
import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/9/22
 * @description plane compactness, sphere compactness, shape distortion = planeCompactness/sphereCompactness
 */
public class CompactnessImpl
{
    public static void main(String[] args)
    {
        IcosahedronInscribed instance = IcosahedronInscribed.getInstance();
        int facesNumber = 20; // 8
//        OctahedronInscribed instance = OctahedronInscribed.getInstance();
//        int facesNumber = 8; // 20
        LatLon a, b, c;
        double baseSphericalFacetArea = 4 * Math.PI * Math.pow(Constant.radius, 2) / facesNumber;
        // perimeter: planar and spherical perimeter
        List<List<Double>> perimeter = new ArrayList<>();
        List<Double> perimeterPlSp;
        // area: planar and spherical area
        List<List<Double>> area = new ArrayList<>();
        List<Double> areaPlSp;
        // compactness Or Shape Distortion
        List<List<Double>> distortion = new ArrayList<>();
        List<Double> distortionPlSp;
//        List<Double> shapeDistortion;
        double epsilon = 1e13 * facesNumber;
        double delta;
        int facet = 0;
        List<Double> show = new ArrayList<>();
        for (int i = 0; i < facesNumber; i++)
        {
            a = Constant.vec4ToLatLon(instance.getFacets()[i].getA());
            b = Constant.vec4ToLatLon(instance.getFacets()[i].getB());
            c = Constant.vec4ToLatLon(instance.getFacets()[i].getC());
            perimeterPlSp = new ArrayList<>(2);
            perimeterPlSp.add(Perimeter.getPlanePerimeter(instance.getFacets()[i]));
            perimeterPlSp.add(Perimeter.getSpherePerimeter(a, b, c));
            perimeter.add(perimeterPlSp);
            areaPlSp = new ArrayList<>(2);
            areaPlSp.add(Area.getPlaneArea(instance.getFacets()[i]));
            areaPlSp.add(Area.getSphereArea(a, b, c));
            area.add(areaPlSp);

            delta = Math.abs(areaPlSp.get(1) - baseSphericalFacetArea);
            show.add(delta);
            if (delta < epsilon)
            {
                epsilon = delta;
                facet = i;
            }

            // distortion
            distortionPlSp = new ArrayList<>(3);
            double disPl = 4 * Math.PI * areaPlSp.get(0) / Math.pow(perimeterPlSp.get(0), 2);
            distortionPlSp.add(disPl);
            double aSp = areaPlSp.get(1);
            double pSp2 = Math.pow(perimeterPlSp.get(1), 2);
            double r2 = Math.pow(Constant.radius, 2);
            double disSp = (4 * Math.PI * r2 * aSp - Math.pow(aSp, 2)) / (r2 * pSp2);
            distortionPlSp.add(disSp);
            distortionPlSp.add(disPl / disSp);
            distortion.add(distortionPlSp);
        }
        System.out.println("AverageSphereArea = " + baseSphericalFacetArea);
//        System.out.println("Perimeter = " + 3 * Math.sqrt(2) * Constant.radius);
        System.out.print(facet);
        System.out.println(", spAreaDelta = " + epsilon);
        System.out.println("Area        " + area.get(facet));
        System.out.println("Perimeter   " + perimeter.get(facet));
        System.out.println("++++++++++++++++++++++++++");

//        System.out.println("Area: " + area);
//        System.out.println("Peri: " + perimeter);
//        System.out.println("Dist: " + distortion);
        for (int i = 0; i < facesNumber; i++)
        {
            System.out.println("AreaDelta = " + show.get(i));
//            System.out.println("A: " + area.get(i));
//            System.out.println("P: " + perimeter.get(i));
//            System.out.println("D: " + distortion.get(i));
        }
        for (int i = 0; i < facesNumber; i++)
        {
//            System.out.println("AreaDelta = " + show);
            System.out.println("A: " + area.get(i));
//            System.out.println("P: " + perimeter.get(i));
//            System.out.println("D: " + distortion.get(i));
        }
        for (int i = 0; i < facesNumber; i++)
        {
//            System.out.println("AreaDelta = " + show);
//            System.out.println("A: " + area.get(i));
            System.out.println("P: " + perimeter.get(i));
//            System.out.println("D: " + distortion.get(i));
        }
        for (int i = 0; i < facesNumber; i++)
        {
//            System.out.println("AreaDelta = " + show);
//            System.out.println("A: " + area.get(i));
//            System.out.println("P: " + perimeter.get(i));
            System.out.println("D: " + distortion.get(i));
        }
    }
}
