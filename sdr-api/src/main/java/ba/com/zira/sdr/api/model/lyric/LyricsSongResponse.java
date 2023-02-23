package ba.com.zira.sdr.api.model.lyric;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of response for route album/{id}/songs")
public class LyricsSongResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "The Language of the lyric")
    private String language;

    @Schema(description = "The ID of the song")
    private Long songId;

    @Schema(description = "The Name of the song")
    private String songName;

    @Schema(description = "The Text of the lyric(song)")
    private String text;

}
