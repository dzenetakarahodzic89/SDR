package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a short song response of an album")
public class SongOfAlbum implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Id of the song")
    private Long id;

    @NotBlank
    @Schema(description = "Name of the song")
    private String name;

    @NotBlank
    @Schema(description = "Genre of the song")
    private String genreName;

    @NotBlank
    @Schema(description = "Playtime of the song")
    private String playtime;

}
