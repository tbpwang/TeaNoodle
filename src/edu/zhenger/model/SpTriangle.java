/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.util.Term;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;
import java.util.Arrays;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function: 修改编码方法，用于任意球面三角形的面积计算 剖分方法为：四孔均分弧法(ClassⅠMid-Arcs)
 * @Date: 2018/12/16
 */
public class SpTriangle //extends AreaMeasurer
{
    private SpTriangle[] children;

    private LatLon top;
    private LatLon left;
    private LatLon right;
    private String id;//geocode

    private double elevation;

    private String coordinate;

    private Position[] locations;

    public SpTriangle(LatLon top, LatLon left, LatLon right, String id)
    {
        if (top == null || left == null || right == null)
        {
            throw Term.nullError("nullValue.VertexIsNull");
        }
        this.top = new LatLon(top);
        this.left = new LatLon(left);
        this.right = new LatLon(right);
        this.id = id;
        this.children = new SpTriangle[4];
        this.coordinate = "";
        this.locations = new Position[3];
        this.elevation = 0.0;
    }

    public SpTriangle(LatLon top, LatLon left, LatLon right, String id,double elevation)
    {
        this(top, left, right, id);
        this.elevation = elevation;
    }

    public SpTriangle(LatLon top, double topElevation, LatLon left, double leftElevation, LatLon right,
        double rightElevation, String id)
    {
        this(top, left, right, id);
        setLocation(topElevation, leftElevation, rightElevation);
        this.elevation = (topElevation + leftElevation + rightElevation) / 3.0;
    }


    private void setLocation(double top, double left, double right)
    {
        this.locations[0] = new Position(this.top, top);
        this.locations[1] = new Position(this.left, left);
        this.locations[2] = new Position(this.right, right);
    }

    private void setLocation()
    {
        this.locations[0] = new Position(this.top, 0);
        this.locations[1] = new Position(this.left, 0);
        this.locations[2] = new Position(this.right, 0);
    }

    public Position[] getLocations()
    {
        if (this.locations == null)
        {
            setLocation();
        }
        return this.locations;
    }

    public SpTriangle[] getChildren()
    {
        LatLon pAb, pAc, pB, pC, a, b, c;
        pAb = getTop();
        pAc = getTop();
        pB = getLeft();
        pC = getRight();
        if (pAb.getLatitude().equals(Angle.NEG90) || pAb.getLatitude().equals(Angle.POS90))
        {
            pAb = LatLon.fromDegrees(getTop().latitude.degrees, pB.longitude.degrees);
        }
        if (pAc.getLatitude().equals(Angle.NEG90) || pAc.getLatitude().equals(Angle.POS90))
        {
            pAc = LatLon.fromDegrees(getTop().latitude.degrees, pC.longitude.degrees);
        }

        a = LatLon.interpolateGreatCircle(0.5, pB, pC);
        b = LatLon.interpolateGreatCircle(0.5, pC, pAc);
        c = LatLon.interpolateGreatCircle(0.5, pAb, pB);

//        // 判断三角形的形状，
//        // 如果三角形是等边的，则三个角的编号相同，flag = "RegularTriangle"；
//        // 如果三角形是等腰的，则两底三角形编号相同，flag = "IsoscelesTriangle"；
//        // 如果三角形是斜三角形，则编号都不同，flag = "ObliqueTriangle"。
//        Angle lAB, lAC, lBC;
//        lAB = LatLon.greatCircleDistance(pAb, pB);
//        lAC = LatLon.greatCircleDistance(pAc, pC);
//        lBC = LatLon.greatCircleDistance(pB, pC);
//
//        // 判断两个数值近似相等
//        // String flag = "ObliqueTriangle";
//        // 斜三角形
//        String child2ID = "2";
//        String child3ID = "3";
//        double epsilon = 1e-6;
//        if (lAB.subtract(lAC).divide(lAC) < epsilon && lAB.subtract(lAC).divide(lAC) > -epsilon)
//        {
//            // flag = "IsoscelesTriangle";
//            // 等腰三角形
//            child2ID = "2";
//            child3ID = "2";
//            if (lAB.subtract(lBC).divide(lBC) < epsilon && lAB.subtract(lBC).divide(lBC) > -epsilon)
//            {
//                // flag = "RegularTriangle";
//                // 等边三角形
//                child2ID = "1";
//                child3ID = "1";
//            }
//        }

        // 编码方法为改进Goodchild编码，我们称为：顶点对称编码法
        children[0] = new SpTriangle(a, c, b, getID() + "0");
        children[1] = new SpTriangle(pAb, c, b, getID() + "1");
//        children[2] = new SpTriangle(pB, c, a, getID() + child2ID);
//        children[3] = new SpTriangle(pC, b, a, getID() + child3ID);
        children[2] = new SpTriangle(pB, a, c, getID() + "2");
        children[3] = new SpTriangle(pC, a, b, getID() + "3");

        return children;
    }

    public static class Area
    {
        public static double computer(LatLon top, LatLon left, LatLon right)
        {
            return computer(top, left, right, 1.0);
        }

        public static double computer(LatLon top, LatLon left, LatLon right, double radius)
        {
            if (top == null || left == null || right == null)
            {
                throw Term.nullError("nullValue.PointIsNull");
            }

//        WWMath.computePolygonAreaFromVertices()
            // getSphereArea
            //已知三边abc求面积
            //公式来源：一般球面三角形计算公式
            //数学手册编写组，数学手册，北京：高等教育出版社，2010年印，p49-50.
            //公式半角: sin(A/2)=sqrt(sin(p-b)*sin(p-c)/(sin(b)*sin(c)))
            // S = (A+B+C-PI)*R^2

            LatLon lTop, rTop;

            if (top.latitude.equals(Angle.POS90) || top.latitude.equals(Angle.NEG90))
            {
                lTop = LatLon.fromDegrees(top.latitude.degrees, left.longitude.degrees);
                rTop = LatLon.fromDegrees(top.latitude.degrees, right.longitude.degrees);
            }
            else
            {
                lTop = top;
                rTop = top;
            }

            // bounders
            //vertex
            double a, b, c, p, A, B, C;

            a = LatLon.greatCircleDistance(left, right).getRadians();
            b = LatLon.greatCircleDistance(right, rTop).getRadians();
            c = LatLon.greatCircleDistance(lTop, left).getRadians();
            // half-side of triangle
            p = (a + b + c) / 2;

            A = 2 * Math.asin(Math.sqrt(Math.sin(p - b) * Math.sin(p - c) / (Math.sin(b) * Math.sin(c))));
            B = 2 * Math.asin(Math.sqrt(Math.sin(p - c) * Math.sin(p - a) / (Math.sin(c) * Math.sin(a))));
            C = 2 * Math.asin(Math.sqrt(Math.sin(p - a) * Math.sin(p - b) / (Math.sin(a) * Math.sin(b))));

            double sigma = radius == 1.0 ? 1000.0 / 6378137 : 1000.0 / radius;

            return Math.pow(radius, 2) * (a > sigma && b > sigma && c > sigma ? (A + B + C - Math.PI)
                : Math.sqrt(p * (p - a) * (p - b) * (p - c)));
        }
    }

    // 标准化到单位球上的面积
    public double getUnitArea()
    {
        return SpTriangle.Area.computer(top, left, right);
    }

    public double getArea()
    {
        return SpTriangle.Area.computer(top, left, right, Term.getSphere().getRadius());
    }

    public LatLon getTop()
    {
        return top;
    }

    public LatLon getLeft()
    {
        return left;
    }

    public LatLon getRight()
    {
        return right;
    }

    public String getID()
    {
        return id;
    }

    public Path[] getBorders()
    {
        Position posA, posB, posC;
        posA = new Position(top, 0);
        posB = new Position(left, 0);
        posC = new Position(right, 0);
        Path[] borders = new Path[3];
        borders[0] = Term.defaultLine(posA, posB);
        borders[1] = Term.defaultLine(posB, posC);
        borders[2] = Term.defaultLine(posC, posA);
        return borders;
    }

    public Position getCenter()
    {
        return new Position(LatLon.getCenter(Arrays.asList(getLocations())),this.elevation);
    }

    public SurfacePolygon getRendererTriangle()
    {
        SurfacePolygon rendererTriangle = new SurfacePolygon();
        rendererTriangle.setLocations(Arrays.asList(getLocations()));
        rendererTriangle.setAttributes(Term.defaultAttribute(true));
        return rendererTriangle;
    }

    public String getUnitCoordinate(Boolean isLatLon)
    {
        if (isLatLon)
        {
            coordinate = outputLatLon(this.getTop()) + " " + outputLatLon(this.getLeft()) + " " + outputLatLon(
                this.getRight());
        }
        else
            coordinate = outputVec4(Term.fromLatLon(this.getTop())) + " " + outputVec4(
                Term.fromLatLon(this.getLeft())) + " "
                + outputVec4(Term.fromLatLon(this.getRight()));
        return coordinate;
    }

    private String outputLatLon(LatLon latLon)
    {
        if (latLon == null)
            throw Term.nullError("LatLonIsNull");

        return "(" + String.format("%.3f", latLon.getLongitude().getDegrees()) + "," + String.format("%.3f",
            latLon.getLatitude().getDegrees()) + ")";
    }

    private String outputVec4(Vec4 vec4)
    {
        if (vec4 == null)
            throw Term.nullError("Vec4IsNull");

        return "(" + String.format("%.3f", vec4.getX()) + "," + String.format("%.3f", vec4.getY()) + ","
            + String.format("%.3f", vec4.getZ()) + ")";
    }
}
