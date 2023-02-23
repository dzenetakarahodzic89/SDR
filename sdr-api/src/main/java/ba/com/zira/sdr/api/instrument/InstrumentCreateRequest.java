package ba.com.zira.sdr.api.instrument;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data

@Schema(description = "Properties for instrument create request")
public class InstrumentCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "instrument name")
    private String instrumentName;

    @Schema(description = "Instrument information")
    private String instrumentInformation;

    @Schema(description = "Instrument type")
    private String instrumentType;

}
