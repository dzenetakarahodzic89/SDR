package ba.com.zira.sdr.core.utils;

import java.util.List;
import java.util.stream.Collectors;

public class HTMLEncoder {

    public static String createTableSongInformation(String title, String titleShort, String duration, Boolean explicitLyrics,
            String albumTitle, List<String> contributors) {

        String delimiter = ",";
        String joinedString = contributors.stream().collect(Collectors.joining(delimiter));
        return "<table>\r\n" + "  <tr>\r\n" + "    <td>Title:</td>\r\n" + "    <td>" + title + "</td>\r\n" + "  </tr>\r\n" + "  <tr>\r\n"
                + "    <td>Title short:</td>\r\n" + "    <td>" + titleShort + "</td>\r\n" + "  </tr>\r\n" + "  <tr>\r\n"
                + "    <td>Duration:</td>\r\n" + "    <td>" + duration + "</td>\r\n" + "  </tr>\r\n" + "  <tr>\r\n"
                + "    <td>Explicit lyrics:</td>\r\n" + "    <td>" + explicitLyrics + "</td>\r\n" + "  </tr>\r\n" + "  <tr>\r\n"
                + "    <td>Album:</td>\r\n" + "    <td>" + albumTitle + "</td>\r\n" + "  </tr>\r\n" + "  <tr>\r\n"
                + "    <td>Contributors</td>\r\n" + "    <td>" + joinedString + "</td>\r\n" + "  </tr>\r\n" + "</table>";
    }
}
