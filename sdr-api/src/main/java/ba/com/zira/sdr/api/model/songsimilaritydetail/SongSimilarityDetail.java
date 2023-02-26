package ba.com.zira.sdr.api.model.songsimilaritydetail;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an SongSimilarityDetail response")
public class SongSimilarityDetailResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the song sample detail")
    private Long id;

    @Schema(description = "Creation date")
    private LocalDateTime created;

    @Schema(description = "User who created it")
    private String createdBy;

    @Schema(description = "Grade of the song")
    private String grade;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the sample")
    private String modifiedBy;

    @Schema(description = "Status", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "User code")
    private String userCode;

    @Schema(description = "The ID of the songsample")
    private Long songSampleId;

}
