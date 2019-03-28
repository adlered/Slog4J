package pers.adlered.slog4j.services;

import pers.adlered.slog4j.services.executor.ConsoleExecutor;
import pers.adlered.slog4j.services.executor.FileExecutor;
import pers.adlered.slog4j.services.executor.MySQLExecutor;

import java.io.File;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 打印工厂
 * 从Cache获取LogList并打印
 */

public class Printer {
    public Printer() {
        if (!Definer.disableAppendToConsole) {
            printToConsole();
        }
        if (!Definer.disableAppendToFile) {
            printToFile();
        }
        if (!Definer.disableAppendToMySQL) {
            mySQLWriter();
        }
    }

    protected void printToConsole() {
        Definer.ConsoleExecutor.execute(new ConsoleExecutor(Cache.logList));
    }

    protected void printToFile() {
        fileProcesser();
        logWriter();
    }

    protected void fileProcesser() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());
        if (date != Cache.filename) {
            Definer.file = new File(Definer.dir + "/" + date + "H.txt");
            Definer.fileDir = new File(Definer.dir);
            try {
                if (!Definer.fileDir.exists()) {
                    Definer.fileDir.mkdirs();
                }
                if (!Definer.file.exists()) {
                    Definer.file.createNewFile();
                }
            } catch (Exception e) {
                GeneralInnerExceptionHandler.handle(e);
            }
            Cache.filename = date;
        }
    }

    protected void logWriter() {
        Definer.FileExecutor.execute(new FileExecutor(Cache.logList));
    }

    protected void mySQLWriter() {
        mySQLConnector();
        createTable();
        writeLogToMySQL();
    }

    protected void mySQLConnector() {
        if (!Definer.connectionHasBeenConnected) {
            try {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (Exception e) {
                    Class.forName("com.mysql.jdbc.Driver");
                }
            } catch (Exception e) {
                new InnerOutput("JDBC Not detected. Did you imported?");
                e.printStackTrace();
            }
            try {
                Definer.connection = DriverManager.getConnection(Definer.mySQLURL, Definer.mySQLUser, Definer.mySQLPassword);
                Definer.statement = Definer.connection.createStatement();
                Definer.connectionHasBeenConnected = true;
            } catch (Exception e) {
                new InnerOutput("MySQL connection refused. Please check out your configuration.");
                e.printStackTrace();
            }
        }
    }

    protected void createTable() {
        if (!Definer.databaseHasBeenSetup) {
            String sql = "CREATE TABLE Slog4J  (" +
                    "  id int(0) NOT NULL AUTO_INCREMENT," +
                    "  text varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ");";
            try {
                Definer.statement.executeUpdate(sql);
                new InnerOutput("Thank you for using Slog4j! We are creating table \"Slog4J\" into your MySQL database...");
            } catch (Exception e) {
                Definer.databaseHasBeenSetup = true;
            }
        }
    }

    protected void writeLogToMySQL() {
        for (String i:Cache.logList) {
            Definer.MySQLExecutor.execute(new MySQLExecutor(i));
        }
    }
}
