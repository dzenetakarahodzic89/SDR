package ba.com.zira.sdr.api.model.moritsintegration;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a morits lyric integration")
public class MoritsIntegrationUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the morits lyric integration")
    private Long id;
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
