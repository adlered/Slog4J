package pers.adlered.slog4j.controller;

import pers.adlered.slog4j.services.Definer;

/**
 * 预留Getter类，可以从外部读取缓存数据
 */

public class Getter {
    public static String format() {
        return Definer.format;
    }
}
