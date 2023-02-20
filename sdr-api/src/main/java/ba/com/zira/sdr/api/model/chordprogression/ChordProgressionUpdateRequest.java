package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update of a chord progression")
public class ChordProgressionUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1;
    @Min(1)
    @Schema(description = "Unique identifier of the sample")
    private long id;
    @NotBlank
    @Schema(description = "Name of the chord progression")
    private String name;
    @NotBlank
    @Schema(description = "Information description")
    private String information;
    @NotBlank
    @Schema(description = "Status")
    private String status;

}
