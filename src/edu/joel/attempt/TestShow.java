/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.attempt;

import edu.joel.*;
import edu.joel.model.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Zheng WANG
 * @create 2019/5/7 14:22
 * @description
 * @parameter
 */
public class TestShow extends ApplicationTemplate
{
    public static void main(String[] args)
    {
        start("TestShow", TestShowAPP.class);
    }

    public static class TestShowAPP extends ApplicationTemplate.AppFrame
    {
        public TestShowAPP()
        {
            LatLon p1 = LatLon.fromDegrees(90, 90);
            LatLon p2 = LatLon.fromDegrees(0, 0);
            LatLon p3 = LatLon.fromDegrees(0, 90);
            List<LatLon> list = new ArrayList<>();
            list.add(p1);
            list.add(p2);
            list.add(p3);
            getWwd().getView().setEyePosition(new Position(Position.getCenter(list),DGGS.getGlobe().getRadius()*1.8));

            RenderableLayer layer = new RenderableLayer();
            layer.setName("ZhaoQTM");
            ZhaoQTM initCell = new ZhaoQTM(p1, p2, p3, "A");
            Mesh mesh = fill(initCell, 4);
            for (List<Mesh.Node> nodeList : mesh.getNodes())
            {
                for (Mesh.Node node : nodeList)
                {
                    ZhaoQTM cell = (ZhaoQTM) node.getCell();
                    for (Path path : cell.renderPath())
                    {
                        layer.addRenderable(path);
                    }
                }
            }
            insertBeforeCompass(getWwd(), layer);

            RenderableLayer lyr = new RenderableLayer();
            lyr.setName("MiddleArcTri");
            MiddleArcTriangle triangle = new MiddleArcTriangle(p1, p2, p3, "M");
            Mesh triMesh = fill(triangle, 4);
            for (List<Mesh.Node> nodeList : triMesh.getNodes())
            {
                for (Mesh.Node node : nodeList)
                {
                    MiddleArcTriangle cell = (MiddleArcTriangle) node.getCell();
                    for (Path path : cell.renderPath(setAttribute()))
                    {
                        lyr.addRenderable(path);
                    }
                }
            }
            insertBeforeCompass(getWwd(),lyr);
        }

        private ShapeAttributes setAttribute()
        {
            ShapeAttributes attr = new BasicShapeAttributes();
            attr.setOutlineMaterial(new Material(new Color(50,150,50)));
            attr.setOutlineWidth(2.0);
            attr.setOutlineStippleFactor(3);
//            attr.setOutlineStipplePattern();
            return attr;
        }
        private Mesh fill(MiddleArcTriangle initCell, int level)
        {
            List<MiddleArcTriangle> list = new ArrayList<>();
            list.add(initCell);
            List<MiddleArcTriangle> temp = new ArrayList<>();
            for (int i = 0; i < level; i++)
            {
                for (MiddleArcTriangle cell : list)
                {
                    temp.addAll(Arrays.asList(cell.refine()));
                }
                list.clear();
                list.addAll(temp);
                temp.clear();
            }
            Mesh mesh = new TriangleMesh(level);
            for (MiddleArcTriangle cell : list)
            {
                mesh.add(cell);
            }
            return mesh;
        }

        private Mesh fill(ZhaoQTM initCell, int level)
        {
            List<ZhaoQTM> list = new ArrayList<>();
            list.add(initCell);
            List<ZhaoQTM> temp = new ArrayList<>();
            for (int i = 0; i < level; i++)
            {
                for (ZhaoQTM cell : list)
                {
                    temp.addAll(Arrays.asList(cell.refine()));
                }
                list.clear();
                list.addAll(temp);
                temp.clear();
            }
            Mesh mesh = new TriangleMesh(level);
            for (ZhaoQTM cell : list)
            {
                mesh.add(cell);
            }
            return mesh;
        }
    }
}
