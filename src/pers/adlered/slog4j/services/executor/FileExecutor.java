package pers.adlered.slog4j.services.executor;

import pers.adlered.slog4j.services.Cache;
import pers.adlered.slog4j.services.Definer;
import pers.adlered.slog4j.services.GeneralInnerExceptionHandler;
import pers.adlered.slog4j.services.InnerOutput;

import java.io.RandomAccessFile;
import java.util.List;

/**
 * 废弃的方法
 */

public class FileExecutor extends Thread {
    List<String> list = null;

    public FileExecutor(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                //由于字体颜色可能导致部分终端不兼容乱码，tail -f支持彩色
                //i = i.replaceAll("\\[3[1-7]{1}(;)?(1)?m|\\[0m|\\^\\[", "");
                String log = "";
                for (String i : list) {
                    log += i + "\r\n";
                }
                Cache.randomAccessFile = new RandomAccessFile(Definer.file, "rw");
                Cache.randomAccessFile.seek(Cache.randomAccessFile.length());
                Cache.randomAccessFile.writeBytes(log);
                Cache.randomAccessFile.close();
            } catch (Exception e) {
                GeneralInnerExceptionHandler.handle(e);
            }
        }
    }
}
