package ba.com.zira.sdr.api.playlistga;

import java.io.Serializable;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PlaylistResponseGA implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Song id")
    private Long songid;

    @Schema(description = "Song name")
    private String songName;

    @Schema(description = "Scores")
    private Map<String, Long> scores;

    @Schema(description = "Genre id")
    private Long genreId;

    @Schema(description = "Genre name")
    private String genreName;

    @Schema(description = "Playtime in seconds")
    private Long playtime;
}
