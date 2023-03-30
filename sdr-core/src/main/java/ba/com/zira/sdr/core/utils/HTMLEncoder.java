package ba.com.zira.sdr.core.utils;

import java.util.List;
import java.util.stream.Collectors;

public class HTMLEncoder {

    private HTMLEncoder() {
        throw new IllegalStateException("HTMLEncoder class");
    }

    public static String createTableSongInformation(String title, String titleShort, String duration, Boolean explicitLyrics,
            String albumTitle, List<String> contributors) {

        String delimiter = ",";
        String joinedString = contributors.stream().collect(Collectors.joining(delimiter));
        String trOne = "  </tr>\r\n";
        String tdrn = "</td>\r\n";
        String td = "    <td>";
        String tr = "  </tr>\r\n  </tr>\r\n";
        return "<table>\r\n" + trOne + "    <td>Title:</td>\r\n" + td + title + tdrn + tr + "    <td>Title short:</td>\r\n" + td
                + titleShort + tdrn + tr + "    <td>Duration:</td>\r\n" + td + duration + tdrn + tr + "    <td>Explicit lyrics:</td>\r\n"
                + td + explicitLyrics + tdrn + tr + "    <td>Album:</td>\r\n" + td + albumTitle + tdrn + tr
                + "    <td>Contributors</td>\r\n" + td + joinedString + tdrn + trOne + "</table>";
    }
}
