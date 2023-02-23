package ba.com.zira.sdr.api.model.songinstrument;


import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data; 

@Data
@Schema(description = "Properties for creation of songInstrument")
public class SongInstrumentCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name of instrument")
    private String name;

    @Schema(description = "Id of the instrument")
    private Long instrumentId;

    @Schema(description = "Id of the person")
    private Long personId;

    @Schema(description = "Id of the song")
    private Long songId;
}