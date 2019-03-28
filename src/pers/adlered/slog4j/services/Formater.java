package pers.adlered.slog4j.services;

import pers.adlered.slog4j.controller.Slog4J;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 格式化工厂
 */

public class Formater {
    public Formater() {
        Cache.logList = processer(Cache.logList, Definer.format);
    }

    //处理器
    private List<String> processer(List<String> logList, String format) {
        List<String> logger = new ArrayList<String>();
        for (String log:logList) {
            logger.add(analyzer(log));
        }
        return logger;
    }

    //分析器
    private String analyzer(String log) {
        String format = Definer.format;
        //>>开始将语句格式化
        //格式化消息
        if (format.indexOf(Definer.modifier.split("&&&")[0] + "words" + Definer.modifier.split("&&&")[1]) != -1) {
            format = explainer(format, "words", log);
        }
        //格式化时间
        if (format.indexOf(Definer.modifier.split("&&&")[0] + "time" + Definer.modifier.split("&&&")[1]) != -1) {
            format = explainer(format, "time", new Generator().sumTime(Definer.timeUnit));
        }
        //格式化级别
        if (format.indexOf(Definer.modifier.split("&&&")[0] + "level" + Definer.modifier.split("&&&")[1]) != -1) {
            format = explainer(format, "level", new Generator().sumLevel());
        }
        //格式化执行序号
        if (format.indexOf(Definer.modifier.split("&&&")[0] + "count" + Definer.modifier.split("&&&")[1]) != -1) {
            format = explainer(format, "count", String.valueOf(new Generator().sumCount()));
        }
        Cache.count++;
        return format;
    }

    //生成器
    class Generator {
        public String sumTime(Integer unit) {
            String[] formatedDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()).split("-");
            String year = formatedDate[0];
            String month = formatedDate[1];
            String day = formatedDate[2];
            String hour = formatedDate[3];
            String minute = formatedDate[4];
            String second = formatedDate[5];
            String millisecond = formatedDate[6];
            switch (unit) {
                case 0:
                    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + ":" + millisecond;
                case 1:
                    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
                case 2:
                    return year + "-" + month + "-" + day + " " + hour + ":" + minute;
                case 3:
                    return year + "-" + month + "-" + day + " " + hour;
                case 4:
                    return year + "-" + month + "-" + day;
                case 5:
                    return year + "-" + month;
                case 6:
                    return month + "-" + day;
                case 7:
                    return hour + ":" + minute + ":" + second + ":" + millisecond;
                case 8:
                    return hour + ":" + minute + ":" + second;
                case 9:
                    return hour + ":" + minute;
                case 10:
                    return minute + ":" + second + ":" + millisecond;
                case 11:
                    return second + ":" + millisecond;
                case 12:
                    return minute + ":" + second;
                default:
                    new InnerOutput("Configuration of \"TimeUnit\" is invalid. Please enter a num between 1-12. Using default TimeUnit.");
                    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + ":" + millisecond;
            }
        }

        public String sumLevel() {
            if (Cache.level.isEmpty()) Slog4J.isNullLevel();
            return Cache.level;
        }

        public long sumCount() {
            return Cache.count;
        }
    }

    //语义解析器
    public String explainer(String format, String keyword, String replaceTo) {
        return format.replaceAll(Definer.modifierInRegEx.split("&&&")[0] + keyword + Definer.modifierInRegEx.split("&&&")[1], replaceTo);
    }
}
