/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.SpTriangle;
import edu.zhenger.util.Term;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.formats.shapefile.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/31
 */
public class App extends ApplicationTemplate//EsriShapefile
{
    public static void main(String[] args)
    {
        start("Application", AppFrame.class);
    }

    public static class AppFrame extends ApplicationTemplate.AppFrame//EsriShapefile.AppFrame
    {
        public AppFrame()
        {
            super(true, true, false);

            int level = 0;

//            PolygonData polygonData = null;
//            Calculator calculator = null;

//            RenderableLayer trigon = new RenderableLayer();
//            trigon.setName("Trigon");
//            for (int i = 2; i < 3; i++)
//            {
//                outline(i);
//
//                calculator = new Calculator(level, i);
//                polygonData = new PolygonData(calculator.getGeocodeOrderCells());
//                for (SurfacePolygon sp : polygonData.getPolygons())
//                {
//                    trigon.addRenderable(sp);
//                }
//            }

//            getWwd().getModel().getLayers().add(trigon);
//             polygonData layers
            RenderableLayer layer = new RenderableLayer();
            //showConterminousUS(layer);
            showLevelOne(layer);
            getWwd().getModel().getLayers().add(layer);

            LatLonGraticuleLayer latLonLayer = new LatLonGraticuleLayer();
//            System.out.println(LatLonGraticuleLayer.GRATICULE_LATLON_LEVEL_0.length());

            insertBeforeCompass(getWwd(), latLonLayer);

            RenderableLayer layer1, layer2, layer3, layer11, layer21, layer31;
            SpTriangle scalene, isosceles, equal;

            // 不等边三角形
            scalene = new SpTriangle(
                LatLon.interpolateGreatCircle(0.2, LatLon.fromDegrees(0f, 0f), LatLon.fromDegrees(0f, 90f)),
                LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f, 0f), LatLon.fromDegrees(-90f, 0f)),
                LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f, 90f), LatLon.fromDegrees(-90f, 90f)),
                "Scalene");
            layer1 = triangleView(scalene);
            layer11 = triangleView(scalene, true);
            insertBeforeCompass(getWwd(), layer1);
            insertBeforeCompass(getWwd(), layer11);

            // 等腰三角形
            isosceles = new SpTriangle(
                LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f, 20f), LatLon.fromDegrees(0f, 90f)),
                LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f, 20f), LatLon.fromDegrees(-90f, 20f)),
                LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f, 90f), LatLon.fromDegrees(-90f, 90f)),
                "isosceles");
            layer2 = triangleView(isosceles);
            layer21 = triangleView(isosceles, true);
            insertBeforeCompass(getWwd(), layer2);
            insertBeforeCompass(getWwd(), layer21);

            // 等边三角形
            equal = new SpTriangle(
                LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f, 0f), LatLon.fromDegrees(0f, 90f)),
                LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f, 0f), LatLon.fromDegrees(-90f, 0f)),
                LatLon.interpolateGreatCircle(0.5, LatLon.fromDegrees(0f, 90f), LatLon.fromDegrees(-90f, 90f)),
                "equal");
            layer3 = triangleView(equal);
            layer31 = triangleView(equal, true);
            insertBeforeCompass(getWwd(), layer3);
            insertBeforeCompass(getWwd(), layer31);

            getWwd().getView().setEyePosition(Position.fromDegrees(-30, 40, 2 * Term.getSphere().getRadius()));
        }

        private RenderableLayer triangleView(SpTriangle sphericalTriangle)
        {
            return triangleView(sphericalTriangle, false);
        }

        private RenderableLayer triangleView(SpTriangle sphericalTriangle, Boolean subdivision)
        {
            RenderableLayer layer = new RenderableLayer();
//            SpTriangle supTriangle = new SpTriangle(top, left, right, name);
            String layerName = sphericalTriangle.getID() + (subdivision ? "Sub" : "");
            layer.setName(layerName);
            for (SpTriangle triangle :
                sphericalTriangle.getChildren())
            {
                if (!subdivision)
                {
                    for (Path border :
                        triangle.getBorders())
                    {
                        layer.addRenderable(border);
                    }
                }
                else
                {
                    for (SpTriangle subTriangle :
                        triangle.getChildren())
                    {
                        for (Path border :
                            subTriangle.getBorders())
                        {
                            layer.addRenderable(border);
                        }
                    }
                }
            }

            return layer;
        }

        private void showConterminousUS(RenderableLayer layer)
        {
            ShapeAttributes attributes = new BasicShapeAttributes();
            attributes.setInteriorOpacity(0.5);
            attributes.setInteriorMaterial(Material.BLUE);//BLACK

            String shpfileSource = "src/edu/zhenger/data/UnitedStates.shp";
            Shapefile shpfile = new Shapefile(shpfileSource);

            ShapefileRecordPolygon polygon = shpfile.nextRecord().asPolygonRecord();

            List<LatLon> latLons = new ArrayList<LatLon>();
            for (double[] d : polygon.getPoints(0))
            {
                latLons.add(LatLon.fromDegrees(d[1], d[0]));
            }

            SurfacePolygon shape = new SurfacePolygon(latLons);
            shape.setAttributes(attributes);
            shape.setPathType(AVKey.GREAT_CIRCLE);

            System.out.println("Area = " + shape.getArea(Term.getSphere()));

            //ShapefileRenderable renderable = new ShapefilePolygons(shpfile);
            //RenderableLayer layer = new RenderableLayer();
            layer.setName("ConterminousUS");
            layer.addRenderable(shape);
            insertBeforeCompass(getWwd(), layer);
        }

        private void outline(int facet)
        {
            ShapeAttributes attributes = new BasicShapeAttributes();
            attributes.setOutlineWidth(5);
//                attributes.setOutlineStippleFactor(1);
//                attributes.setOutlineStipplePattern((short) 1000);
            attributes.setOutlineMaterial(new Material(Color.MAGENTA));
            attributes.setOutlineStippleFactor(1);
            attributes.setOutlineStipplePattern((short) 1000);
            attributes.setInteriorOpacity(0.0);
            //attributes.setImageScale(1.0);
            //attributes.

            Calculator calculator = new Calculator(1, facet);
            PolygonData polygonData = new PolygonData(calculator.getGeocodeOrderCells());

            RenderableLayer outline = new RenderableLayer();
            outline.setName("outline" + facet);
            for (SurfacePolygon sp : polygonData.getPolygons())
            {
                sp.setAttributes(attributes);
                outline.addRenderable(sp);
            }
            insertBeforeCompass(getWwd(), outline);
        }

        private void showLevelOne(RenderableLayer layer)
        {
            ShapeAttributes attributes = new BasicShapeAttributes();
//            attributes.setInteriorOpacity(0.5);
//            attributes.setInteriorMaterial(Material.BLUE);//BLACK
            attributes.setOutlineMaterial(new Material(Color.MAGENTA));
            attributes.setOutlineWidth(20);

            List<LatLon> latLons = new ArrayList<LatLon>();
            latLons.add(LatLon.fromDegrees(90, -90));
            latLons.add(LatLon.fromDegrees(0, -90));
            latLons.add(LatLon.fromDegrees(-90, -90));
            latLons.add(LatLon.fromDegrees(-90, 0));
            latLons.add(LatLon.fromDegrees(0, 0));
            latLons.add(LatLon.fromDegrees(90, 0));
            latLons.add(LatLon.fromDegrees(90, 90));
            latLons.add(LatLon.fromDegrees(0, 90));
            latLons.add(LatLon.fromDegrees(-90, 90));
            latLons.add(LatLon.fromDegrees(-90, 180));
            latLons.add(LatLon.fromDegrees(0, 180));
            latLons.add(LatLon.fromDegrees(90, 180));
            latLons.add(LatLon.fromDegrees(90, -90));
            latLons.add(LatLon.fromDegrees(0, -90));
            latLons.add(LatLon.fromDegrees(0, 0));
            latLons.add(LatLon.fromDegrees(0, 90));
            latLons.add(LatLon.fromDegrees(0, 180));
            latLons.add(LatLon.fromDegrees(0, -90));

            SurfacePolyline polyline = new SurfacePolyline(latLons);
            polyline.setAttributes(attributes);
            polyline.setPathType(AVKey.GREAT_CIRCLE);
//            polyline.setPathType(AVKey.RHUMB_LINE);

            layer.setName("Level One");
            layer.addRenderable(polyline);
            insertBeforeCompass(getWwd(), layer);
        }
    }
}
