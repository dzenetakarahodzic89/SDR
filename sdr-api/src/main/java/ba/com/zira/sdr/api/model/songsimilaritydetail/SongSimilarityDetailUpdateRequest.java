package ba.com.zira.sdr.api.model.songsimilaritydetail;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a sample")
public class SongSimilarityDetailUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the song sample detail")
    private Long id;

    @Schema(description = "Grade of the song")
    private BigDecimal grade;

    @Schema(description = "ID of the song sample")
    private Long songSimilarityId;

}