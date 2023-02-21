package ba.com.zira.sdr.api.model.songinstrument;


import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a songInstrument")
public class SongInstrumentUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the songInstrument")
    private Long id;
    @NotBlank
    @Schema(description = "Content of songInstrument")
    private String content;
}