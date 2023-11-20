package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a Album")
public class AlbumCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

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

    private String outlineText;
    
    private String coverImageData;
    private String coverImage;
}