package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import ba.com.zira.sdr.api.model.song.SongResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of album response")
public class AlbumResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Album ID")
    private Long id;

    @NotBlank
    @Schema(description = "Release date of album")
    private LocalDateTime dateOfRelease;

    @NotBlank
    @Schema(description = "Informations about album")
    private String information;

    @NotBlank
    @Schema(description = "Album name")
    private String name;

    @NotBlank
    @Schema(description = "Album status")
    private String status;

    @NotBlank
    @Schema(description = "Album era")
    private String era;

    @Schema(description = "Artist name")
    private String artistName;

    @Schema(description = "Album songs")
    private List<SongResponse> songs;

    private List<SongAudio> audioUrls;

    private String imageUrl;
    private List<String> albumArtists;

}
