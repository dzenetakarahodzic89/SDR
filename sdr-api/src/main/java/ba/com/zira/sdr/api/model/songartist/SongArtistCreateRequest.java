package ba.com.zira.sdr.api.model.songartist;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Properties of song-artist create request")
public class SongArtistCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Id of associated song")
    private Long songId;

    @NotNull
    @Schema(description = "Id of associated artist")
    private Long artistId;

    @NotNull
    @Schema(description = "Id of associated label")
    private Long labelId;

    @NotNull
    @Schema(description = "Id of album that has this song")
    private Long albumId;
}
