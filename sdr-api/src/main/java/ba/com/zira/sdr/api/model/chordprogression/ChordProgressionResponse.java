package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChordProgressionResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String status;
    private String information;
    private List<SongResponse> songs;

    public ChordProgressionResponse(final Long id, String name, String status, String information) {
        super();
        this.id = id;
        this.name = name;
        this.status = status;
        this.information = information;
    }
}
