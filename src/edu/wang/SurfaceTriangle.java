/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.wang;

import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.render.Path;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/5/16
 * @description
 * @parameter
 */
public class SurfaceTriangle extends Cell
{
    public SurfaceTriangle(LatLon top, LatLon left, LatLon right, String ID)
    {
        super(top, left, right, ID);
    }

    public boolean isUp()
    {
        return this.getGeocode().isUp();
    }

    public LatLon getTop()
    {
        return getGeoVertices().get(0);
    }

    public LatLon getLeft()
    {
        return getGeoVertices().get(1);
    }

    public LatLon getRight()
    {
        return getGeoVertices().get(2);
    }

    public List<Double> lengthOfEdgeDegree()
    {
        List<Double> edges = new ArrayList<>();
        edges.add(LatLon.greatCircleDistance(getLeft(), getRight()).getDegrees());// a
        edges.add(LatLon.greatCircleDistance(getTop(), getRight()).getDegrees());// b
        edges.add(LatLon.greatCircleDistance(getLeft(), getTop()).getDegrees());// c
        return edges;
    }

    public List<Double> interiorAngleDegree()
    {
        List<Double> radians = interiorAngle();
        List<Double> degrees = new ArrayList<>();
        double constant = 180 / Math.PI;
        degrees.add(radians.get(0) * constant);
        degrees.add(radians.get(1) * constant);
        degrees.add(radians.get(2) * constant);
        return degrees;
    }

    public List<Double> interiorAngle()
    {
        List<Double> edges = lengthOfEdgeDegree();
        double p, a, b, c;
        double top, left, right;
//        a = LatLon.greatCircleDistance(getLeft(), getRight()).radians;
//        c = LatLon.greatCircleDistance(getLeft(), getTop()).radians;
//        b = LatLon.greatCircleDistance(getTop(), getRight()).radians;
        double degree2rad = Math.PI / 180;
        a = edges.get(0) * degree2rad;
        b = edges.get(1) * degree2rad;
        c = edges.get(2) * degree2rad;
        p = (a + b + c) / 2.0;
        top = 2 * Math.asin(Math.sqrt(Math.sin(p - c) * Math.sin(p - b) / Math.sin(c) / Math.sin(b)));
        left = 2 * Math.asin(Math.sqrt(Math.sin(p - c) * Math.sin(p - a) / Math.sin(c) / Math.sin(a)));
        right = 2 * Math.asin(Math.sqrt(Math.sin(p - a) * Math.sin(p - b) / Math.sin(a) / Math.sin(b)));
        List<Double> interiors = new ArrayList<>();
        interiors.add(top);
        interiors.add(left);
        interiors.add(right);
        return interiors;
    }

    public double computeArea()
    {
        return getUnitArea();
    }

    public double getUnitArea()
    {
        List<Double> interiors = interiorAngle();
        return (interiors.get(0) + interiors.get(1) + interiors.get(2) - Math.PI);

//        int length = getGeoVertices().size();
//        double excess = -Math.PI * (length - 1 - 2);
//        LatLon startPoint, midPoint, endPoint;
//        // 临时三角形的周长p、以及角度ABC对应的边abc
//        double p, a, b, c;
//        a= LatLon.greatCircleDistance(getLeft(),getRight()).radians;
//        c= LatLon.greatCircleDistance(getLeft(),getTop()).radians;
//        b= LatLon.greatCircleDistance(getTop(),getRight()).radians;
//        for (int i = 0; i < length - 1; i++)
//        {
//            if (i == 0)
//            {
//                startPoint = getGeoVertices().get(length - 2);
//                midPoint = getGeoVertices().get(0);
//                endPoint = getGeoVertices().get(1);
//            }
//            else
//            {
//                startPoint = getGeoVertices().get(i - 1);
//                midPoint = getGeoVertices().get(i);
//                endPoint = getGeoVertices().get(i + 1);
//            }
//            c = LatLon.greatCircleDistance(startPoint, midPoint).radians;
//            b = LatLon.greatCircleDistance(endPoint, midPoint).radians;
//            a = LatLon.greatCircleDistance(startPoint, endPoint).radians;
//            p = (a + b + c) / 2;
//            excess += 2 * Math.asin(Math.sqrt(Math.sin(p - c) * Math.sin(p - b) / Math.sin(c) / Math.sin(b)));
//        }
//        return excess;
    }

    @Override
    public Cell[] refine()
    {
        return null;
    }

    @Override
    public Path[] renderPath()
    {
        return null;
    }
}
