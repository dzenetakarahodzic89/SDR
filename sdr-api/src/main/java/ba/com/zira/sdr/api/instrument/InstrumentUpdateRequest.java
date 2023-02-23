package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

@Schema(description = "Properties of instrument update request")

public class InstrumentUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Id of record for update")
    private Long id;

    @Schema(description = "Instrument name")
    private String name;

    @Schema(description = "Instrument information")
    private String information;

    @Schema(description = "Instrument type")
    private String type;

    @Schema(description = "Outline text")
    private String outlineText;
}
