/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.wang.model;

import edu.wang.*;
import edu.wang.io.IO;
import gov.nasa.worldwind.geom.LatLon;

import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/7/17
 * @description
 */
public class ZhaoTriangleMesh extends TriangleMesh
{
    public ZhaoTriangleMesh(int level)
    {
        super(level);
    }

    public ZhaoTriangleMesh cutMesh()
    {
        return cutMesh(1);
    }

    public ZhaoTriangleMesh cutMesh(int octant)
    {
        int level = this.getLevel();
//        MidArcTriangleMesh tempMesh = new MidArcTriangleMesh(level);
        ZhaoTriangleMesh mesh = new ZhaoTriangleMesh(level);
        List<ZhaoSurfaceTriangle> basePolygons = new ArrayList<>();
        List<ZhaoSurfaceTriangle> refineTriangles = new ArrayList<>();

        for (SphericalTriangleOctahedron triangle : SphericalTriangleOctahedron.values())
        {
            basePolygons.add(ZhaoSurfaceTriangle.cast(triangle.baseTriangle()));
        }

        for (ZhaoSurfaceTriangle triangle : basePolygons)
        {
            refineTriangles.add(triangle);
            for (int i = 0; i < level; i++)
            {
                List<ZhaoSurfaceTriangle> temp = new ArrayList<>();
                for (ZhaoSurfaceTriangle tri : refineTriangles)
                {
                    // refine
                    temp.addAll(Arrays.asList(tri.refine()));
//                    count++;
                }
                if (!temp.isEmpty())
                {
                    refineTriangles.clear();
                    refineTriangles.addAll(temp);
                }
                temp.clear();
            }
            // mesh
            for (ZhaoSurfaceTriangle t : refineTriangles)
            {
//                tempMesh.addNode(t);
                this.addNode(t);
            }
            refineTriangles.clear();
        }

        List<Mesh.Node> nodeList = this.getNodes();

        int size = nodeList.size() / 8;
        int fromPosition, toPosition;
        if (octant >= 1 && octant <= 8)
        {
            fromPosition = (octant - 1) * size;
            toPosition = octant * size;
        }
        else
        {
            fromPosition = 0;
            toPosition = size;
        }
        List<Mesh.Node> nodeListTemp = new ArrayList<>(nodeList.subList(fromPosition, toPosition));
        for (Mesh.Node n : nodeListTemp)
        {
//            tempMesh.fillNeighbors(n);
            this.fillNeighbors(n);
        }

        mesh.setNodes(nodeListTemp);

        return mesh;
    }

    public void analysis()
    {
        // level, area,
        List<Mesh.Node> nodes = new ArrayList<>(this.getNodes());

        StringBuilder analysisString = new StringBuilder();
//        int level = 0;
        for (Mesh.Node node : nodes)
        {
            // level
//            level++;
//            analysisString.append("level ").append(level).append(" ");
            ZhaoSurfaceTriangle triangle = (ZhaoSurfaceTriangle) node.getCell();

            // area
            double area = triangle.computeArea();
            analysisString.append("area ").append(area).append(" ");

            // compact
            LatLon top, left, right, center;
            center = triangle.getCenter();
            top = triangle.getTop();
            left = triangle.getLeft();
            right = triangle.getRight();
            double a, b, c, d1, d2, d3;
            a = LatLon.greatCircleDistance(right, left).radians;
            b = LatLon.greatCircleDistance(top, right).radians;
            c = LatLon.greatCircleDistance(top, left).radians;
            List<Double> list = new ArrayList<>();
            list.add(a);
            list.add(b);
            list.add(c);
            double[] stats = statistics(list);

            analysisString.append("avg_compact ").append(stats[0]).append(" ");
            analysisString.append("sigma_compact^2 ").append(stats[1]).append(" ");
            list.clear();

            // in-distance
            d1 = LatLon.greatCircleDistance(center, top).radians;
            d2 = LatLon.greatCircleDistance(center, left).radians;
            d3 = LatLon.greatCircleDistance(center, right).radians;
            list.add(d1);
            list.add(d2);
            list.add(d3);
            stats = statistics(list);
            analysisString.append("avg_distance ").append(stats[0]).append(" ");
            analysisString.append("sigma_distance^2 ").append(stats[1]).append(" ");
            list.clear();

            // adj-distance
            List<Mesh.Neighbor> neighbors = node.getNeighborList();
            for (Mesh.Neighbor dgg : neighbors)
            {
                Mesh.Node n = dgg.getNode();
                if (!n.isFlag())
                {
                    ZhaoSurfaceTriangle arcTriangle = (ZhaoSurfaceTriangle) n.getCell();
                    LatLon adjCenter = arcTriangle.getCenter();
                    list.add(LatLon.greatCircleDistance(center, adjCenter).radians);
                }
            }
            setFlag(node.getCell(), true);
            stats = statistics(list);
            analysisString.append("adj_distance ").append(stats[0]).append(" ");
            analysisString.append("sigma_adjDist^2 ").append(stats[1]).append(" ");
            list.clear();
            analysisString.append(System.lineSeparator());
        }

        String fileName = this.getClass().getSimpleName();
        String level = String.valueOf(this.getLevel());
        IO.write(fileName, level, analysisString.toString());
    }

    private double[] statistics(List<Double> data)
    {
        // [0] avg
        // [1] sigma
        double avg = 0.0, sum = 0.0, sigma = 0.0;
        int length = data.size();
        for (Double var : data)
        {
            sum += var;
        }
        avg = sum / length;
        sum = 0.0;
        for (Double var :
            data)
        {
            sum += Math.pow(var - avg, 2);
        }
        sigma = sum / length;
        double[] avg_sigma = new double[2];
        // [0] avg
        // [1] sigma
        avg_sigma[0] = avg;
        avg_sigma[1] = sigma;
        return avg_sigma;
    }
}
