package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import ba.com.zira.sdr.api.model.song.SongResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of response for route album/{id}/songs")
public class AlbumSongResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Release date of album")
    List<SongResponse> songs;

    @NotBlank
    @Schema(description = "Total playtime")
    String totalPlayTime;

    @NotBlank
    @Schema(description = "Map of songs")
    Map<Long, SongResponse> map;
}
