package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlbumArtistSingleResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Release date of album")
    private Long id;

    @NotBlank
    @Schema(description = "Album name")
    private String name;

    @Schema(description = "Era name")
    private String eraName;

    @NotNull
    @Schema(description = "Release date of album")
    private LocalDateTime dateOfRelease;

}
