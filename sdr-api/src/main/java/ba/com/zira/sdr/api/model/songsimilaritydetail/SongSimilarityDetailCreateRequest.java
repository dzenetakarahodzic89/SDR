package ba.com.zira.sdr.api.model.songsimilaritydetail;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creating details of a song")

public class SongSimilarityDetailCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Grade of the song")
    private String grade;

    @NotBlank
    @Schema(description = "User code")
    private String userCode;

    @Schema(description = "ID of the song sample")
    private Long songSimilarityId;

}
