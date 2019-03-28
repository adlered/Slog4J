package pers.adlered.slog4j.controller;

import pers.adlered.slog4j.services.*;

public class Appender {
    /**
     * 设置日志消息到缓存
     * @param log
     * @return
     */
    public static void log(String log) { try { Cache.logList.add(log); } catch (Exception error) { new InnerOutput("Internal error!"); error.printStackTrace(); }}
    public static void log(char[] log) { try { Cache.logList.add(String.valueOf(log)); } catch (Exception error) { new InnerOutput("Internal error!"); error.printStackTrace(); } }
    public static void log(Object log) { try { Cache.logList.add(String.valueOf(log)); } catch (Exception error) { new InnerOutput("Internal error!"); error.printStackTrace(); } }

    /**
     * 追加日志并立即输出
     * @param log
     * @return
     */
    public static void logAndFlush(String log) {
        try {
            Cache.logList.add(log);
            new Formater();
            new Printer();
            Slog4J.wipeLogList();
        } catch (Exception error) {
            GeneralInnerExceptionHandler.handle(error);
        }
    }
    public static void logAndFlush(char[] log) { try { logAndFlush(String.valueOf(log)); } catch (Exception error) { new InnerOutput("Internal error!"); error.printStackTrace(); } }
    public static void logAndFlush(Object log) { try { logAndFlush(String.valueOf(log)); } catch (Exception error) { new InnerOutput("Internal error!"); error.printStackTrace(); } }

    /**
     * 指定级别并立即输出日志
     */
    public static void logAndFlushWithLevel(String level, String log) {
        try {
            Cache.logList.add(log);
            Slog4J.setLevelByString(level);
            new Formater();
            new Printer();
            Slog4J.wipeLogList();
        } catch (Exception error) {
            GeneralInnerExceptionHandler.handle(error);
        }
    }
    public static void logAndFlushWithLevel(String level, char[] log) { try { logAndFlushWithLevel(level, String.valueOf(log)); } catch (Exception error) { new InnerOutput("Internal error!"); error.printStackTrace(); } }
    public static void logAndFlushWithLevel(String level, Object log) { try { logAndFlushWithLevel(level, String.valueOf(log)); } catch (Exception error) { new InnerOutput("Internal error!"); error.printStackTrace(); } }

    /**
     * 打印错误报告
     * @param e
     */
    public static void printStackTrace(Exception e) {
        try {
            //将未输出内容强制输出
            new Formater();
            new Printer();
            Slog4J.wipeLogList();
            //开始处理异常
            String level = Cache.level;
            Slog4J.isStackTracerLevel();
            if (Definer.disableColor) {
                logAndFlush("STACK TRACE BEGIN >>>");
                logAndFlush(e.getClass());
                StackTraceElement[] get = e.getStackTrace();
                for (StackTraceElement i : get) {
                    logAndFlush("   at " + i.toString());
                }
                logAndFlush("<<< STACK TRACE END");
            } else {
                logAndFlush("\033[31;1mSTACK TRACE BEGIN >>>\033[0m");
                logAndFlush("\033[31;1m" + e.getClass() + "\033[0m");
                StackTraceElement[] get = e.getStackTrace();
                for (StackTraceElement i : get) {
                    logAndFlush("\033[34;1m   at " + i.toString() + "\033[0m");
                }
                logAndFlush("\033[31;1m<<< STACK TRACE END\033[0m");
            }
            Cache.level = level;
        } catch (Exception error) {
            GeneralInnerExceptionHandler.handle(error);
        }
    }
}
