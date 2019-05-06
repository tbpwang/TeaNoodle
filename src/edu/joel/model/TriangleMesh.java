/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.model;

import edu.joel.*;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.util.Logging;

/**
 * @author Zheng WANG
 * @create 2019/5/6 16:49
 * @description
 * @parameter
 */
public class TriangleMesh extends Mesh
{
    public TriangleMesh(int level)
    {
        super(level);
    }

    @Override
    public void adjoin(Cell cell, Cell nearCell)
    {
        int[] thisCell = find(cell);
        int[] rowCol = find(nearCell);

        if (thisCell[0] == -1 || rowCol[0] == -1)
        {
            String message = Logging.getMessage("nullValue.CellIsNotInMesh");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        if (Math.abs(rowCol[0] - thisCell[0]) > 1)
        {
            String message = Logging.getMessage("nullValue.CellIsFaraway");
            Logging.logger().severe(message);
            return;
        }

        int nearVertexOrEdge = -1;

        LatLon top, left, right, cellTop, cellLeft, cellRight;
        top = cell.getGeoVertices().get(0);
        left = cell.getGeoVertices().get(1);
        right = cell.getGeoVertices().get(2);
        cellTop = nearCell.getGeoVertices().get(0);
        cellLeft = nearCell.getGeoVertices().get(1);
        cellRight = nearCell.getGeoVertices().get(2);

        if (top.equals(cellTop))
        {
            if (left.equals(cellLeft) || left.equals(cellRight) || right.equals(cellLeft) || right.equals(cellRight))
            {
                nearVertexOrEdge = 1;
            }
            else
            {
                nearVertexOrEdge = 0;
            }
        }

        if (top.equals(cellLeft))
        {
            if (right.equals(cellRight) || left.equals(cellRight) || right.equals(cellTop) || left.equals(cellTop))
            {
                nearVertexOrEdge = 1;
            }
            else
            {
                nearVertexOrEdge = 0;
            }
        }

        if (top.equals(cellRight))
        {
            if (right.equals(cellTop) || left.equals(cellTop) || left.equals(cellLeft) || right.equals(cellLeft))
            {
                nearVertexOrEdge = 1;
            }
            else
            {
                nearVertexOrEdge = 0;
            }
        }

        if (nearVertexOrEdge != -1)
        {
            cell.setNeighbor(new Neighbor(nearCell, rowCol[0], rowCol[1], nearVertexOrEdge));
        }
    }
}
