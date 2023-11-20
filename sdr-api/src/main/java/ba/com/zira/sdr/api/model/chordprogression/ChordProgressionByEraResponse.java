package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "Response of chord progressions by eras")
@AllArgsConstructor
public class ChordProgressionByEraResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "Eras - format 1 - era1 ")
    private Long eraId;
    private String eraName;
    @Schema(description = "Chord progressions - format ID - number of songs (1:13)")
    private Map<Long, Integer> chordProgressions;
}