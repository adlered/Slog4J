package pers.adlered.slog4j.services;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 存储静态资源
 */

public class Definer {
    public static String version = "1.0.0";
    public static String developerInfo = "GitHub: AdlerED";
    //>>变量修饰符 &&&是分隔符，用于分割前缀和后缀
    //正则表达式修饰符，需要转义字符（两个反斜杠）
    public static String modifierInRegEx = "\\$\\{&&&\\}";
    //原修饰符，为正则表达式修饰符删掉所有反斜杠后结果，两项必须同步修改
    public static String modifier = "${&&&}";
    public static String FileURL = "";

    public static ExecutorService MySQLExecutor = Executors.newSingleThreadExecutor();
    public static ExecutorService FileExecutor = Executors.newSingleThreadExecutor();
    public static ExecutorService ConsoleExecutor = Executors.newSingleThreadExecutor();

    public static File file;
    public static File fileDir;

    public static String defaultFormat = "[${level}]-[${time}]-[${count}] ${words}";
    public static String format = defaultFormat;
    public static Integer timeUnit = 0;
    public static String dir = "Slog4J";
    public static boolean disableColor = false;
    public static boolean databaseHasBeenSetup = false;
    public static boolean connectionHasBeenConnected = false;
    public static boolean disableFileOutputColor = false;

    public static boolean disableAppendToConsole = false;
    public static boolean disableAppendToFile = false;
    public static boolean disableAppendToMySQL = true;

    public static String mySQLURL = "";
    public static String mySQLUser = "";
    public static String mySQLPassword = "";

    public static Connection connection;
    public static Statement statement;
}
