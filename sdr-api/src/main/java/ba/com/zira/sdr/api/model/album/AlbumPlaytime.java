package ba.com.zira.sdr.api.model.album;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class AlbumPlaytime {

    @NotBlank
    @Schema(description = "Album ID")
    private Long id;
    private Long playtimeInSeconds;

    @NotBlank
    @Schema(description = "Informations about album")
    private String information;

    @NotBlank
    @Schema(description = "Album name")
    private String name;

}
