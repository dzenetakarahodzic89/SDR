package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response of chord progressions by eras")
public class ChordSongAlbumEraResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long eraId;
    private String eraName;
    private Long chordId;
    private String songName;

    public ChordSongAlbumEraResponse(Long eraId, String eraName, Long chordId, String songName) {
        super();
        this.eraId = eraId;
        this.eraName = eraName;
        this.chordId = chordId;
        this.songName = songName;
    }

}
