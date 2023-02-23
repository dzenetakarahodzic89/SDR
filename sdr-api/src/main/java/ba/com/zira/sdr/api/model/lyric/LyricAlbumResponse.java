package ba.com.zira.sdr.api.model.lyric;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of response for route album/{id}/songs")
public class LyricAlbumResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Total Lyric Length for Album")
    private int totalLyricLength;

    @NotBlank
    @Schema(description = "Map of lyrics")
    private Map<String, List<LyricsSongResponse>> map;
}
