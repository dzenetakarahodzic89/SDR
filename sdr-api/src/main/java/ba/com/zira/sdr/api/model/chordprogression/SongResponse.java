package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;

import lombok.Data;

@Data
public class SongResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String playtime;
}
