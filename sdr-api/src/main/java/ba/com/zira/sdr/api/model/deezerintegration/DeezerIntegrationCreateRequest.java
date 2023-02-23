package ba.com.zira.sdr.api.model.deezerintegration;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a deezer integration")
public class DeezerIntegrationCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the deezer integration")
    private String name;
    @NotBlank
    @Schema(description ="Request of the deezer integration")
    private String request;
    @NotBlank
    @Schema(description ="Response of the deezer integration")
    private String response;
}