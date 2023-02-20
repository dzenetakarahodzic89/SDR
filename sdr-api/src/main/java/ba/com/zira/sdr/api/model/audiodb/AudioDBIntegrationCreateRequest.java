package ba.com.zira.sdr.api.model.audiodb;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a AudioDB implementation")
public class AudioDBIntegrationCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the AudioDB integration")
    private String name;

    /*
    @NotBlank
    @Schema(description = "The status of the AudioDB integration", allowableValues = { "Inactive", "Active" })
    private String status;
     */

}