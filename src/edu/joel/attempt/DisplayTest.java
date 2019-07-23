/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.attempt;

import edu.joel.*;
import edu.joel.model.*;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Zheng WANG
 * @create 2019/5/7
 * @description 显示图形，测试程序
 * @parameter
 */
public class DisplayTest extends ApplicationTemplate
{
    public static void main(String[] args)
    {
        start("TestShow", TestShowAPP.class);
    }

    public static class TestShowAPP extends ApplicationTemplate.AppFrame
    {
        public TestShowAPP()
        {
            int level = 3;
//            LatLon p1 = LatLon.fromDegrees(90, 90);
//            LatLon p2 = LatLon.fromDegrees(0, 0);
//            LatLon p3 = LatLon.fromDegrees(0, 90);
//            List<LatLon> list = new ArrayList<>();
//            list.add(p1);
//            list.add(p2);
//            list.add(p3);
//            ZhaoQTM initCell = new ZhaoQTM(p1, p2, p3, new Geocode("A"));
//            getWwd().getView().setEyePosition(
//                new Position(Position.getCenter(list), DGGS.getGlobe().getRadius() * 1.8));
//
//            Mesh mesh = fill(initCell, level);
//            RenderableLayer layer = new RenderableLayer();
//            layer.setName("ZhaoQTM");
//            for (List<Mesh.Node> nodeList : mesh.getNodes())
//            {
//                for (Mesh.Node node : nodeList)
//                {
//                    ZhaoQTM cell = (ZhaoQTM) node.baseTriangle();
//                    for (Path path : cell.renderPath())
//                    {
//                        layer.addRenderable(path);
//                    }
//                }
//            }
//            insertBeforeCompass(getWwd(), layer);

            List<SphericalTriangleOctahedron> initTriangles = new ArrayList<>();
            initTriangles.add(SphericalTriangleOctahedron.first);
            initTriangles.add(SphericalTriangleOctahedron.second);
            initTriangles.add(SphericalTriangleOctahedron.third);
            initTriangles.add(SphericalTriangleOctahedron.fourth);
            initTriangles.add(SphericalTriangleOctahedron.fifth);
            initTriangles.add(SphericalTriangleOctahedron.sixth);

            RenderableLayer lyr;
            for (int i = 0; i < initTriangles.size(); i++)
            {
                lyr = new RenderableLayer();
                lyr.setName("MiddleArcTri" + i);
//                MiddleArcTriangle triangle = new MiddleArcTriangle(p1, p2, p3, new Geocode("M"));
                MiddleArcSurfaceTriangle triangle = (MiddleArcSurfaceTriangle)initTriangles.get(i).baseTriangle();
                Mesh triMesh = fill(triangle, level);

                for (Mesh.Node node : triMesh.getNodes())
                {
                    MiddleArcSurfaceTriangle cell = (MiddleArcSurfaceTriangle) node.getCell();
//                        for (Path path : cell.renderPath(setAttribute(i * 2)))
                    for (Path path : cell.renderPath(setAttribute((i + 1) * 20, (i + 1) * 120, (i + 1) * 20)))
                    {
                        lyr.addRenderable(path);
                    }
                }

                insertBeforeCompass(getWwd(), lyr);
            }
        }

        private ShapeAttributes setAttribute()
        {
            ShapeAttributes attr = new BasicShapeAttributes();
            attr.setOutlineMaterial(new Material(new Color(50, 150, 50)));
            attr.setOutlineWidth(2.0);
            attr.setOutlineStippleFactor(3);
//            attr.setOutlineStipplePattern();
            return attr;
        }

        private ShapeAttributes setAttribute(int stippleFactor)
        {
            ShapeAttributes attr = new BasicShapeAttributes();
            attr.setOutlineMaterial(new Material(new Color(50, 150, 50)));
            attr.setOutlineWidth(2.0);
            attr.setOutlineStippleFactor(stippleFactor);
//            attr.setOutlineStipplePattern();
            return attr;
        }

        private ShapeAttributes setAttribute(int red, int green, int blue)
        {
            ShapeAttributes attr = new BasicShapeAttributes();
            attr.setOutlineMaterial(new Material(new Color(red % 256, green % 256, blue % 256)));
            attr.setOutlineWidth(3.0);
            attr.setOutlineStippleFactor(0);
//            attr.setOutlineStipplePattern();
            return attr;
        }

        private Mesh fill(MiddleArcSurfaceTriangle initCell, int level)
        {
            List<MiddleArcSurfaceTriangle> list = new ArrayList<>();
            list.add(initCell);
            Attempt.refineTriangle(level, list);
            Mesh mesh = new TriangleMesh(level);
            for (MiddleArcSurfaceTriangle cell : list)
            {
                mesh.addNode(cell);
            }
            return mesh;
        }

        private Mesh fill(ZhaoSurfaceTriangle initCell, int level)
        {
            List<ZhaoSurfaceTriangle> list = new ArrayList<>();
            list.add(initCell);
            List<ZhaoSurfaceTriangle> temp = new ArrayList<>();
            for (int i = 0; i < level; i++)
            {
                for (ZhaoSurfaceTriangle cell : list)
                {
                    temp.addAll(Arrays.asList(cell.refine()));
                }
                list.clear();
                list.addAll(temp);
                temp.clear();
            }
            Mesh mesh = new TriangleMesh(level);
            for (ZhaoSurfaceTriangle cell : list)
            {
                mesh.addNode(cell);
            }
            return mesh;
        }
    }
}
