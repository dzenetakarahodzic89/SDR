package ba.com.zira.sdr.api.model.genre;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties to update a genre")
public class GenreModelUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the genre")
    private Long id;

    @Size(min = 1)
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
