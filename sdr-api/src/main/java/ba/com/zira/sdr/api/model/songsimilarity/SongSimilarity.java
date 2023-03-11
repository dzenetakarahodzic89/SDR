package ba.com.zira.sdr.api.model.songsimilarity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Properties of an SongSimilarityDetail response")
public class SongSimilarity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier")
    private Long id;

    @Schema(description = "created")
    private LocalDateTime created;

    @Schema(description = "created_by")
    private String createdBy;

    @Schema(description = "modified")
    private LocalDateTime modified;

    @Schema(description = "modified_by")
    private String modifiedBy;

    @Schema(description = "status")
    private String status;

    @Schema(description = "total_similarity_score")
    private BigDecimal totalSimilarityScore;

    @Schema(description = "song_a_id")
    private Long songA;

    @Schema(description = "song_b_id")
    private Long songB;

}
