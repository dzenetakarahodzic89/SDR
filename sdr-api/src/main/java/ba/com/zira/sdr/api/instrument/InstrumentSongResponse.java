package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstrumentSongResponse implements Serializable {

    @Schema(description = "Name")
    private Long id;
    @Schema(description = "Name of instrument")
    private String displayName;

}
