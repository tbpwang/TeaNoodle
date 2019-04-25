/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.model.*;
import edu.zhenger.util.IO;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

/**
 * @Author: WANG Zheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2019/2/27
 */
public class TestSome extends ApplicationTemplate
{
    public static void main(String[] args)
    {
        SpTriangle[] octa = SphereOctahedron.getInstance().getSpFacets();
        for (SpTriangle sp :
            octa)
        {
            IO.write("OctaCoordinate", sp.getUnitCoordinate(false));
        }
        SpTriangle[] icosa = SphereIcosahedron.getInstance().getSpFacets();
        for (SpTriangle sp :
            icosa)
        {
            IO.write("IcosaCoordinate", sp.getUnitCoordinate(false));
        }
    }
//    public static void main(String[] args)
//    {
//        start("GlobeGrid", TestSomeApp.class);
//    }
//
//    public static class TestSomeApp extends ApplicationTemplate.AppFrame
//    {
//        public TestSomeApp()
//        {
//            SpTriangle[] octa = SphereOctahedron.getInstance().getSpFacets();
//            for (SpTriangle sp :
//                octa)
//            {
//                IO.write("OctaCoord", sp.getUnitCoordinate());
//            }
//            SpTriangle[] icosa = SphereIcosahedron.getInstance().getSpFacets();
//            for (SpTriangle sp :
//                icosa)
//            {
//                IO.write("IcosaCoord", sp.getUnitCoordinate());
//            }
//        }
//    }
}
