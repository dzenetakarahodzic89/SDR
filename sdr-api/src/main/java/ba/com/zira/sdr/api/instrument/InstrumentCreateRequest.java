package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for instrument creat request")
public class InstrumentCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "Unique identifier of the sample")
    private Long id;

    @Schema(description = "Instrument name")
    private String instrumentName;

    @Schema(description = "Instrument information")
    private String instrumentInformation;

    @Schema(description = "Instrument type")
    private String instrumentType;

}
