package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlbumArtistResponse implements Serializable {


    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Release date of album")
    private Long id;

    @NotBlank
    @Schema(description = "Album name")
    private String name;

    @NotNull
    @Schema(description = "Release date of album")
    private LocalDateTime dateOfRelease;
    @Schema(description = "Release date of album")
    private List<AlbumArtistResponse> album;
    public AlbumArtistResponse(Long id, String name, LocalDateTime dateOfRelease) {
        this.id = id;
        this.name = name;
        this.dateOfRelease = dateOfRelease;
    }
    public AlbumArtistResponse(List<AlbumArtistResponse> albums) {
        this.album=albums;
    }




}
