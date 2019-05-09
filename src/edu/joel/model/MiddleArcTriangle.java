/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.model;

import edu.joel.*;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.Logging;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/4/30 11:04
 * @description
 * @parameter
 */
public class MiddleArcTriangle extends Cell
{
    private boolean isUp = true;

    public MiddleArcTriangle(LatLon top, LatLon left, LatLon right, String ID)
    {
        super(top, left, right, ID);
        double epsilon = 1e-11;
        if (top.latitude.cos() - this.getCenter().latitude.cos() > epsilon)
        {
            isUp = false;
        }
    }

    public MiddleArcTriangle(List<LatLon> list, String ID)
    {
        super(list, ID);
        if (list.size() == 4 && !list.get(0).equals(list.get(3)) || list.size() > 4)
        {
            String message = Logging.getMessage("errorValue.ValueExceeds");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        double epsilon = 1e-11;
        if (list.get(0).latitude.cos() - this.getCenter().latitude.cos() > epsilon)
        {
            isUp = false;
        }
    }

    @Override
    public double getUnitArea()
    {
        int length = getGeoVertices().size();
        double excess = -Math.PI * (length - 1 - 2);
        LatLon startPoint, midPoint, endPoint;
        // 临时三角形的周长p、以及角度ABC对应的边abc
        double p, a, b, c;
        for (int i = 0; i < length - 1; i++)
        {
            if (i == 0)
            {
                startPoint = getGeoVertices().get(length - 2);
                midPoint = getGeoVertices().get(0);
                endPoint = getGeoVertices().get(1);
            }
            else
            {
                startPoint = getGeoVertices().get(i - 1);
                midPoint = getGeoVertices().get(i);
                endPoint = getGeoVertices().get(i + 1);
            }
            c = LatLon.greatCircleDistance(startPoint, midPoint).radians;
            b = LatLon.greatCircleDistance(endPoint, midPoint).radians;
            a = LatLon.greatCircleDistance(startPoint, endPoint).radians;
            p = (a + b + c) / 2;
            excess += 2 * Math.asin(Math.sqrt(Math.sin(p - c) * Math.sin(p - b) / Math.sin(c) / Math.sin(b)));
        }
        return excess;
    }

    @Override
    public double computeArea()
    {
        return getUnitArea() * DGGS.getGlobe().getRadius() * DGGS.getGlobe().getRadius();
    }

    public boolean isUp()
    {
        return isUp;
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

    public SurfacePolygon[] renderPolygon()
    {
        SurfacePolygon polygon = new SurfacePolygon(DGGS.polygonAttribute(),getGeoVertices());
        polygon.setPathType(pathType);
        return new SurfacePolygon[]{polygon};
    }

    public Path[] renderPath(ShapeAttributes attr)
    {
        ShapeAttributes attributes;
        if (attr == null)
        {
            attributes = DGGS.pathAttribute();
        }
        else
        {
            attributes = attr;
        }
        List<Position> positions = new ArrayList<>();
        for (LatLon latLon : getGeoVertices())
        {
            positions.add(new Position(latLon, 0));
        }
        Path path = new Path(positions);
        path.setPathType(pathType);
//        path.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        path.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        path.setFollowTerrain(true);
        path.setAttributes(attributes);
        path.setShowPositions(true);
//        path.setDrawVerticals(true);
        return new Path[]{path};
    }
    public Path[] renderPath()
    {
        return renderPath(null);
    }

    @Override
    public MiddleArcTriangle[] refine()
    {
        //         LatLon.interpolate(AVKey.GREAT_CIRCLE,0.5,getLeft(),getRight());
        LatLon a = LatLon.interpolateGreatCircle(0.5, getLeft(), getRight());
        LatLon b = LatLon.interpolateGreatCircle(0.5, getTop(), getRight());
        LatLon c = LatLon.interpolateGreatCircle(0.5, getLeft(), getTop());
        String ID = this.getID();
        MiddleArcTriangle[] subCells = new MiddleArcTriangle[4];
        // 中心对齐，整体全等
        // 关于过顶点的中心线对称
//        subCells[0] = new MiddleArcTriangle(a, b, c, ID + "0");
//        subCells[1] = new MiddleArcTriangle(getTop(), c, b, ID + "1");
//        subCells[2] = new MiddleArcTriangle(getLeft(), a, c, ID + "2");
//        subCells[3] = new MiddleArcTriangle(getRight(), a, b, ID + "3");
        // Goodchild 编码法
        subCells[0] = new MiddleArcTriangle(a, c, b, ID + "0");
        subCells[1] = new MiddleArcTriangle(getTop(), c, b, ID + "1");
        subCells[2] = new MiddleArcTriangle(c, getLeft(), a, ID + "2");
        subCells[3] = new MiddleArcTriangle(b, a, getRight(), ID + "3");
        return subCells;
    }
}
