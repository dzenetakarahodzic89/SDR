package ba.com.zira.sdr.core.utils;

import java.util.List;

public class PlayTimeHelper {

    public static String totalPlayTime(final List<String> playtimes) {
        int seconds = 0;
        for (var playtime : playtimes) {
            seconds += playTimeToSeconds(playtime);
        }
        return secondsToPlayTime(seconds);
    }

    public static int playTimeToSeconds(final String playtime) {
        var elements = playtime.split(":");
        int seconds = Integer.parseInt(elements[0]) * 3600 + Integer.parseInt(elements[1]) * 60 + Integer.parseInt(elements[2]);
        return seconds;
    }

    public static String secondsToPlayTime(final int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds - hours * 3600) / 60;
        int secs = seconds - (hours * 3600 + minutes * 60);
        return Integer.toString(hours) + ":" + Integer.toString(minutes) + ":" + Integer.toString(secs);
    }
}
