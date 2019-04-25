/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/10/11
 */
public class InitLabel extends ApplicationTemplate
{
    public static class InitLabelFrame extends AppFrame
    {
        public InitLabelFrame()
        {
            List<Position> latLons = new ArrayList<>();
            latLons.add(Position.fromDegrees(90, 0));
            latLons.add(Position.fromDegrees(0, 0));
            latLons.add(Position.fromDegrees(-90, 0));
            latLons.add(Position.fromDegrees(-90, 90));
            latLons.add(Position.fromDegrees(0, 90));
            latLons.add(Position.fromDegrees(0, 0));
            latLons.add(Position.fromDegrees(0, 90));
            latLons.add(Position.fromDegrees(90, 90));
            latLons.add(Position.fromDegrees(90, 180));
            latLons.add(Position.fromDegrees(0, 180));
            latLons.add(Position.fromDegrees(0, 90));
            latLons.add(Position.fromDegrees(-90, 90));
            latLons.add(Position.fromDegrees(-90, 180));
            latLons.add(Position.fromDegrees(0, 180));
            ShapeAttributes attr = new BasicShapeAttributes();
            attr.setInteriorMaterial(new Material(Color.GREEN));
            Path path = new Path(latLons);
            path.setAttributes(attr);
            path.setPathType(AVKey.GREAT_CIRCLE);

            Layer pathLayer = new RenderableLayer();
            ((RenderableLayer) pathLayer).addRenderable(path);
            insertBeforeCompass(getWwd(), pathLayer);

            LatLonGraticuleLayer layer = new LatLonGraticuleLayer();
//            System.out.println(LatLonGraticuleLayer.GRATICULE_LATLON_LEVEL_0.length());

            insertBeforeCompass(getWwd(), layer);
        }
    }

    public static void main(String[] args)
    {
        start("InitLAbel", InitLabelFrame.class);
    }
}
