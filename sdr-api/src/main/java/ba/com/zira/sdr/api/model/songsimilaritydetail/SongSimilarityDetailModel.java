package ba.com.zira.sdr.api.model.songsimilaritydetail;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an SongSimilarityDetail response")
public class SongSimilarityDetailModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the song")
    private Long id;
    @Schema(description = "Name of the person who created it")
    private String createdBy;
    @Schema(description = "Grade of the song")
    private String grade;
    @Schema(description = "Modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the sample")
    private String modifiedBy;
    @Schema(description = "Status", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "User code")
    private String userCode;

}
