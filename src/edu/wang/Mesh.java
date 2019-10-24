/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.wang;

import edu.wang.io.Cons;

import java.io.Serializable;
import java.util.*;

/**
 * @author Zheng WANG
 * @create 2019/5/6 12:41
 * @description
 */
public abstract class Mesh implements Serializable
{
    private List<Node> nodes;
    private int level;
    private boolean nodeSorted;

    public class Node
    {
        private DGG cell;
        private boolean flag;
        private List<Neighbor> neighborList;

        private Node()
        {
            this(null);
        }

        private Node(DGG cell)
        {
            this(cell, false);
        }

        private Node(DGG cell, boolean flag)
        {
            this.cell = cell;
            this.flag = flag;
//            neighborList = null;
        }

        public DGG getCell()
        {
            return cell;
        }

        public boolean isFlag()
        {
            return flag;
        }

        private void setFlag(boolean flag)
        {
            this.flag = flag;
        }

        public List<Neighbor> getNeighborList()
        {
            if (neighborList == null)
                neighborList = new ArrayList<>();
            return neighborList;
        }

        public Neighbor asNeighbor()
        {
//            return new Neighbor();
            return new Neighbor(this);
        }

        public Neighbor asNeighbor(int nodeType)
        {
//            return new Neighbor();
            return new Neighbor(this, nodeType);
        }

        public void addNeighbor(Neighbor neighbor)
        {
            if (neighbor != null)
                this.getNeighborList().add(neighbor);
        }

//        public void addNeighbor(Node neighbor, int neighborType)
//        {
//            Neighbor n = asNeighbor();
////                n.asNeighbour(neighbor);
////                n.setType(type);
////                n.asNeighbour(neighbor,neighborType);
//            n.
//        }

        public void setNeighborList(List<Neighbor> neighborList)
        {
            if (neighborList != null)
            {
                this.neighborList = neighborList;
            }
        }
    }

    public class Neighbor
    {
        //    private DGG host;
        private Node node;
        //        private Geocode neighborGeocode;
        private int type;

//        private Neighbor()
//        {
//            this(null);
//        }

        private Neighbor(Node node)
        {
            this(node, -1);
        }

        private Neighbor(Node node, int type)
        {
            if (node != null)
            {
                this.node = node;
            }
            else
            {
                this.node = new Node();
            }
            if (type == Cons.NEIGHBOR_TYPE_EDGE || type == Cons.NEIGHBOR_TYPE_VERTEX)
            {
                this.type = type;
            }
            else
            {
                this.type = -1;
            }
        }

        public Node getNode()
        {
            return node;
        }

//        public void setNode(Node node)
//        {
//            this.node = node;
//        }
//
//        public void setNode(Node node, int type)
//        {
//            setNode(node);
//            setType(type);
//        }

        public int getType()
        {
            // DGGS.NEIGHBOR_TYPE_EDGE = 1
            // DGGS.NEIGHBOR_TYPE_VERTEX = 0
            // Error = -1
            return type;
        }

        public void setType(int neighborType)
        {
//            this.type = neighborType;
            if (type == Cons.NEIGHBOR_TYPE_VERTEX || type == Cons.NEIGHBOR_TYPE_EDGE)
            {
                this.type = neighborType;
            }
            else
            {
                this.type = -1;
            }
        }

        @Override
        public String toString()
        {
            return getNode().getCell().getGeocode().toString() +
                ", \'" + (type == 1 ? "Ed" : type == 0 ? "Vt" : "Un") + "\'";
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
        nodeSorted = false;
//        pathType = AVKey.GREAT_CIRCLE;
    }

    private boolean isNodeSorted()
    {
        return nodeSorted;
    }

    private void setNodeSorted()
    {
        nodeSorted = true;
    }

    public int getLevel()
    {
        return level;
    }

    public void setNodes(List<Node> nodes)
    {
        if (nodes != null)
        {
            this.nodes = nodes;
        }
    }

    public void addNode(DGG cell)
    {
        //Node node
        if (cell.getLevel() == this.getLevel())
        {
            nodes.add(new Node(cell));
        }
    }

    public void addNode(Node node)
    {
        if (node.getCell().getLevel() == this.getLevel())
        {
            nodes.add(node);
        }
    }

    public Node search(int row, int column, String baseID)
    {
        // 此处的查找算法有待优化，以便提高查找速度
        List<Node> temp = getNodes();
//        int listSize = temp.size();
        for (Node node : temp)
        {
            Geocode geocode = node.getCell().getGeocode();
            String base = geocode.getBaseID();
            if (base.equals(baseID) && geocode.getRow() == row && geocode.getColumn() == column)
            {
                return node;
            }
        }
        // 新查找方法
        // TODO

        return null;
    }

    public Node search(Geocode geocode)
    {
        for (int i = 0; i < getNodes().size(); i++)
        {
            if (getNodes().get(i).getCell().getGeocode().equals(geocode))
            {
                return getNodes().get(i);
            }
        }
        return null;
    }

    public List<Node> getNodes()
    {
        if (nodes != null)
        {
            if (!isNodeSorted())
            {
                nodes.sort((o1, o2) -> {
                    Geocode g1, g2;
                    g1 = o1.getCell().getGeocode();
                    g2 = o2.getCell().getGeocode();
                    if (g1.getBaseID().equals(g2.getBaseID()))
                    {
                        if (g1.getRow() == g2.getRow())
                        {
                            return g1.getColumn() - g2.getColumn();
                        }
                        return g1.getRow() - g2.getRow();
                    }
                    return g1.getBaseID().compareTo(g2.getBaseID());
                });
                setNodeSorted();
            }
            return nodes;
        }
        return null;
    }

    public void setFlag(DGG cell, boolean flag)
    {
        Node node = search(cell.getGeocode());
        if (node != null)
        {
            node.setFlag(flag);
        }
    }

//    public String getPathType()
//    {
//        return pathType;
//    }
//
//    protected void setPathType(String pathType)
//    {
//        this.pathType = pathType;
//    }

    @Override
    public String toString()
    {
        StringBuilder stringNodes = new StringBuilder();

        for (Node node : nodes)
        {
            stringNodes.append(node.getCell().toString()).append(System.lineSeparator());
        }

        return "Mesh{" +
            "level = " + level +
            ", nodes = " + System.lineSeparator() + stringNodes.toString() +
            '}';
    }
}
