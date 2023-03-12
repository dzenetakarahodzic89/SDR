package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a song response")
public class ChordProgressionSearchResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String outlineText;
    private String imageUrl;

    public ChordProgressionSearchResponse(Long id, String name, String outlineText) {
        super();
        this.id = id;
        this.name = name;
        this.outlineText = outlineText;

    }

}
