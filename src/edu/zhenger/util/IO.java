/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.util;

import edu.zhenger.Cell;
import edu.zhenger.model.*;

import java.io.*;
import java.util.*;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/28
 */
public class IO
{
    private static String outPath = "D:\\outdata\\";

    public static void write(QTM grid)
    {
        if (grid == null)
        {
            return;
        }
        checkPath(outPath);

        List<Sequence> sequenceList;
        sequenceList = new ArrayList<Sequence>();

//        int level = grid.getLevel();

        FileWriter writer = null;

        String fileName = "inQTM";
        try
        {
            writer = new FileWriter(outPath + fileName + grid.getLevel() + ".txt", true);

            for (Cell[] cells : grid.getRanksCells())
            {
                for (Cell aCell : cells)
                {
                    writer.write(aCell.toString());
                    Sequence[] ranks = grid.adjoin(aCell).getSideRanks();
                    for (Sequence rank : ranks)
                    {
                        if (rank.getRow() > grid.computeRanks(aCell).getRow()
                            || rank.getCol() > grid.computeRanks(aCell).getCol())
                        {
                            writer.write(grid.measureDistance(aCell, grid.search(rank)) + "\t");
                            writer.write(grid.halveDistance(aCell, grid.search(rank)) + "\t");
                        }
                    }
                    writer.write(System.lineSeparator());
                }
            }

//            for (Cell[] rowCol : grid.getRanksCells())
//            {
//                for (Cell aCell : rowCol)
//                {
//                    writer.write(aCell.toString());
//
//                    sequenceList.add(aCell.computeRanks());
//
//                    Sequence[] sideCellRanks = grid.adjoin(aCell).getSideRanks();
//
//                    for (Sequence r : sideCellRanks)
//                    {
//                        boolean isOut = true;
//                        for (Sequence aRanks : sequenceList)
//                        {
//                            if (r.equals(aRanks))
//                            {
//                                isOut = false;
//                                break;
//                            }
//                        }
//                        if (isOut)
//                        {
//                            writer.write(grid.measureDistance(aCell, grid.search(r)) + "\t");
//                            writer.write(grid.halveDistance(aCell,grid.search(r))+"\t");
//                        }
//                    }
//                    writer.write(System.lineSeparator());
//                }
//            }
            writer.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            sequenceList = null;
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
//        return true;
    }

    private static void checkPath(String filePath)
    {
        File file = new File(filePath);
        if (!(file.exists() && file.isDirectory()))
            file.mkdirs();
//        if (!(file.exists()))file.mkdirs();
    }

    public static void write(Cell[][] cells, int level)
    {
        if (cells == null)
        {
            return;
        }
        checkPath(outPath);

        FileWriter writer = null;

        String fileName = "codeOrder";
        try
        {
            writer = new FileWriter(outPath + fileName + level + ".txt",
                true);

            for (Cell aCell : cells[level])
            {
                writer.write(aCell.toString());
                writer.write(System.lineSeparator());
            }
            writer.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //ranks = null;
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void write(String title, Object object)
    {
        checkPath(outPath);
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(outPath + title + ".txt", true);
            writer.write(object.toString() + System.lineSeparator());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //ranks = null;
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void write(String title, Trigon[] trigons)
    {
        checkPath(outPath);
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(outPath + title + ".txt", true);
            for (Trigon trigon : trigons)
            {
                writer.write(trigon.getGeocode() + " " + trigon.getArea(Term.getSphere())
                    + System.lineSeparator());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //ranks = null;
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void write(String title, List<Object> objects)
    {
        checkPath(outPath);
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(outPath + title + ".txt");
            for (Object object : objects)
            {
                writer.write(object.toString() + System.lineSeparator());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //ranks = null;
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
