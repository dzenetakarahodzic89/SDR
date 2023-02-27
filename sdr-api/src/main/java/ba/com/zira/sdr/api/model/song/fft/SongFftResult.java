package ba.com.zira.sdr.api.model.song.fft;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an SongFft response")
public class SongFftResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the SongFft")
    private Long id;
    @Schema(description = "Creation date")
    private LocalDateTime created;

    @Schema(description = "User that created the SongFft")
    private String createdBy;
    @Schema(description = "Results of the SongFft")
    private String fftResults;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the sample")
    private String modifiedBy;
    @Schema(description = "The status of the SongFft", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "SongId")
    private Long songID;
}