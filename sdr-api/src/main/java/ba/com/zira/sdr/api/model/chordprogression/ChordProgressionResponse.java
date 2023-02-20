package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChordProgressionResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    @Schema(description = "The status of the chord", allowableValues = { "Inactive", "Active" })
    private String status;
    private String information;
    @Schema(description = "Songs")
    private Map<Long, String> songNames;
}
