package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of album response")
public class AlbumResponse implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private Long eraId;
}
