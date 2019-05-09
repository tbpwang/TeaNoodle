/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.model;

import edu.joel.Cell;
import gov.nasa.worldwind.geom.LatLon;

/**
 * @author Zheng WANG
 * @create 2019/5/7 14:41
 * @description 包括正八面体球的所有面——八个球面三角形即球面八分体
 * @parameter
 */
public enum OctahedralSphere
{
    first(new MiddleArcTriangle(LatLon.fromDegrees(90.0, 0.0),LatLon.fromDegrees(0.0, 0.0),LatLon.fromDegrees(0.0, 90.0),"A")),
    second(new MiddleArcTriangle(LatLon.fromDegrees(90.0, 90.0),LatLon.fromDegrees(0.0, 90.0),LatLon.fromDegrees(0.0, 180.0),"B")),
    third(new MiddleArcTriangle(LatLon.fromDegrees(90.0, 180.0),LatLon.fromDegrees(0.0, 180.0),LatLon.fromDegrees(0.0, -90.0),"C")),
    fourth(new MiddleArcTriangle(LatLon.fromDegrees(90.0, -90.0),LatLon.fromDegrees(0.0, -90.0),LatLon.fromDegrees(0.0, 0.0),"D")),
    fifth(new MiddleArcTriangle(LatLon.fromDegrees(-90.0, 0.0),LatLon.fromDegrees(0.0, 0.0),LatLon.fromDegrees(0.0, 90.0),"E")),
    sixth(new MiddleArcTriangle(LatLon.fromDegrees(-90.0, 90.0),LatLon.fromDegrees(0.0, 90.0),LatLon.fromDegrees(0.0, 180.0),"F")),
    seventh(new MiddleArcTriangle(LatLon.fromDegrees(-90.0, 180.0),LatLon.fromDegrees(0.0, 180.0),LatLon.fromDegrees(0.0, -90.0),"G")),
    eighth(new MiddleArcTriangle(LatLon.fromDegrees(-90.0, -90.0),LatLon.fromDegrees(0.0, -90.0),LatLon.fromDegrees(0.0, 0.0),"H"));
    private Cell cell;
    OctahedralSphere(Cell cell)
    {
        this.cell = cell;
    }
    public Cell getCell()
    {
        return cell;
    }
}
