/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.wang.attempt;

import edu.wang.*;
import edu.wang.impl.OctahedronInscribed;
import edu.wang.io.*;
import edu.wang.model.MiddleArcSurfaceTriangle;
import gov.nasa.worldwind.geom.*;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/4/29 16:02
 * @description essential variable
 * @parameter 面积、角度、边长、形状
 */
public class EssentialVariable
{
    //
    public static void main(String[] args)
    {
        OctahedronInscribed octahedron = OctahedronInscribed.getInstance();
        //
        // facet-ID
        // 0-A,1-B,2-C,3-D,4-E,5-F,6-G,7-H
        int facet = 3;
        String ID = "D";

        MiddleArcSurfaceTriangle triangle = new MiddleArcSurfaceTriangle(
            Cons.changeTriangleVertexes(octahedron.getFacetList().get(facet)), new Geocode(ID));

        int level = 10;
        for (int i = 1; i <= level; i++)
        {
//            int maxRow = (int) (Math.pow(2, level) + 1);
            String folder = "SQT";
            String fileName = folder + triangle.getGeocode().getBaseID() + "_" + i;

            String title = "格元ID\t面积(km^2)\t角度A(°)\t角度B(°)\t角度C(°)\t边长a(°)\t边长b(°)\t边长c(°)";
            IO.write(folder, fileName, title);

            List<MiddleArcSurfaceTriangle> triangleList;
            triangleList = new ArrayList<>(Collections.singletonList(triangle));
            refineTriangle(i, triangleList);

            triangleList.sort((o1, o2) -> Cons.sortByRow(o1.getGeocode(), o2.getGeocode()));

            StringBuilder content;
            // 记录格点
            List<LatLon> vertices = new ArrayList<>();
            Set<LatLon> set = new HashSet<>();
            for (MiddleArcSurfaceTriangle tri : triangleList)
            {
                if (tri.isUp())
                {
                    if (set.add(tri.getLeft()))
                    {
                        vertices.add(tri.getLeft());
                    }
                    if (set.add(tri.getRight()))
                    {
                        vertices.add(tri.getRight());
                    }
                }
                if (tri.getGeocode().getRow() == Math.pow(2, i))
                {
                    if (set.add(tri.getTop()))
                    {
                        vertices.add(tri.getTop());
                    }
                }

                content = new StringBuilder();
                content.append(tri.getGeocode().getID()).append("\t");
                content.append(IO.formatDouble(tri.computeArea())).append("\t");
                content.append(IO.formatDouble(tri.interiorAngleDegree().get(0), 6)).append("\t");
                content.append(IO.formatDouble(tri.interiorAngleDegree().get(1), 6)).append("\t");
                content.append(IO.formatDouble(tri.interiorAngleDegree().get(2), 6)).append("\t");
                content.append(IO.formatDouble(tri.lengthOfEdgeDegree().get(0))).append("\t");
                content.append(IO.formatDouble(tri.lengthOfEdgeDegree().get(1))).append("\t");
                content.append(IO.formatDouble(tri.lengthOfEdgeDegree().get(2))).append("\t");
                IO.write(folder, fileName, content.toString());
            }
            if (facet == 0)
            {
//                String vertexTxtName = folder + "Vertex_" + i;
                String vertexTxtName = "vertex_" + i;
                for (LatLon ver : vertices)
                {
                    String latLonString = IO.formatDouble(ver.getLatitude().getDegrees(), 6) + "\t" + IO.formatDouble(
                        ver.getLongitude().getDegrees(), 6);
                    IO.write(folder, vertexTxtName, latLonString);
                }
            }
        }
    }

    static void refineTriangle(int level, List<MiddleArcSurfaceTriangle> triangles)
    {
        List<MiddleArcSurfaceTriangle> tempList = new ArrayList<>();
        for (int i = 0; i < level; i++)
        {
            for (MiddleArcSurfaceTriangle tri : triangles)
            {
                tempList.addAll(Arrays.asList(tri.refine()));
            }
            triangles.clear();
            triangles.addAll(tempList);
            tempList.clear();
        }
    }
}
