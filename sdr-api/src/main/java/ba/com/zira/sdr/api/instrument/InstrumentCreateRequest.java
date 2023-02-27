package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

@Schema(description = "Properties for instrument create request")
public class InstrumentCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "instrument name")
    private String name;

    @Schema(description = "Instrument information")
    private String information;

    @Schema(description = "Instrument type")
    private String type;

    @Schema(description = "Outline text")
    private String outlineText;

    @Schema(description = "Cover image")
    private String coverImage;

    @Schema(description = "Cover image data")
    private String coverImageData;
}
