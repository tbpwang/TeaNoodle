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
 * @create 2019/4/30 11:04
 * @description
 * @parameter
 */
public class MiddleArcTriangle extends Cell
{
    public MiddleArcTriangle(LatLon top, LatLon left, LatLon right, String ID)
    {
        super(top, left, right, ID);
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

    @Override
    public Cell[] refine()
    {
        //         LatLon.interpolate(AVKey.GREAT_CIRCLE,0.5,getLeft(),getRight());
        LatLon a = LatLon.interpolateGreatCircle(0.5,getLeft(),getRight());
        LatLon b = LatLon.interpolateGreatCircle(0.5,getTop(),getRight());
        LatLon c = LatLon.interpolateGreatCircle(0.5,getLeft(),getTop());
        String ID = this.getID();
        Cell[] subCells = new MiddleArcTriangle[4];
        // 中心对齐，整体全等
        // 关于过顶点的中心线对称
        subCells[0] = new MiddleArcTriangle(a,b,c,ID + "0");
        subCells[1] = new MiddleArcTriangle(getTop(),c,b,ID + "1");
        subCells[2] = new MiddleArcTriangle(getLeft(),a,c,ID + "2");
        subCells[3] = new MiddleArcTriangle(getRight(),a,b,ID + "3");
        return subCells;
    }
}
