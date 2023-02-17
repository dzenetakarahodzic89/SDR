package ba.com.zira.sdr.api.model.genre;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a genre")
public class GenreModelCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the genre")
    private String name;

    @Schema(description = "Information about the genre")
    private String information;

    @Schema(description = "Type")
    private String type;

    @Schema(description = "Main genre ID")
    private Long mainGenreID;

}
