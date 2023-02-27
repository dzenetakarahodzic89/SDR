package ba.com.zira.sdr.core.utils;

import java.util.List;

public class PlayTimeHelper {

    private PlayTimeHelper() {
    }

    public static String totalPlayTime(final List<String> playtimes) {
        var seconds = 0;
        for (var playtime : playtimes) {
            seconds += playTimeToSeconds(playtime);
        }
        return secondsToPlayTime(seconds);
    }

    public static int playTimeToSeconds(final String playtime) {
        var elements = playtime.split(":");
        return Integer.parseInt(elements[0]) * 3600 + Integer.parseInt(elements[1]) * 60 + Integer.parseInt(elements[2]);
    }

    public static String secondsToPlayTime(final int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds - hours * 3600) / 60;
        int secs = seconds - (hours * 3600 + minutes * 60);
        return (hours < 10 ? "0" : "") + Integer.toString(hours) + ":" + (minutes < 10 ? "0" : "") + Integer.toString(minutes) + ":"
                + (seconds < 10 ? "0" : "") + Integer.toString(secs);
    }
}
