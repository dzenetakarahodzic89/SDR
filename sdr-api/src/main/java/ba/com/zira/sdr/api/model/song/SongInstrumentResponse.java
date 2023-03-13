package ba.com.zira.sdr.api.model.song;

import java.io.Serializable;
import java.util.List;

import ba.com.zira.sdr.api.instrument.InstrumentSongResponse;
import lombok.Data;

@Data
public class SongInstrumentResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private List<InstrumentSongResponse> instruments;
}
