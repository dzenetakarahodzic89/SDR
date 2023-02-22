package ba.com.zira.sdr.api.model.SongFFt;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a sample")
public class SongFftResultUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the SongFftResult")
    private long id;
    @NotBlank
    @Schema(description = "SongFft results")
    private String fftResults;
    @NotBlank
    @Schema(description = "SongID")
    private Long songID;
}