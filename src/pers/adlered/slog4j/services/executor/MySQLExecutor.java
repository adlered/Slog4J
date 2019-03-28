package pers.adlered.slog4j.services.executor;

import pers.adlered.slog4j.services.Definer;
import pers.adlered.slog4j.services.InnerOutput;

public class MySQLExecutor extends Thread {
    public String log;

    public MySQLExecutor(String log) {
        this.log = log;
    }

    @Override
    public void run() {
        //过滤颜色
        log = log.replaceAll("\\[3[1-7]{1}(;)?[1-7]?m|\\[0m", "");
        String sql = "INSERT INTO Slog4J (text) " +
                "VALUES ('" + log + "')";
        try {
            Definer.statement.executeUpdate(sql);
        } catch (Exception e) {
            new InnerOutput("Log writing to MySQL error!");
            e.printStackTrace();
        }
    }
}
