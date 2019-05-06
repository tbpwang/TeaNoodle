/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel;

import gov.nasa.worldwind.util.Logging;

import java.io.Serializable;

/**
 * @author Zheng WANG
 * @create 2019/5/6 15:38
 * @description
 * @parameter
 */
public class Neighbor implements Serializable
{
    private Cell neighbour;
    private int row, column;
    private int vertexOrEdge;//Edge 1, OR Vertex 0

    public Neighbor(Cell neighbour)
    {
        this(neighbour, -1, -1, -1);
    }

    public Neighbor(Cell neighbour, int row, int column)
    {
        this(neighbour, row, column, -1);
    }

    public Neighbor(Cell neighbour, int row, int column, int vertexOrEdge)
    {
        if (neighbour == null)
        {
            String message = Logging.getMessage("nullValue.CellIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        this.neighbour = neighbour;
        this.row = row;
        this.column = column;
        this.vertexOrEdge = vertexOrEdge;
    }

    public Cell getNeighbour()
    {
        return neighbour;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        if (this.row != -1)
        {
            String message = Logging.getMessage("unchangeableValue.ValueIsUnchangeable");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        this.row = row;
    }

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int column)
    {
        if (this.column != -1)
        {
            String message = Logging.getMessage("unchangeableValue.ValueIsUnchangeable");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        this.column = column;
    }

    public int getVertexOrEdge()
    {
        // Edge 1
        // Vertex 0
        // null -1
        if (vertexOrEdge > 1 || vertexOrEdge < -1)
        {
            vertexOrEdge = -1;
        }
        return vertexOrEdge;
    }

    public void setVertexOrEdge(int vertexOrEdge)
    {
        if (this.vertexOrEdge != -1)
        {
            String message = Logging.getMessage("unchangeableValue.ValueIsUnchangeable");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        if (vertexOrEdge > 1 || vertexOrEdge < -1)
        {
            vertexOrEdge = -1;
        }
        this.vertexOrEdge = vertexOrEdge;
    }
}
