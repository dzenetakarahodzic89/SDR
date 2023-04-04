package ba.com.zira.sdr.api.model.chordprogression;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChordProgressionSongResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String songName;
    private String genreName;
    private String playtime;
    private String flagAbbriviation;

    public ChordProgressionSongResponse(String songName, String playtime, String genreName, String flagAbbriviation) {
        super();
        this.songName = songName;
        this.playtime = playtime;
        this.genreName = genreName;
        this.flagAbbriviation = flagAbbriviation;
    }

}
