package ba.com.zira.sdr.api.model.songinstrument;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of songInstrument")
public class SongInstrumentCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of song instrument")
    private String name;

    @NotNull
    @Schema(description = "Song id")
    private Long songId;

    @NotNull
    @Schema(description = "Instrument id")
    private Long instrumentId;

    @NotNull
    @Schema(description = "Person id")
    private Long personId;

    @Schema(description = "Outline text")
    private String outlineText;
}