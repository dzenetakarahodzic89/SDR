package ba.com.zira.sdr.api.model.audiodb;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for the update of an AudioDB Integration")
public class AudioDBIntegrationUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the AudioDB integration")
    private Long id;

    @NotBlank
    @Schema(description = "Name of the AudioDB integration document")
    private String name;



}