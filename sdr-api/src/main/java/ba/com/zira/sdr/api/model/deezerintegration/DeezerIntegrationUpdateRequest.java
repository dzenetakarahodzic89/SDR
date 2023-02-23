package ba.com.zira.sdr.api.model.deezerintegration;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a sample")
public class DeezerIntegrationUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the deezer integration")
    private long id;
    @NotBlank
    @Schema(description = "Name of the deezer integration")
    private String name;
    @NotBlank
    @Schema(description = "Request of the deezer integration")
    private String request;
    @NotBlank
    @Schema(description = "Response of the deezer integration")
    private String response;
}