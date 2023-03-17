package ba.com.zira.sdr.api.model.moritsintegration;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongLyricData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the song")
    private Long id;
    @Schema(description = "Name of the song")
    private String name;
    @Schema(description = "Lyric language")
    private String language;
    @Schema(description = "Song playtime")
    private String playtime;
}
