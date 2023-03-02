package ba.com.zira.sdr.api.model.songSimilarity;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema(description = "Properties of a SongSimilarityDetail response")
public class SongSimilarityResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long songAId;
    private String songAName;
    private String albumAName;
    private LocalDateTime albumAReleaseDate;
    private Long songBId;
    private String songBName;
    private String albumBName;
    private LocalDateTime albumBReleaseDate;
    private String songAimageUrl;
    private String songBimageUrl;

    public SongSimilarityResponse(Long id, Long songAId, String songAName, String albumAName, LocalDateTime albumAReleaseDate, Long songBId,
            String songBName, String albumBName, LocalDateTime albumBReleaseDate) {
        this.id = id;
        this.songAId = songAId;
        this.songAName = songAName;
        this.albumAName = albumAName;
        this.albumAReleaseDate = albumAReleaseDate;
        this.songBId = songBId;
        this.songBName = songBName;
        this.albumBName = albumBName;
        this.albumBReleaseDate = albumBReleaseDate;
    }

}
