package ba.com.zira.sdr.api.model.songsimilaritydetail;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a sample")
public class SongSimilarityDetailUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the song sample detail")
    private Long id;

    @NotBlank
    @Schema(description = "Grade of the song")
    private String grade;

    @Schema(description = "ID of the song sample")
    private Long songSimilarityId;

}