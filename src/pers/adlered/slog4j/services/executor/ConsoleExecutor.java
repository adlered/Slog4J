package pers.adlered.slog4j.services.executor;

import java.util.List;

public class ConsoleExecutor extends Thread {
    List<String> logList;

    public ConsoleExecutor(List<String> logList) {
        this.logList = logList;
    }

    @Override
    public void run() {
        for (String i:logList) {
            System.out.println(i);
        }
    }
}
