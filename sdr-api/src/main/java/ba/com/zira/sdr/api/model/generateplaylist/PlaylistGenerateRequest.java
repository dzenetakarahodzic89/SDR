package ba.com.zira.sdr.api.model.generateplaylist;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for generating a playlist")
public class PlaylistGenerateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Genre id of our songs")
    private Long genreId;

    @Schema(description = "Include remixes")
    private Boolean includeRemixes;

    @Schema(description = "Include covers")
    private Boolean includeCovers;

    @Schema(description = "Amount of songs")
    private Long amountOfSongs;
}
