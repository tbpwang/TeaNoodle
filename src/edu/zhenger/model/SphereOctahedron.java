/*
 * Copyright (C) 2018 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.util.Term;
import gov.nasa.worldwind.geom.*;

/**
 * @Author: Wangzheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2018/12/22
 */
public class SphereOctahedron
{
    private static SphereOctahedron ourInstance = new SphereOctahedron();

    public static SphereOctahedron getInstance()
    {
        return ourInstance;
    }

//    verticesVector;
//] verticesLatLon;
    private SpTriangle[] spFacets;
    private Vec4[] verticesVector;
    private LatLon[] verticesLatLon;

    private SphereOctahedron()
    {
        verticesLatLon = new LatLon[6];
        verticesLatLon[0] = LatLon.fromDegrees(90.0, 0.0);
        verticesLatLon[1] = LatLon.fromDegrees(0.0, 0.0);
        verticesLatLon[2] = LatLon.fromDegrees(0.0, 90.0);
        verticesLatLon[3] = LatLon.fromDegrees(0.0, 180.0);
        verticesLatLon[4] = LatLon.fromDegrees(0.0, -90.0);
        verticesLatLon[5] = LatLon.fromDegrees(-90.0, 0.0);

        verticesVector = new Vec4[6];
        for (int i = 0; i < 6; i++)
        {
            verticesVector[i] = Term.fromLatLon(verticesLatLon[i]);
        }

        spFacets = new SpTriangle[8];
//        spFacets[0] = new SpTriangle(verticesLatLon[0], verticesLatLon[1], verticesLatLon[2], "0");
//        spFacets[1] = new SpTriangle(verticesLatLon[0], verticesLatLon[2], verticesLatLon[3], "1");
//        spFacets[2] = new SpTriangle(verticesLatLon[0], verticesLatLon[3], verticesLatLon[4], "2");
//        spFacets[3] = new SpTriangle(verticesLatLon[0], verticesLatLon[4], verticesLatLon[1], "3");
//        spFacets[4] = new SpTriangle(verticesLatLon[5], verticesLatLon[1], verticesLatLon[2], "4");
//        spFacets[5] = new SpTriangle(verticesLatLon[5], verticesLatLon[2], verticesLatLon[3], "5");
//        spFacets[6] = new SpTriangle(verticesLatLon[5], verticesLatLon[3], verticesLatLon[4], "6");
//        spFacets[7] = new SpTriangle(verticesLatLon[5], verticesLatLon[4], verticesLatLon[1], "7");
        spFacets[0] = new SpTriangle(verticesLatLon[0], verticesLatLon[1], verticesLatLon[2], "");
        spFacets[1] = new SpTriangle(verticesLatLon[0], verticesLatLon[2], verticesLatLon[3], "");
        spFacets[2] = new SpTriangle(verticesLatLon[0], verticesLatLon[3], verticesLatLon[4], "");
        spFacets[3] = new SpTriangle(verticesLatLon[0], verticesLatLon[4], verticesLatLon[1], "");
        spFacets[4] = new SpTriangle(verticesLatLon[5], verticesLatLon[1], verticesLatLon[2], "");
        spFacets[5] = new SpTriangle(verticesLatLon[5], verticesLatLon[2], verticesLatLon[3], "");
        spFacets[6] = new SpTriangle(verticesLatLon[5], verticesLatLon[3], verticesLatLon[4], "");
        spFacets[7] = new SpTriangle(verticesLatLon[5], verticesLatLon[4], verticesLatLon[1], "");
    }

    public SpTriangle[] getSpFacets()
    {
        return spFacets;
    }

    public SpTriangle getSpFacets(int i)
    {
        return spFacets[i];
    }

    public Vec4[] getVerticesVector()
    {
        return verticesVector;
    }

    public LatLon[] getVerticesLatLon()
    {
        return verticesLatLon;
    }
}
