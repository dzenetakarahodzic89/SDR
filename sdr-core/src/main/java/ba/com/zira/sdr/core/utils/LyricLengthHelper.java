package ba.com.zira.sdr.core.utils;

import java.util.List;

public class LyricLengthHelper {

    private LyricLengthHelper() {
    }

    public static int totalLyricLength(final List<String> texts) {
        int length = 0;
        for (var text : texts) {
            length = length + text.length();
        }
        return length;
    }
}
