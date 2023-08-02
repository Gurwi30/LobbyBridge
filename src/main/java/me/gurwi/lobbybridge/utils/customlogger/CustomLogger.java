package me.gurwi.lobbybridge.utils.customlogger;

public class CustomLogger {

    public static void log(LoggerTag loggerTag, String msg) {
        System.out.println(loggerTag.getTag() + msg);
    }

}
