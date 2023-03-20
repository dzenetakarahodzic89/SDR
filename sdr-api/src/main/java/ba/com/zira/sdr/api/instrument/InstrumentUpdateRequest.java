package ba.com.zira.sdr.api.instrument;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of instrument update request")

public class InstrumentUpdateRequest extends InstrumentRequestSuperclass {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Id of record for update")
    private Long id;

}
