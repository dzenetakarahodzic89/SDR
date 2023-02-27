package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a Album")
public class AlbumUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Id of the album")
    private Long id;

    @NotNull
    @Schema(description = "Release date of album")
    private LocalDateTime dateOfRelease;

    @NotBlank
    @Schema(description = "Informations about album")
    private String information;

    @NotBlank
    @Schema(description = "Album name")
    private String name;

    @NotNull
    @Schema(description = "Album era")
    private Long eraId;
    
    @Schema(description = "Outline text")
    private Long outlineText;
}