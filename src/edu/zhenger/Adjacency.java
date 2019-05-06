/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger;

import edu.zhenger.model.Sequence;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/20
 */
public interface Adjacency
{
//    private Cell flat;
//    private Sequence[] shareVertexCells;
//    private Sequence[] shareSideCells;

    Cell getFlat();

    //void setShareVertexCells(Sequence[] shareVertexCells);
    Sequence[] getSubVertexRanks();

    Sequence[] getVertexRanks();

    Sequence[] getSideRanks();

    //void setShareSideCells(Sequence[] shareSideCells);
}
