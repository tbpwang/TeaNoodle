/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.model;

import edu.joel.*;
import edu.joel.SurfaceTriangle;
import edu.joel.io.Constant;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/4/30 11:04
 * @description
 * @parameter
 */
public class MiddleArcSurfaceTriangle extends SurfaceTriangle
{
    private Geocode geocode;

    public MiddleArcSurfaceTriangle(LatLon top, LatLon left, LatLon right, Geocode geocode)
    {
        super(top, left, right, geocode.getID());
        this.geocode = geocode;
    }

    public static MiddleArcSurfaceTriangle cast(SurfaceTriangle triangle)
    {
        if (triangle == null)
        {
            return null;
        }
        return new MiddleArcSurfaceTriangle(triangle.getTop(),triangle.getLeft(),triangle.getRight(),triangle.getGeocode());
    }

    public double computeArea()
    {
        return getUnitArea() * Constant.getGlobe().getRadius() * Constant.getGlobe().getRadius();
    }

    public SurfacePolygon[] renderPolygon()
    {
        SurfacePolygon polygon = new SurfacePolygon(Constant.defaultPolygonAttribute(), getGeoVertices());
//        polygon.setPathType(pathType);
        polygon.setPathType(AVKey.GREAT_CIRCLE);
        return new SurfacePolygon[] {polygon};
    }

    public Path[] renderPath(ShapeAttributes attr)
    {
        ShapeAttributes attributes;
        if (attr == null)
        {
            attributes = Constant.defaultPathAttribute();
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
//        path.setPathType(pathType);
        path.setPathType(AVKey.GREAT_CIRCLE);
//        path.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        path.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        path.setFollowTerrain(true);
        path.setAttributes(attributes);
        path.setShowPositions(true);
//        path.setDrawVerticals(true);
        return new Path[] {path};
    }

    public Geocode getGeocode()
    {
        return geocode;
    }

    public Path[] renderPath()
    {
        return renderPath(null);
    }

    @Override
    public MiddleArcSurfaceTriangle[] refine()
    {
        //         LatLon.interpolate(AVKey.GREAT_CIRCLE,0.5,getLeft(),getRight());
        LatLon a = LatLon.interpolateGreatCircle(0.5, getLeft(), getRight());
        LatLon b = LatLon.interpolateGreatCircle(0.5, getTop(), getRight());
        LatLon c = LatLon.interpolateGreatCircle(0.5, getLeft(), getTop());
//        String ID = this.getGeocode().getID();
        MiddleArcSurfaceTriangle[] subCells = new MiddleArcSurfaceTriangle[4];
        // 中心对齐，整体全等
        // 关于过顶点的中心线对称
//        subCells[0] = new MiddleArcTriangle(a, b, c, ID + "0");
//        subCells[1] = new MiddleArcTriangle(getTop(), c, b, ID + "1");
//        subCells[2] = new MiddleArcTriangle(getLeft(), a, c, ID + "2");
//        subCells[3] = new MiddleArcTriangle(getRight(), a, b, ID + "3");
        // Goodchild 编码法
        // Goodchild 编码下的行列号
        Geocode[] geocode = this.geocode.binaryRefine();
//        geocode[0] = new Geocode(ID + "0");
//        geocode[1] = new Geocode(ID + "1");
//        geocode[2] = new Geocode(ID + "2");
//        geocode[3] = new Geocode(ID + "3");
//        int row, column;
//        row = 2 * this.geocode.getRow();
//        column = 2 * this.geocode.getColumn();
//        if (isUp)
//        {
//            geocode[0].setRow(row - 1);
//            geocode[1].setRow(row);
//            geocode[2].setRow(row - 1);
//            geocode[3].setRow(row - 1);
//            geocode[0].setColumn(column);
//            geocode[1].setColumn(column - 1);
//            geocode[2].setColumn(column - 1);
//            geocode[3].setColumn(column + 1);
//        }
//        else
//        {
//            geocode[0].setRow(row);
//            geocode[1].setRow(row - 1);
//            geocode[2].setRow(row);
//            geocode[3].setRow(row);
//            geocode[0].setColumn(column - 1);
//            geocode[1].setColumn(column);
//            geocode[2].setColumn(column - 2);
//            geocode[3].setColumn(column);
//        }

        subCells[0] = new MiddleArcSurfaceTriangle(a, c, b, geocode[0]);
        subCells[1] = new MiddleArcSurfaceTriangle(getTop(), c, b, geocode[1]);
        subCells[2] = new MiddleArcSurfaceTriangle(c, getLeft(), a, geocode[2]);
        subCells[3] = new MiddleArcSurfaceTriangle(b, a, getRight(), geocode[3]);
        return subCells;
    }
}
