/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.model;

import edu.joel.*;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.util.Logging;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/5/8 16:23
 * @description
 * @parameter
 */
public class ZhaoQTM extends Cell//MiddleArcTriangle
{
    private boolean isUp = true;

    public ZhaoQTM(LatLon top, LatLon left, LatLon right, String ID)
    {
        super(top, left, right, ID);
        double epsilon = 1e-13;
//        if (Math.abs(left.latitude.degrees - right.latitude.degrees) > epsilon)
//        {
//            String message = Logging.getMessage("errorModel.NotZhaoQTM");
//            Logging.logger().severe(message);
//            throw new IllegalArgumentException(message);
//        }
        if (top.latitude.cos() - this.getCenter().latitude.cos() > epsilon)
        {
            isUp = false;
        }
    }

    public ZhaoQTM(List<LatLon> list, String ID)
    {
        super(list, ID);
        double epsilon = 1e-13;
//        if (Math.abs(list.get(1).latitude.degrees - list.get(2).latitude.degrees) > epsilon)
//        {
//
//            String message = Logging.getMessage("errorModel.NotZhaoQTM");
//            Logging.logger().severe(message);
//            throw new IllegalArgumentException(message);
//        }

        if (list.get(0).latitude.cos() - this.getCenter().latitude.cos() > epsilon)
        {
            isUp = false;
        }
    }

    public boolean isUp()
    {
        return isUp;
    }

    @Override
    public double getUnitArea()
    {
        // TODO: 计算单位圆中ZhaoQTM方法的单元格面积
        return 0;
    }

    @Override
    public double computeArea()
    {
        return getUnitArea()*DGGS.getGlobe().getRadius()*DGGS.getGlobe().getRadius();
    }

    @Override
    public Path[] renderPath()
    {
        List<Position> positions = new ArrayList<>();
        // 两边为大圆弧 AVKey.GREAT_CIRCLE
        // 底边为纬线即恒向线 AVKey.RHUMB_LINE
        Path path1, path2;

        positions.add(new Position(getGeoVertices().get(2), 0));
        positions.add(new Position(getGeoVertices().get(0), 0));
        positions.add(new Position(getGeoVertices().get(1), 0));
        path1 = new Path(positions);
        path1.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        path1.setFollowTerrain(true);
        path1.setPathType(AVKey.GREAT_CIRCLE);
        path1.setAttributes(DGGS.pathAttribute());

        path2 = new Path(new Position(getGeoVertices().get(1), 0), new Position(getGeoVertices().get(2), 0));
        path2.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        path2.setFollowTerrain(true);
        path2.setPathType(AVKey.RHUMB_LINE);
        path2.setAttributes(DGGS.pathAttribute());

        return new Path[] {path1, path2};
    }

    @Override
    public ZhaoQTM[] refine()
    {
        // 两边是大圆弧 GreatCircle
        // 底边沿着纬线 Rhumb
//        LatLon a = LatLon.fromDegrees(getGeoVertices().get(1).latitude.degrees,
//            Angle.average(getGeoVertices().get(1).longitude, getGeoVertices().get(2).longitude).degrees);
        LatLon a = LatLon.interpolateRhumb(0.5,getGeoVertices().get(1),getGeoVertices().get(2));
        LatLon b = LatLon.interpolateGreatCircle(0.5, getGeoVertices().get(0), getGeoVertices().get(2));
        LatLon c = LatLon.interpolateGreatCircle(0.5, getGeoVertices().get(1), getGeoVertices().get(0));
        String ID = this.getID();
        ZhaoQTM[] subCells = new ZhaoQTM[4];
        // 中心对齐，整体全等
        // 关于过顶点的中心线对称
//        subCells[0] = new ZhaoQTM(a, b, c, ID + "0");
//        subCells[1] = new ZhaoQTM(getGeoVertices().get(0), c, b, ID + "1");
//        subCells[2] = new ZhaoQTM(getGeoVertices().get(1), a, c, ID + "2");
//        subCells[3] = new ZhaoQTM(getGeoVertices().get(2), a, b, ID + "3");
//        // Goodchild 编码法
        subCells[0] = new ZhaoQTM(a, c, b, ID + "0");
        subCells[1] = new ZhaoQTM(getGeoVertices().get(0), c, b, ID + "1");
        subCells[2] = new ZhaoQTM(c, getGeoVertices().get(1), a, ID + "2");
        subCells[3] = new ZhaoQTM(b, a, getGeoVertices().get(2), ID + "3");
        return subCells;
    }
}
