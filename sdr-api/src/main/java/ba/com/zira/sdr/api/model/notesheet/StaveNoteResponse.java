package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StaveNoteResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Keys")
    private String keys;
    @Schema(description = "Duration")
    private String duration;
    @Schema(description = "Accidental")
    private String accidental;
    @Schema(description = "Is dotted")
    private Boolean dotted;
    @Schema(description = "stave")
    private Long stave;
}
