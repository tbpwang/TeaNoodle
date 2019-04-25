/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.util.Term;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.util.Logging;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function: used in View
 * @Date: 2017/12/6
 */
public class Vertex
{
    private String id;
    private LatLon coordinate;

    public Vertex(LatLon coordinate, String id)
    {
        if (coordinate == null)
        {
            String message = Logging.getMessage("nullValue.CoordinateIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
            //new Mistake("NullCoordinate");
        }
        this.id = id;
        this.coordinate = Term.approximate(coordinate);
    }

    public String getId()
    {
        return id;
    }

    public LatLon getCoordinate()
    {
        return coordinate;
    }

    @Override
    public String toString()
    {
        return //"Vertex{" +
            //"id='" + id + '\'' +
            //", coord=(" + coordinate.latitude.degrees +","+coordinate.longitude.degrees+")"+
            "" + coordinate;
        //'}';
    }
}