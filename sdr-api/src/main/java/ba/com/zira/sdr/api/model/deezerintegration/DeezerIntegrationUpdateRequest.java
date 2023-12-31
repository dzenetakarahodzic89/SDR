package ba.com.zira.sdr.api.model.deezerintegration;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a Deezer integration")
public class DeezerIntegrationUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the Deezer integration")
    private String id;
    @NotBlank
    @Schema(description = "Name of the Deezer integration")
    private String name;
    @NotBlank
    @Schema(description = "Request of the Deezer integration")
    private String request;
    @NotBlank
    @Schema(description = "Response of the Deezer integration")
    private String response;
}