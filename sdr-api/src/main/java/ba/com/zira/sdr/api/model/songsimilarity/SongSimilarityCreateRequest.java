package ba.com.zira.sdr.api.model.songsimilarity;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a song similarity")
public class SongSimilarityCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Song A")
    private Long songA;

    @Schema(description = "Song B")
    private Long songB;

}