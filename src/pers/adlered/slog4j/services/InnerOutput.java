package pers.adlered.slog4j.services;

public class InnerOutput {
    public InnerOutput(String log) {
        System.out.println("[\033[32;1mSlog4J\033[0m] " + log);
    }
}
