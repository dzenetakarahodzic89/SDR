package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StaveResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Width")
    private Integer width;
    @Schema(description = "Clef")
    private String clef;
    @Schema(description = "Time signature")
    private String timeSignature;
    @Schema(description = "Y position")
    private Integer yPosition;
    @Schema(description = "X position")
    private Integer xPosition;
    private List<StaveNoteResponse> staveNotes;
}
