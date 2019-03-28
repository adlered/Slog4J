package pers.adlered.slog4j.services;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储动态资源
 */

public class Cache {
    public static List<String> logList = new ArrayList<String>();
    public static String level = "";
    public static RandomAccessFile randomAccessFile;
    public static String filename = "";
    public static long count = 0;
}
