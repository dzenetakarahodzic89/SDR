package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChordProgressionOverview implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    @Schema(description = "The status of the chord", allowableValues = { "Inactive", "Active" })
    private String status;
    private String information;

    private String outlineText;
    private String imageUrl;
    private long songCount;

    public ChordProgressionOverview(Long id, String name, String status, String information, String outlineText, long songCount) {
        super();
        this.id = id;
        this.name = name;
        this.status = status;
        this.information = information;
        this.outlineText = outlineText;
        this.songCount = songCount;
    }

}
