/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.wang;

import java.io.Serializable;

/**
 * @author Zheng WANG
 * @create 2019/5/17
 * @description 正八面体的基面代码，北半球从左到右依次是： A，B，C，D， 南半球从左到右依次是：E，F，G，H，具有对称性
 * @parameter
 */
public enum OctahedronBaseID implements Serializable
{
    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6),
    H(7);
//    A("A", 0),
//    B("B", 1),
//    C("C", 2),
//    D("D", 3),
//    E("E", 4),
//    F("F", 5),
//    G("G", 6),
//    H("H", 7);

    private int index;
//    private String name;

    OctahedronBaseID(int index)
    {
//        this.name = name;
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    public static OctahedronBaseID indexOf(int index)
    {
        for (OctahedronBaseID cell : OctahedronBaseID.values())
        {
            if (cell.getIndex() == index % OctahedronBaseID.values().length)
            {
                return cell;
            }
        }
        return null;
    }

//    public String getName()
//    {
//        return name;
//    }
//
//    public static OctahedronBaseID indexOf(String name)
//    {
//        for (OctahedronBaseID cell : OctahedronBaseID.values())
//        {
//            if (cell.getName().equalsIgnoreCase(name))
//            {
//                return cell;
//            }
//        }
//        return null;
//    }
}
