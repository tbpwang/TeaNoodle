/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.Logging;
import sun.rmi.runtime.Log;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Zheng WANG
 * @create 2019/4/28
 * @description 闭合基本单元格，attributes of a cell
 * @parameter 构成单元格的顶点（初始顶点和终结顶点相同），地理编码ID
 */
public abstract class Cell extends SurfacePolygon implements Area, Refinement
{
    // domain
    //private Globe globe;

    //    // 表示单元格的基本形状取值为：
//    // "TRI"(3),"QUA"(4),"HEX"(6)....
    private int shape;
    private int level;

    private List<LatLon> geoVertices;

    // reference point
    private LatLon center;

    // Geo-coding
    private String ID;
//    private boolean flag;

    private List<Neighbor> neighbors;

    public Cell(Iterable<? extends LatLon> locations, String ID, int level)
    {
        if (locations == null)
        {
            String message = Logging.getMessage("nullValue.VertexIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        geoVertices = new ArrayList<>();

        for (LatLon point :
            locations)
        {
            geoVertices.add(point);
        }

        shape = geoVertices.size() - 1;

        if (!geoVertices.get(0).equals(geoVertices.get(shape)))
        {
            geoVertices.add(geoVertices.get(0));
            shape++;
        }

        if (shape < 3)
        {
            String message = Logging.getMessage("lackValue.VertexLessThan3");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        //this.globe = DGGS.getGlobe();
        center = LatLon.getCenter(DGGS.getGlobe(), locations);
        this.ID = ID;
        this.level = level;
//        flag = false;
    }

    public Cell(Iterable<? extends LatLon> locations, String ID)
    {
        this(locations, ID, -1);
    }

    public Cell(LatLon top, LatLon left, LatLon right, String ID, int level)
    {
        if (top == null || left == null || right == null)
        {
            String message = Logging.getMessage("nullValue.VertexIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        geoVertices = new ArrayList<>();
        geoVertices.add(top);
        geoVertices.add(left);
        geoVertices.add(right);
        geoVertices.add(top);
        center = LatLon.getCenter(geoVertices);
        shape = 3;
        this.ID = ID;
        this.level = level;
//        flag = false;
    }

    public Cell(LatLon top, LatLon left, LatLon right, String ID)
    {
        this(top, left, right, ID, -1);
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        if (this.level != -1)
        {
            String message = Logging.getMessage("unchangeableValue.ValueIsUnchangeable");
            Logging.logger().severe(message);
            return;
        }
        this.level = level;
    }

    public List<Neighbor> getNeighbors()
    {
        return neighbors;
    }

    public void setNeighbor(Neighbor neighbor)
    {
        if (neighbor == null)
        {
            return;
        }
        if (getNeighbors() == null)
        {
            neighbors = new ArrayList<>();
        }
        neighbors.add(neighbor);
    }

    public abstract double getUnitArea();

    public abstract double computeArea();

    public abstract Cell[] refine();

    public abstract Path[] renderPath();

    public int getShape()
    {
        return shape;
    }

    public LatLon getCenter()
    {
        return center;
    }

    public String getID()
    {
        return ID;
    }

    public List<LatLon> getGeoVertices()
    {
        return geoVertices;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cell cell = (Cell) o;
        boolean isEqual = true;
        for (int i = 0; i < geoVertices.size(); i++)
        {
//            geoVertices.get(i).equals(cell.geoVertices.get(i));
            if (!LatLon.equals(geoVertices.get(i), cell.geoVertices.get(i)))
            {
                isEqual = false;
                break;
            }
        }
        if (cell.level != -1 && level != -1 && cell.level != level)
        {
            isEqual = false;
        }
        return isEqual && ID.equals(cell.ID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(geoVertices, ID, level);
    }

    @Override
    public String toString()
    {
        StringBuilder locations = new StringBuilder();

        for (LatLon ll : geoVertices)
        {
            locations.append(ll.toString());
        }

        return "Cell{" +
            "ID = '" + ID + '\'' +
            ", shape = " + shape +
            ", geoVertices = " + locations.toString() +
            '}';
    }
}
