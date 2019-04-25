/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @Author: WANG Zheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2019/2/18
 */
public class AnnotationApp extends ApplicationTemplate
{
    public static class AnnotationFrame extends ApplicationTemplate.AppFrame
    {
        public AnnotationFrame()
        {
            AnnotationLayer annotationLayer = new AnnotationLayer();
            annotationLayer.setName("Geocode");
            RenderableLayer polygonLayer = new RenderableLayer();
            polygonLayer.setName("Triangle");

            List<SpTriangle> triangleList = new ArrayList<>(
                Arrays.asList(SphereOctahedron.getInstance().getSpFacets(1).getChildren()));

            AnnotationAttributes attributes = new AnnotationAttributes();

            attributes.setDrawOffset(new Point(0,0));
            //attributes.setTextAlign(AVKey.CENTER);
            //attributes.setAdjustWidthToText(AVKey.SIZE_FIT_TEXT);
//            attributes.setFrameShape(AVKey.SHAPE_NONE);
            //attributes.setFrameShape(AVKey.SHAPE_LINE);
            //attributes.setDistanceMaxScale(0);
            attributes.setLeader(AVKey.SHAPE_NONE);
            attributes.setOpacity(0.5);
            attributes.setInsets(new Insets(0,0,0,0));

            for (SpTriangle st :
                triangleList)
            {
                for (SpTriangle spTriangle :
                    st.getChildren())
                {
                    polygonLayer.addRenderables(Arrays.asList(spTriangle.getBorders()));
                    // TODO:
                    // 标注的属性，改为浮在面上
                    annotationLayer.addAnnotation(new GlobeAnnotation(spTriangle.getID(), spTriangle.getCenter(), attributes));
                }
            }

            insertBeforeCompass(getWwd(), polygonLayer);
            insertBeforeCompass(getWwd(), annotationLayer);
        }
    }

    public static void main(String[] args)
    {
        start("Annotation", AnnotationFrame.class);
    }
}

