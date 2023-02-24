package ba.com.zira.sdr.api.model.song.fft;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a sample")
public class SongFftResultCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "SongFft results")
    private String fftResults;
    @NotNull
    @Schema(description = "SongID")
    private Long songID;

}