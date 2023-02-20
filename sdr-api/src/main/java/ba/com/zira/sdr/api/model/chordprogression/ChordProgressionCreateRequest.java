package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a chord progression")
public class ChordProgressionCreateRequest implements Serializable {
    private static final long serialVersionUID = 1;
    @NotBlank
    @Schema(description = "Name of the chord progression")
    private String name;
    @NotBlank
    @Schema(description = "Information description")
    private String information;
}
