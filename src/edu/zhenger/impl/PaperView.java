/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.formats.shapefile.ShapefileLayerFactory;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.util.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function: MidArcView
 * @Date: 2018/7/6
 */
public class PaperView extends ApplicationTemplate
{
    public static class MidArcAppFrame extends AppFrame
    {
        public MidArcAppFrame()
        {
            super();
            int level = 5;

            // All globe is in gird
            gridOcta(getWwd(), level);
            gridIcosa(getWwd(), level);

            // Add shapefile of test Zone
            ShapefileLayerFactory factory = new ShapefileLayerFactory();

            // attrs as one style
            final ShapeAttributes attrs = new BasicShapeAttributes();
//            attrs.setInteriorMaterial(new Material(new Color(150,100,200)));
            attrs.setInteriorMaterial(new Material(new Color(0, 150, 0)));
            attrs.setInteriorOpacity(0.99);
            //as randomAttrs
            //final RandomShapeAttributes randomAttrs = new RandomShapeAttributes();
            factory.setAttributeDelegate((shapefileRecord, renderableRecord) -> {
                //renderableRecord.setAttributes(randomAttrs.nextAttributes().asShapeAttributes());
                renderableRecord.setAttributes(attrs);
            });

            // Load the shapefile. Define the completion callback.
            String shapefileSource = "src/edu/zhenger/data/testZone.shp";
//            String shapefileSource = "src/edu/zhenger/data/China_District.shp";

            factory.createFromShapefileSource(shapefileSource,
                new ShapefileLayerFactory.CompletionCallback()
                {
                    @Override
                    public void completion(Object result)
                    {
                        // the result is the layer the factory created
                        final RenderableLayer layer = (RenderableLayer) result;

                        layer.setName(WWIO.getFilename(layer.getName()));

                        // Add the layer to the World Window's layer list on the Event Dispatch Thread.
                        SwingUtilities.invokeLater(() -> {
                            // EsriShapefile.AppFrame.this.getWwd().getModel().setGlobe(Term.getSphere());
                            MidArcAppFrame.this.getWwd().getModel().getLayers().add(layer);
                            MidArcAppFrame.this.layerPanel.updateLayers(
                                MidArcAppFrame.this.getWwd());
                        });
                    }

                    @Override
                    public void exception(Exception e)
                    {
                        Logging.logger().log(java.util.logging.Level.SEVERE, e.getMessage(), e);
                    }
                });
        }
        private List<SpTriangle> grids(SpTriangle triangle,int depth)
        {
            List<SpTriangle> triangleList, tempList;
            //triangleList = new ArrayList<>(Collections.singletonList(triangle));
            triangleList = new ArrayList<>();
            triangleList.add(triangle);

            for (int j = 0; j < depth; j++)
            {
                tempList = new ArrayList<>();
                for (SpTriangle tri :
                    triangleList)
                {
                    tempList.addAll(Arrays.asList(tri.getChildren()));
                }
                triangleList.clear();
                triangleList.addAll(tempList);
                tempList.clear();
            }
            return triangleList;
        }
        //annotationLayer.addAnnotation(new GlobeAnnotation(st.getID(),st.getCenter()));
        private AnnotationLayer getAnnotation(SpTriangle triangle,int depth)
        {
            AnnotationAttributes attributes = new AnnotationAttributes();
            //attributes.setFrameShape(AVKey.SHAPE_NONE);
            attributes.setLeader(AVKey.SHAPE_NONE);
            attributes.setInsets(new Insets(0,0,0,0));
            //attributes.setOpacity(0.5);
            attributes.setDrawOffset(new Point(0,0));

            AnnotationLayer annotationLayer = new AnnotationLayer();


            for (SpTriangle sp :
                grids(triangle,depth))
            {
                annotationLayer.addAnnotation(new GlobeAnnotation(sp.getID(),sp.getCenter(),attributes));
            }
            return annotationLayer;

        }

        private RenderableLayer tessellate(SpTriangle triangle,int depth)
        {
            RenderableLayer layer = new RenderableLayer();

            for (SpTriangle sp :
                grids(triangle,depth))
            {
                layer.addRenderables(Arrays.asList(sp.getBorders()));
            }
            return layer;
        }

        private void gridIcosa(WorldWindow wwd, int level)
        {
            for (int i = 0; i < 20; i++)
            {
                SpTriangle triangle = SphereIcosahedron.getInstance().getSpFacets(i);
                RenderableLayer layer = tessellate(triangle,level);
                layer.setName("Icosa_" + i);
                layer.setEnabled(false);
                insertBeforeCompass(wwd, layer);
                AnnotationLayer annotationLayer = getAnnotation(triangle, level);
                annotationLayer.setName("IcosaAnnotation_" + i);
                annotationLayer.setEnabled(false);
                insertBeforeCompass(getWwd(),annotationLayer);
            }
        }

        private void gridOcta(WorldWindow wwd, int level)
        {
            for (int i = 0; i < 8; i++)
            {
                SpTriangle triangle = SphereOctahedron.getInstance().getSpFacets(i);
                RenderableLayer layer = tessellate(triangle,level);
                layer.setName("Octa" + i);
                layer.setEnabled(false);
                insertBeforeCompass(wwd, layer);
                AnnotationLayer annotationLayer = getAnnotation(triangle, level);
                annotationLayer.setName("OctaAnnotation_" + i);
                annotationLayer.setEnabled(false);
                insertBeforeCompass(getWwd(),annotationLayer);
            }
        }
    }

    public static void main(String[] args)
    {
        start("Class I Mid-Arcs", MidArcAppFrame.class);
    }
}
