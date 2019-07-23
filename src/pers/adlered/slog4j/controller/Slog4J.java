package pers.adlered.slog4j.controller;

import pers.adlered.slog4j.services.Cache;
import pers.adlered.slog4j.services.Definer;
import pers.adlered.slog4j.services.Formater;
import pers.adlered.slog4j.services.Printer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;

public class Slog4J extends Appender {
    public static final Appender append = null;
    public static final Getter get = null;
    public static final MySQL mysql = null;

    //设置格式
    public static void setFormat(String format) { Definer.format = format; }

    //格式化日志并输出
    public static void flush() {
        new Formater();
        new Printer();
        wipeLogList();
    }
    public static void write() { flush(); }

    //重置日志缓存
    public static void wipeLogList() { Cache.logList = new ArrayList<String>(); }


    //设置格式为默认
    public static void restoreFormatDefault() { Definer.format = Definer.defaultFormat; }

    /**
     * 设置时间单位
     * @param timeUnit 0-12，为不同输出复杂程度
     */
    public static void setTimeUnit(Integer timeUnit) { Definer.timeUnit = timeUnit; }

    //切换显示颜色模式
    public static void disableColor(boolean wouldYouLikeTo) {
            Definer.disableColor = wouldYouLikeTo;
    }

    //切换文件是否输出颜色
    public static void disableFileOutputColor(boolean wouldYouLikeTo) {
        Definer.disableFileOutputColor = wouldYouLikeTo;
    }

    //获取当前设置目录的绝对位置
    public static String getFileAbsolutePath() {
        return new File(Definer.dir + "/" + new SimpleDateFormat("yyyy-MM-dd HH").format(new Date()) + "H.txt").getAbsolutePath();
    }

    //获取Slog4J版本号
    public static String getSlog4JVersion() {
        return Definer.version;
    }

    //获取开发者信息
    public static String getDeveloperInfo() {
        return Definer.developerInfo;
    }

    //设置日志保存目录
    public static void setLogDir(String path) {
        Definer.dir = path;
    }

    //关闭Slog4J全部线程池，以便关闭Tomcat
    public static void shutdownAllSlog4JThreadPool() {
        Definer.MySQLExecutor.shutdown();
        Definer.FileExecutor.shutdown();
        Definer.ConsoleExecutor.shutdown();
    }
    public static void close() {
        Definer.MySQLExecutor.shutdown();
        Definer.FileExecutor.shutdown();
        Definer.ConsoleExecutor.shutdown();
    }
    public static void start() {
        Definer.MySQLExecutor = Executors.newSingleThreadExecutor();
        Definer.FileExecutor = Executors.newSingleThreadExecutor();
        Definer.ConsoleExecutor = Executors.newSingleThreadExecutor();
    }
    public static void shutdownAllSlog4JThreadPoolNow() {
        Definer.MySQLExecutor.shutdownNow();
        Definer.FileExecutor.shutdownNow();
        Definer.ConsoleExecutor.shutdownNow();
    }

    /**
     * 自定义禁用或启用某个输出模式
     */
    public static void disableConsoleOutput() {
        Definer.disableAppendToConsole = true;
    }
    public static void enableConsoleOutput() {
        Definer.disableAppendToConsole = false;
    }
    public static void disableFileOutput() {
        Definer.disableAppendToFile = true;
    }
    public static void enableFileOutput() {
        Definer.disableAppendToFile = false;
    }
    public static void disableMySQLOutput() {
        Definer.disableAppendToMySQL = true;
    }
    public static void enableMySQLOutput() {
        Definer.disableAppendToMySQL = false;
    }

    /**
     * 设置级别方法
     */
    public static void isBootLevel() {
        if (Definer.disableColor) {
            Cache.level = "BOOT";
        } else {
            Cache.level = "\033[36mBOOT\033[0m";
        }
    }
    public static void isLogLevel() {
        if (Definer.disableColor) {
            Cache.level = "LOG";
        } else {
            Cache.level = "\033[36mLOG\033[0m";
        }
    }
    public static void isInfoLevel() {
        if (Definer.disableColor) {
            Cache.level = "INFO";
        } else {
            Cache.level = "\033[32mINFO\033[0m";
        }
    }
    public static void isWarnLevel() {
        if (Definer.disableColor) {
            Cache.level = "WARN";
        } else {
            Cache.level = "\033[31;1mWARN\033[0m";
        }
    }
    public static void isImportantLevel() {
        if (Definer.disableColor) {
            Cache.level = "IMPORTANT";
        } else {
            Cache.level = "\033[31;1mIMPORTANT\033[0m";
        }
    }
    public static void isErrorLevel() {
        if (Definer.disableColor) {
            Cache.level = "ERROR";
        } else {
            Cache.level = "\033[31;1mERROR\033[0m";
        }
    }
    public static void isCrashLevel() {
        if (Definer.disableColor) {
            Cache.level = "CRASH";
        } else {
            Cache.level = "\033[31;1mCRASH\033[0m";
        }
    }
    public static void isDownLevel() {
        if (Definer.disableColor) {
            Cache.level = "DOWN";
        } else {
            Cache.level = "\033[31;1mDOWN\033[0m";
        }
    }
    public static void isShutdownLevel() {
        if (Definer.disableColor) {
            Cache.level = "SHUTDOWN";
        } else {
            Cache.level = "\033[31;1mSHUTDOWN\033[0m";
        }
    }
    public static void isStackTracerLevel() {
        if (Definer.disableColor) {
            Cache.level = "STACKTRACER";
        } else {
            Cache.level = "\033[31;1mSTACKTRACER\033[0m";
        }
    }
    public static void isNullLevel() {
        if (Definer.disableColor) {
            Cache.level = "?";
        } else {
            Cache.level = "\033[37;1m?\033[0m";
        }
    }

    /**
     * 级别判断
     */
    public static void setLevelByString(String level) {
        level = level.toLowerCase();
        switch (level) {
            case "boot":
                isBootLevel();
                break;
            case "log":
                isLogLevel();
                break;
            case "info":
                isInfoLevel();
                break;
            case "warn":
                isWarnLevel();
                break;
            case "important":
                isImportantLevel();
                break;
            case "error":
                isErrorLevel();
                break;
            case "crash":
                isCrashLevel();
                break;
            case "down":
                isDownLevel();
                break;
            case "shutdown":
                isShutdownLevel();
                break;
            case "stacktracer":
                isStackTracerLevel();
                break;
            case "null":
                isNullLevel();
                break;
            default:
                isNullLevel();
                break;
        }
    }
}
