/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.model;

import edu.joel.io.Constant;

/**
 * @author Zheng WANG
 * @create 2019/9/21
 * @description based on M. Basaraner & S. Cetinkaya 2019
 */
public class Compactness
{
//    private double k_plane;
//    private double C_plane;
//    private double theta;
//    private double k_sphere;
//    private double C_sphere;

    public static double getPlaneCompactness(double area, double perimeter)
    {
        double k = 4 * Math.PI;
        return perimeter == 0.0 ? 0.0 : k * area / perimeter / perimeter;
    }

    public static double getSphereCompactness(double area, double perimeter, double radius)
    {
        double c = 0;
        if (radius != 0.0 && perimeter != 0.0)
        {
            c = (4 * Math.PI * area * radius * radius - area * area) / Math.pow(radius * perimeter, 2);
        }
        return c;
    }

    public static double getSphereCompactness(double area, double perimeter)
    {
        double radius = Constant.radius;
        return perimeter == 0.0 ? 0.0
            : (4 * Math.PI * area * radius * radius - area * area) / Math.pow(radius * perimeter, 2);
    }

    public static double getShapeDistortion(double planeArea, double planePerimeter, double sphereArea,
        double spherePerimeter)
    {
        return getSphereCompactness(sphereArea, spherePerimeter) == 0.0 ? 0.0
            : getPlaneCompactness(planeArea, planePerimeter) / getSphereCompactness(sphereArea, spherePerimeter);
    }

    public static double getShapeDistortion(double planeArea, double planePerimeter, double sphereArea,
        double spherePerimeter, double radius)
    {
        return getSphereCompactness(sphereArea, spherePerimeter, radius) == 0.0 ? 0.0
            : getPlaneCompactness(planeArea, planePerimeter) / getSphereCompactness(sphereArea, spherePerimeter,
                radius);
    }
}
