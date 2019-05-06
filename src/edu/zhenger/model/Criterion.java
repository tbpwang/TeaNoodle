/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.DGGS;
import gov.nasa.worldwind.util.Logging;

/**
 * @Author Zheng WANG
 * @create 2019/4/28 10:08
 * @description 格网评价准则构建
 * Criterion: area/size, shape,
 */
public class Criterion extends DGGS
{
    private SpTriangle cell;

    //  基于(椭)球体模型的单元格面积
    private double cellArea;
    //  单元格的投影平面面积
    private double flatArea;
    // 单元格的拓扑属性
    private char shape;

    // 单元格所在的格网系统，及其位置
    private Sequence mesh;

    public Criterion(SpTriangle cell)
    {
        if (cell == null)
        {
            String message = Logging.getMessage("nullValue.CellIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }
        this.cell = cell;
    }

}
