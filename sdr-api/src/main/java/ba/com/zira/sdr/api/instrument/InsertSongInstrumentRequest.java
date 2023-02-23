package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for insert instruments to Song request")
public class InsertSongInstrumentRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name")
    private String name;
    @Schema(description = " Type of the instrumentId")
    private Long instrumentId;
    @Schema(description = " Type of the songId")
    private Long songId;
    @Schema(description = " Type of the personId")
    private Long personId;
}
