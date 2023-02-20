package ba.com.zira.sdr.api.model.songartist;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of song-artist create request")
public class SongArtistUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Id of record for update")
    private Long id;

    @Schema(description = "Id of associated song")
    private Long songId;

    @Schema(description = "Id of associated artist")
    private Long artistId;

    @Schema(description = "Id of associated label")
    private Long labelId;

    @Schema(description = "Id of album that has this song")
    private Long albumId;

}
