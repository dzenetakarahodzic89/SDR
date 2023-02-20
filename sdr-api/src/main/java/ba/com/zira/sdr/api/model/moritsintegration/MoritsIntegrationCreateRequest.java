package ba.com.zira.sdr.api.model.moritsintegration;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a morits lyric integration")
public class MoritsIntegrationCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the morits lyric integration")
    private String name;
    @NotBlank
    @Schema(description = "Request of the morits lyric integration")
    private String request;
    @NotBlank
    @Schema(description = "Response of the morits lyric integration")
    private String response;
}
