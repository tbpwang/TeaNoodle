/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.attempt;

import edu.joel.*;
import edu.joel.io.IO;
import edu.joel.model.*;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/5/27
 * @description 用于验证TriangleMesh的功能，建立全部格网后，对每个格网的node填充邻居信息（注：遍历较慢，后继需改进算法）
 *              按行列号输出三角形及其邻域三角形
 */
public class NeighborhoodFinder
{
    public static void main(String[] args)
    {
        // constructor
        int level = 3;
        TriangleMesh tempMesh = new TriangleMesh(level);
        TriangleMesh mesh = new TriangleMesh(level);
//        double startTimes = System.currentTimeMillis();
        for (SphericalTriangleOctahedron polygon : SphericalTriangleOctahedron.values())
        {
            List<MiddleArcSurfaceTriangle> triangleList = new ArrayList<>();
            triangleList.add(polygon.baseTriangle());

            List<MiddleArcSurfaceTriangle> temp;

            for (int i = 0; i < level; i++)
            {
                temp = new ArrayList<>();
                for (MiddleArcSurfaceTriangle middleArcTriangle : triangleList)
                {
                    temp.addAll(Arrays.asList(middleArcTriangle.refine()));
                }
                if (!temp.isEmpty())
                {
                    triangleList.clear();
                    triangleList.addAll(temp);
                }
            }
//            double endTime1 = System.currentTimeMillis();
//            System.out.println((endTime1 - startTimes) + "");
            for (MiddleArcSurfaceTriangle middleArcTriangle : triangleList)
            {
                tempMesh.addNode(middleArcTriangle);
            }
        }
        System.out.println("===================");

//        startTimes = System.currentTimeMillis();
        int meshSize = tempMesh.getNodes().size();
        // 只算一个八分体的面
        int listSize = meshSize / 8;
        List<Mesh.Node> nodeList = new ArrayList<>(tempMesh.getNodes().subList(0, listSize));
//        int count = 0;
        for (Mesh.Node node : nodeList)
        {
            tempMesh.fillNeighbors(node);
//            System.out.println(++count);
        }
//        double endTimes = System.currentTimeMillis();
//        System.out.println("FilledSuccessfully");
//        double interval = (endTimes - startTimes) / 1000.0;
//        System.out.println(interval);
        // construction
        mesh.setNodes(nodeList);


//        startTimes = System.currentTimeMillis();

        for (Mesh.Node node : nodeList)
        {
//            IO.write("Node", (Cell) node.getCell());
            System.out.println(node.getCell());
            List<Mesh.Neighbor> neighborList = node.getNeighborList();
            for (Mesh.Neighbor n : neighborList)
            {
//                IO.write("Node", Integer.toString(level), n.toString());
                System.out.println(n);
                System.out.print("");
            }
        }
//        endTimes = System.currentTimeMillis();
//        System.out.println(((endTimes - startTimes) / 1000) + "");
    }
}
