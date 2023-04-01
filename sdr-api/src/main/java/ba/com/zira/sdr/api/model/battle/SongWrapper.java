package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long artistId;
    private SongStructure song;

    public SongWrapper(Long artistId, Long songId, String name, String spotifyId, String audioUrl, String playtime) {
        this.artistId = artistId;
        this.song = new SongStructure(songId, name, spotifyId, audioUrl, playtime);

    }

}
