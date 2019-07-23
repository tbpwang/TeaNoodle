/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.joel.io;

import edu.joel.*;
import gov.nasa.worldwind.util.Logging;

import java.io.*;
import java.util.List;

/**
 * @author Zheng WANG
 * @create 2019/5/28
 * @description
 * @parameter
 */
public class IO
{
    private static boolean checkPath(String filePath)
    {
        File file = new File(filePath);
        //File parentFile = file.getParentFile();
        if (!file.exists())
        {
            return file.mkdirs();
        }
        return true;
    }

    public static void write(String fileName, String level, String content)
    {
        String path = "D:\\outData\\" + fileName + "\\";
        if (!checkPath(path))
        {
            String message = Logging.getMessage("FileError.创建文件夹出错");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }

        FileWriter rt;
        String pathFileName = path + level + ".txt";
        String outContent = "   " + content;
        try
        {
            rt = new FileWriter(pathFileName, true);

            rt.write(outContent);
            rt.write(System.lineSeparator());

            rt.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void write(String fileName, Cell cell)
    {
        String path = "D:\\outData\\" + fileName + "\\";
        if (!checkPath(path))
        {
            String message = Logging.getMessage("FileError.创建文件夹出错");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }

        FileWriter rt;
        String pathFileName = path + cell.getLevel() + ".txt";
        try
        {
            rt = new FileWriter(pathFileName, true);
            rt.write(cell.toString());
            rt.write(System.lineSeparator());
            rt.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void write(String fileName, Cell[] cells)
    {
        if (cells != null)
            for (Cell cell : cells)
            {
                write(fileName, cell);
            }
    }

    public static void write(String fileName, List<Cell> cellList)
    {
        if (cellList != null)
            for (Cell cell : cellList)
            {
                write(fileName, cell);
            }
    }
}
