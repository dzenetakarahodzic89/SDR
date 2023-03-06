package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for addition of a song and a label to an album")
public class SongOfAlbumUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Id of the album")
    private Long albumId;

    @NotNull
    @Schema(description = "Id of the song")
    private Long songId;

    @NotNull
    @Schema(description = "Id of the artist")
    private Long artistId;

    @NotNull
    @Schema(description = "Id of the label")
    private Long labelId;

}
