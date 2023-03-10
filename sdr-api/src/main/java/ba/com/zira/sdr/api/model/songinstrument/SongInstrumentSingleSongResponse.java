package ba.com.zira.sdr.api.model.songinstrument;

import java.io.Serializable;

import lombok.Data;

@Data
public class SongInstrumentSingleSongResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    Long songInstrumentId;
    Long songId;
    Long instrumentId;

    public SongInstrumentSingleSongResponse(Long songInstrumentId, Long songId, Long instrumentId) {
        this.songInstrumentId = songInstrumentId;
        this.songId = songId;
        this.instrumentId = instrumentId;

    }

}
