/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.test;

import edu.zhenger.model.*;
import edu.zhenger.util.Term;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/8/28
 */
public class TestView extends ApplicationTemplate
{
    public static class TestApp extends AppFrame
    {
        public TestApp()
        {
            RenderableLayer layer = new RenderableLayer();

//            for (int i = 0; i < 20; i++)
//            {
//                for (int j = 0; j < 3; j++)
//                {
//                    layer.addRenderable(SphereIcosahedron.getInstance().getSpFacets(i).getBorders()[j]);
//                }
//
//            }

            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    layer.addRenderable(SphereOctahedron.getInstance().getSpFacets(i).getBorders()[j]);
                }
            }

//            ShapeAttributes attributes = new BasicShapeAttributes();
//            attributes.setOutlineMaterial(new Material(new Color(255, 0, 0)));
//            attributes.setOutlineWidth(2.0);

//            // prime meridian
//            List<Position> poles = new ArrayList<>();
//            poles.add(Position.fromDegrees(90.0,0.0,0));
//            poles.add(Position.fromDegrees(-90.0,0.0,0));
//            Path meridian = Term.defaultLine(poles.get(0),poles.get(1));
//            meridian.setPositions(poles);
//            Path meridian = new Path(poles);
//            meridian.setAttributes(attributes);
//            meridian.setPathType(AVKey.GREAT_CIRCLE);
//            meridian.setFollowTerrain(true);
//            meridian.setTerrainConformance(1);
//            meridian.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
//            layer.addRenderable(meridian);

//            SurfacePolyline polyline = new SurfacePolyline();
////            List<LatLon> list = new ArrayList<>();
////            list.add(LatLon.fromDegrees(90,180));
////            list.add(LatLon.fromDegrees(-89,180));
////            polyline.setLocations(list);
////            polyline.setAttributes(attributes);
////            RenderableLayer poly = new RenderableLayer();
////            poly.addRenderable(polyline);
////            insertBeforeCompass(getWwd(),poly);
////            Path path = new Path(Position.fromDegrees(30,-180),Position.fromDegrees(30,-90));
//////            Path path = new Path(Position.fromDegrees(30,-90),Position.fromDegrees(30,-180));
////            path.setAttributes(attributes);
////            path.setPathType(AVKey.GREAT_CIRCLE);
////            path.setFollowTerrain(true);
////            path.setTerrainConformance(1);
////            path.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
////            RenderableLayer layer = new RenderableLayer();
////            layer.addRenderable(path);
////            insertBeforeCompass(getWwd(),layer);
//            double r, m, n;
//            r = Term.getSphere().getRadius();
//            m = Math.sqrt(50.0 - 10.0 * Math.sqrt(5.0)) / 10.0;
//            n = Math.sqrt(50.0 + 10.0 * Math.sqrt(5.0)) / 10.0;
//            Vec4 vA = new Vec4(0, r * n, r * m);
//            Vec4 vB = new Vec4(-r * m, 0, r * n);
//            Vec4 vC = new Vec4(r * m, 0, r * n);
//            LatLon latLonA = Term.fromVec4(vA);
//            LatLon latLonB = Term.fromVec4(vB);
//            LatLon latLonC = Term.fromVec4(vC);
//            SphericalTriangleCell cell = new SphericalTriangleCell(latLonA, latLonB, latLonC, "1");
//            layer.addRenderable(cell.triangle());

            getWwd().getView().setEyePosition(Position.fromDegrees(0.0, 90.0, 2.5 * Term.getSphere().getRadius()));
            insertBeforeCompass(getWwd(), layer);
        }
    }

    public static void main(String[] args)
    {
        start("TestView", TestApp.class);
    }
}
