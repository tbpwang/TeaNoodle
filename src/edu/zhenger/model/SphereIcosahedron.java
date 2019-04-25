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
 * @Date: 2018/9/19
 */
public class SphereIcosahedron
{
    private static SphereIcosahedron ourInstance = new SphereIcosahedron();

    public static SphereIcosahedron getInstance()
    {
        return ourInstance;
    }

    private SpTriangle[] spFacets;
    private Vec4[] verticesVector;
    private LatLon[] verticesLatLon;

    private SphereIcosahedron()
    {
//        double r, x, z;
//        r = Term.getSphere().getRadius();
//        x = r * Math.sqrt(50.0 - 10.0 * Math.sqrt(5.0)) / 10.0;
//        z = r * Math.sqrt(50.0 + 10.0 * Math.sqrt(5.0)) / 10.0;
        verticesVector = new Vec4[12];
        verticesVector[3] = new Vec4(-0.894427188999916, 0, 0.447213599499958);
        verticesVector[1] = new Vec4(-0.723606798171796, -0.525731108671732, -0.447213598870108);
        verticesVector[2] = new Vec4(-0.723606798171796, 0.525731108671732, -0.447213598870108);
        verticesVector[4] = new Vec4(-0.276393199228192, -0.850650807624619, 0.447213598751188);
        verticesVector[5] = new Vec4(-0.276393199228192, 0.850650807624619, 0.447213598751188);
        verticesVector[0] = new Vec4(0.0, 0.0, -1.0);
        verticesVector[6] = new Vec4(0.0, 0.0, 1.0);
        verticesVector[10] = new Vec4(0.276393199228192, -0.850650807624619, -0.447213598751188);
        verticesVector[11] = new Vec4(0.276393199228192, 0.850650807624619, -0.447213598751188);
        verticesVector[7] = new Vec4(0.723606798171796, -0.525731108671732, 0.447213598870108);
        verticesVector[8] = new Vec4(0.723606798171796, 0.525731108671732, 0.447213598870108);
        verticesVector[9] = new Vec4(0.894427188999916, 0, -0.447213599499958);

        verticesLatLon = new LatLon[12];
        for (int i = 0; i < 12; i++)
        {
            verticesLatLon[i] = Term.fromVec4(verticesVector[i]);
        }

        spFacets = new SpTriangle[20];
        spFacets[0] = new SpTriangle(verticesLatLon[0], verticesLatLon[1], verticesLatLon[2], "");
        spFacets[1] = new SpTriangle(verticesLatLon[1], verticesLatLon[2], verticesLatLon[3], "");
        spFacets[2] = new SpTriangle(verticesLatLon[1], verticesLatLon[3], verticesLatLon[4], "");
        spFacets[3] = new SpTriangle(verticesLatLon[2], verticesLatLon[3], verticesLatLon[5], "");
        spFacets[4] = new SpTriangle(verticesLatLon[3], verticesLatLon[5], verticesLatLon[6], "");
        spFacets[5] = new SpTriangle(verticesLatLon[3], verticesLatLon[4], verticesLatLon[6], "");
        spFacets[6] = new SpTriangle(verticesLatLon[4], verticesLatLon[7], verticesLatLon[10], "");
        spFacets[7] = new SpTriangle(verticesLatLon[4], verticesLatLon[6], verticesLatLon[7], "");
        spFacets[8] = new SpTriangle(verticesLatLon[5], verticesLatLon[6], verticesLatLon[8], "");
        spFacets[9] = new SpTriangle(verticesLatLon[5], verticesLatLon[8], verticesLatLon[11], "");
        spFacets[10] = new SpTriangle(verticesLatLon[6], verticesLatLon[7], verticesLatLon[8], "");
        spFacets[11] = new SpTriangle(verticesLatLon[7], verticesLatLon[8], verticesLatLon[9], "");
        spFacets[12] = new SpTriangle(verticesLatLon[7], verticesLatLon[9], verticesLatLon[10], "");
        spFacets[13] = new SpTriangle(verticesLatLon[8], verticesLatLon[9], verticesLatLon[11], "");
        spFacets[14] = new SpTriangle(verticesLatLon[0], verticesLatLon[9], verticesLatLon[11], "");
        spFacets[15] = new SpTriangle(verticesLatLon[0], verticesLatLon[9], verticesLatLon[10], "");
        spFacets[16] = new SpTriangle(verticesLatLon[1], verticesLatLon[4], verticesLatLon[10], "");
        spFacets[17] = new SpTriangle(verticesLatLon[0], verticesLatLon[1], verticesLatLon[10], "");
        spFacets[18] = new SpTriangle(verticesLatLon[0], verticesLatLon[2], verticesLatLon[11], "");
        spFacets[19] = new SpTriangle(verticesLatLon[2], verticesLatLon[5], verticesLatLon[11], "");
//        spFacets[0] = new SpTriangle(verticesLatLon[0], verticesLatLon[1], verticesLatLon[2], "0");
//        spFacets[1] = new SpTriangle(verticesLatLon[1], verticesLatLon[2], verticesLatLon[3], "1");
//        spFacets[2] = new SpTriangle(verticesLatLon[1], verticesLatLon[3], verticesLatLon[4], "2");
//        spFacets[3] = new SpTriangle(verticesLatLon[2], verticesLatLon[3], verticesLatLon[5], "3");
//        spFacets[4] = new SpTriangle(verticesLatLon[3], verticesLatLon[5], verticesLatLon[6], "4");
//        spFacets[5] = new SpTriangle(verticesLatLon[3], verticesLatLon[4], verticesLatLon[6], "5");
//        spFacets[6] = new SpTriangle(verticesLatLon[4], verticesLatLon[7], verticesLatLon[10], "6");
//        spFacets[7] = new SpTriangle(verticesLatLon[4], verticesLatLon[6], verticesLatLon[7], "7");
//        spFacets[8] = new SpTriangle(verticesLatLon[5], verticesLatLon[6], verticesLatLon[8], "8");
//        spFacets[9] = new SpTriangle(verticesLatLon[5], verticesLatLon[8], verticesLatLon[11], "9");
//        spFacets[10] = new SpTriangle(verticesLatLon[6], verticesLatLon[7], verticesLatLon[8], "A");
//        spFacets[11] = new SpTriangle(verticesLatLon[7], verticesLatLon[8], verticesLatLon[9], "B");
//        spFacets[12] = new SpTriangle(verticesLatLon[7], verticesLatLon[9], verticesLatLon[10], "C");
//        spFacets[13] = new SpTriangle(verticesLatLon[8], verticesLatLon[9], verticesLatLon[11], "D");
//        spFacets[14] = new SpTriangle(verticesLatLon[0], verticesLatLon[9], verticesLatLon[11], "E");
//        spFacets[15] = new SpTriangle(verticesLatLon[0], verticesLatLon[9], verticesLatLon[10], "F");
//        spFacets[16] = new SpTriangle(verticesLatLon[1], verticesLatLon[4], verticesLatLon[10], "G");
//        spFacets[17] = new SpTriangle(verticesLatLon[0], verticesLatLon[1], verticesLatLon[10], "H");
//        spFacets[18] = new SpTriangle(verticesLatLon[0], verticesLatLon[2], verticesLatLon[11], "I");
//        spFacets[19] = new SpTriangle(verticesLatLon[2], verticesLatLon[5], verticesLatLon[11], "J");
    }

    public SpTriangle[] getSpFacets()
    {
        return spFacets;
    }

    public SpTriangle getSpFacets(int facet)
    {
        return spFacets[facet];
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
