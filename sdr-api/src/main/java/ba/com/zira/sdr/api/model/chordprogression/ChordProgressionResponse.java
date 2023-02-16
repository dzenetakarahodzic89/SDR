package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;

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
}
