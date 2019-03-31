package pers.adlered.slog4j;

import pers.adlered.slog4j.controller.Slog4J;

public class Tester {
    public static void main(String[] args) {
        //Slog4J.disableColor(false);
        //Slog4J.disableFileOutput();
        //Slog4J.disableConsoleOutput();
        Slog4J.mysql.setURL("jdbc:mysql://localhost/Users?useSSL=false");
        Slog4J.mysql.setUser("root");
        Slog4J.mysql.setPassword("@Adler42650909951");
        Slog4J.enableMySQLOutput();

        Slog4J.setLogDir("/Users/adler/logs/");
        Slog4J.logAndFlush("加载中Process is begin, please wait...");
        Slog4J.logAndFlushWithLevel("boot","Booting system...");
        Slog4J.setTimeUnit(0);
        try {
            int a = 1/0;
            throw new IllegalAccessException();
        } catch (Exception e) {
            Slog4J.printStackTrace(e);
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Slog4J.logAndFlushWithLevel("info",i);
        }
        long b = System.currentTimeMillis() - a;
        Slog4J.logAndFlushWithLevel("warn","Boot failed!");
        Slog4J.logAndFlush(b);
        Slog4J.logAndFlush("helloworld~!!!");
        Slog4J.close();
        Slog4J.start();
        Slog4J.logAndFlush("hi");
        Slog4J.logAndFlush(Slog4J.getFileAbsolutePath());
        Slog4J.close();
        //System.out.println(Slog4J.getFileAbsolutePath());
        /*Slog4J.isInfoLevel();
        Slog4J.logAndFlush("Hello world!");*/
        Slog4J.setTimeUnit(8);
        Slog4J.logAndFlushWithLevel("info","Slog4J time unit is customized!");
    }
}
