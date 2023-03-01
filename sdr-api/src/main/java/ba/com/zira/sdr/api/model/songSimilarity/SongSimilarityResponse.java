package ba.com.zira.sdr.api.model.songSimilarity;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Properties of an SongSimilarityDetail response")
public class SongSimilarityResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier")
    private Long id;
    private String songName;
    private String albumName;
    private LocalDateTime dateOfRelease;

}
