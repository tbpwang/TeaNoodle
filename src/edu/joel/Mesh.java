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
    private List<List<Node>> nodes;
    //    private List<LatLon> vertices;
    private int level;

    public class Node
    {
        private Object cell;
        private boolean flag;

        private Node(Object cell)
        {
            this(cell, false);
        }

        private Node(Object cell, boolean flag)
        {
            this.cell = cell;
            this.flag = flag;
        }

        public Object getCell()
        {
            return cell;
        }

        public boolean isFlag()
        {
            return flag;
        }
    }

    public Mesh()
    {
        this(-1);
    }

    public Mesh(int level)
    {
        nodes = new ArrayList<>();
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

    public void add(Object cell, int i, int j, boolean isFlag)
    {
        if (cell == null)
        {
            String message = Logging.getMessage("nullValue.CellIsNull");
            Logging.logger().severe(message);
            return;
        }
//        if (level != -1 && cell.getLevel() != -1 && level != cell.getLevel())
//        {
//            String message = Logging.getMessage("inputError.LevelMisMatches");
//            Logging.logger().severe(message);
//            throw new InputMismatchException(message);
//        }
        List<Node> list;
        if (i == -1 && j == -1)
        {
            if (nodes.size() > 0)
            {
                list = nodes.get(0);
            }
            else
            {
                list = new ArrayList<>();
            }
            list.add(new Node(cell, isFlag));
            nodes.add(list);
        }
        else
        {
            int il = nodes.size();
            if (il < i)
            {
                list = new ArrayList<>();
                for (int k = 0; k < j; k++)
                {
                    if (k == j - 1)
                    {
                        list.add(new Node(cell, isFlag));
                    }
                    else
                    {
                        list.add(null);
                    }
                }
                for (int k = 0; k < i - il; k++)
                {
                    if (k == i - il - 1)
                    {
                        nodes.add(list);
                    }
                    else
                    {
                        nodes.add(null);
                    }
                }
            }
            else
            {
                // il >= i-1
                int jl = nodes.get(i - 1).size();
                if (jl < j)
                {
                    for (int k = 0; k < j - jl; k++)
                    {
                        if (k == j - jl - 1)
                        {
                            nodes.get(i - 1).add(new Node(cell, isFlag));
                        }
                        else
                        {
                            nodes.get(i - 1).add(null);
                        }
                    }
                }
                else
                {
                    nodes.get(i - 1).set(j - 1, new Node(cell, isFlag));
                }
            }
        }
    }

    public void add(Object cell, int i, int j)
    {
        add(cell, i, j, false);
    }

    public void add(Object cell)
    {
        add(cell, -1, -1);
    }

    //    private boolean equals(Cell cell)
//    {
//        while (nodes.iterator().hasNext())
//        {
//            while (nodes.iterator().next().iterator().hasNext())
//            {
//                return nodes.iterator().next().iterator().next().cell.equals(cell);
//            }
//        }
//    }

    public List<List<Node>> getNodes()
    {
        return nodes;
    }

    public void setFlag(Object cell, boolean flag)
    {
        while (nodes.iterator().hasNext())
        {
            List<Node> rowList = nodes.iterator().next();
            while (rowList.iterator().hasNext())
            {
                Node node = rowList.iterator().next();
                if (node.cell.equals(cell))
                {
                    node.flag = flag;
                }
            }
        }
    }

//   邻接关系不能移除
//    public void remove(int i, int j)
//    {
//        if (nodes.size() >= i)
//        {
//            nodes.get(i).remove(j);
//        }
//    }

    public int[] find(Object cell)
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
        while (nodes.iterator().hasNext() && !isFind)
        {
            while (nodes.get(row).iterator().hasNext() && !isFind)
            {
                if (cell.equals(nodes.get(row).iterator().next()))
                {
                    isFind = true;
                }
                column++;
            }
            row++;
            nodes.iterator().next();
        }

        row--;
        column--;

        result[0] = row;
        result[1] = column;

        return result;
    }

    public abstract void adjoin(Object cell, Object nearCell);

    public String getPathType()
    {
        return pathType;
    }

    protected void setPathType(String pathType)
    {
        this.pathType = pathType;
    }

    @Override
    public String toString()
    {
        StringBuilder stringNodes = new StringBuilder();
        for (List<Node> nodeList : nodes)
        {
            for (Node node : nodeList)
            {
                stringNodes.append(node.getCell().toString()).append(System.lineSeparator());
            }
        }
        return "Mesh{" +
            "level = " + level +
            ", pathType = '" + pathType + '\''  +
            ", nodes = " + System.lineSeparator() + stringNodes.toString() +
            '}';
    }
}
