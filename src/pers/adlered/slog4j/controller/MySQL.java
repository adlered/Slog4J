package pers.adlered.slog4j.controller;

import pers.adlered.slog4j.services.Definer;

public class MySQL {
    public static void setURL(String url) { Definer.mySQLURL = url; }
    public static void setUser(String user) {
        Definer.mySQLUser = user;
    }
    public static void setPassword(String password) {
        Definer.mySQLPassword = password;
    }
}
