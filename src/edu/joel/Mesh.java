/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.util.Logging;

import java.io.Serializable;
import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/5/6 12:41
 * @description
 * @parameter
 */
public abstract class Mesh implements Serializable
{
    private String pathType = AVKey.GREAT_CIRCLE;
    private List<List<Node>> graph;
    //    private List<LatLon> vertices;
    private int level;

    private class Node
    {
        private Cell cell;
        private boolean flag;

        Node(Cell cell)
        {
            this(cell, false);
        }

        Node(Cell cell, boolean flag)
        {
            this.cell = cell;
            this.flag = flag;
        }
    }

    public Mesh()
    {
        this(-1);
    }

    public Mesh(int level)
    {
        graph = new ArrayList<>();
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        if (this.level != -1)
        {
            String message = Logging.getMessage("unchangeableValue.ValueIsUnchangeable");
            Logging.logger().severe(message);
            return;
        }
        this.level = level;
    }

    public void add(Cell cell, int i, int j, boolean isFlag)
    {
        if (cell == null)
        {
            String message = Logging.getMessage("nullValue.CellIsNull");
            Logging.logger().severe(message);
            return;
        }
        if (level != -1 && cell.getLevel() != -1 && level != cell.getLevel())
        {
            String message = Logging.getMessage("inputError.LevelMisMatches");
            Logging.logger().severe(message);
            throw new InputMismatchException(message);
        }
        List<Node> list;
        if (graph.size() < i)
        {
            list = new ArrayList<>();
            list.add(j, new Node(cell, isFlag));
            graph.add(i, list);
        }
        else
        {
            if (cell.equals(graph.get(i).get(j).cell))
            {
                String message = Logging.getMessage("duplicateValue.ValueExists");
                Logging.logger().severe(message);
            }
            else
            {
                graph.get(i).remove(j);
                graph.get(i).add(j, new Node(cell, isFlag));
            }
        }
    }

    public void add(Cell cell, int i, int j)
    {
        add(cell, i, j, false);
    }

    //    private boolean equals(Cell cell)
//    {
//        while (graph.iterator().hasNext())
//        {
//            while (graph.iterator().next().iterator().hasNext())
//            {
//                return graph.iterator().next().iterator().next().cell.equals(cell);
//            }
//        }
//    }
    public void setFlag(Cell cell, boolean flag)
    {
        while (graph.iterator().hasNext())
        {
            while (graph.iterator().next().iterator().hasNext())
            {
                if (graph.iterator().next().iterator().next().cell.equals(cell))
                {
                    graph.iterator().next().iterator().next().flag = flag;
                }
            }
        }
    }

//   邻接关系不能移除
//    public void remove(int i, int j)
//    {
//        if (graph.size() >= i)
//        {
//            graph.get(i).remove(j);
//        }
//    }

    public int[] find(Cell cell)
    {
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        if (cell == null)
        {
            String message = Logging.getMessage("nullValue.CellIsNull");
            Logging.logger().severe(message);
            return result;
        }
        int row = 0, column = 0;
        boolean isFind = false;
        while (graph.iterator().hasNext() && !isFind)
        {
            while (graph.get(row).iterator().hasNext() && !isFind)
            {
                if (cell.equals(graph.get(row).iterator().next()))
                {
                    isFind = true;
                }
                column++;
            }
            row++;
            graph.iterator().next();
        }

        row--;
        column--;

        result[0] = row;
        result[1] = column;

        return result;
    }

    public abstract void adjoin(Cell cell, Cell nearCell);

    public String getPathType()
    {
        return pathType;
    }

    protected void setPathType(String pathType)
    {
        this.pathType = pathType;
    }
}
