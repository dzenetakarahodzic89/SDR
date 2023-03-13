package ba.com.zira.sdr.api.model.notesheet;

import java.io.Serializable;

import lombok.Data;

@Data
public class SongInstrumentSheetResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long songId;
    private Long InstrumentId;

}
