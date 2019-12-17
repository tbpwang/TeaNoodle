/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.wang.io;

import edu.wang.*;
import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.util.Logging;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

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

    public static void write(String folderName, String title, String content)
    {
        String path = "D:\\outData\\" + folderName + "\\";
        if (!checkPath(path))
        {
            String message = Logging.getMessage("FileError.创建文件夹出错");
            Logging.logger().severe(message);
            throw new IllegalArgumentException(message);
        }

        FileWriter rt;
        String pathFileName = path + title + ".txt";
        // String outContent = "   " + content;
        try
        {
            rt = new FileWriter(pathFileName, true);

            rt.write(content);
            rt.write(System.lineSeparator());

            rt.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void write(String folderName, Cell cell)
    {
        String path = "D:\\outData\\" + folderName + "\\";
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

    public static void write(String folderName, Cell[] cells)
    {
        if (cells != null)
            for (Cell cell : cells)
            {
                write(folderName, cell);
            }
    }

    public static void write(String folderName, List<Cell> cellList)
    {
        if (cellList != null)
            for (Cell cell : cellList)
            {
                write(folderName, cell);
            }
    }

    public static String formatDouble(double number, int digit)
    {
        StringBuilder pattern = new StringBuilder("0.");
        String c = "0";
        if (digit <= 0 || digit > 10)
        {
            digit = 6;
        }
        for (int i = 0; i < digit; i++)
        {
            pattern.append(c);
        }
        return new DecimalFormat(pattern.toString()).format(number);
    }

    public static String formatDouble(double number)
    {
        String pattern = Math.abs(number) >= 100 ? "0.000000E0" : "0.000000";
        return new DecimalFormat(pattern).format(number);
    }

    public static List<Vec4> parsePointsToVec(String fileName)
    {
        // 读取txt文档，该文档中存储了Vec4的点，
        // 每行一个点，格式为 x y z
        File file = new File(fileName);
        BufferedReader reader = null;
        //StringBuffer stringBuffer = new StringBuffer();
        List<Vec4> parseVec = new ArrayList<>();
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null)
            {
                //stringBuffer.append(tempStr);
                String[] str = tempStr.split("\t");
                parseVec.add(
                    new Vec4(Double.parseDouble(str[0]), Double.parseDouble(str[1]), Double.parseDouble(str[2])));
                //System.out.println(vec4);
            }
            reader.close();
//            return stringBuffer.toString();
            return parseVec;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        return parseVec;
    }

}
