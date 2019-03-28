package pers.adlered.slog4j.services;

public class GeneralInnerExceptionHandler {
    public static void handle(Exception e) {
        new InnerOutput("Internal error!");
        if (Definer.disableColor) {
            new InnerOutput("STACK TRACE BEGIN >>>");
            new InnerOutput("" + e.getClass());
            StackTraceElement[] get = e.getStackTrace();
            for (StackTraceElement i : get) {
                new InnerOutput("   at " + i.toString());
            }
            new InnerOutput("<<< STACK TRACE END");
        } else {
            new InnerOutput("\033[31;1mSTACK TRACE BEGIN >>>\033[0m");
            new InnerOutput("\033[31;1m" + e.getClass() + "\033[0m");
            StackTraceElement[] get = e.getStackTrace();
            for (StackTraceElement i : get) {
                new InnerOutput("\033[34;1m   at " + i.toString() + "\033[0m");
            }
            new InnerOutput("\033[31;1m<<< STACK TRACE END\033[0m");
        }
    }
}
