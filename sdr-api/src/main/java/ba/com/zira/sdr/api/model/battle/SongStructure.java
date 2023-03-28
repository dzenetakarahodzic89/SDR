package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.Data;

@Data
public class SongStructure implements Serializable {

    private static final long serialVersionUID = 1L;
    Long songId;
    String name;
    String spotifyId;
    String audioUrl;

    public SongStructure(Long songId, String name, String spotifyId, String audioUrl) {
        this.songId = songId;
        this.name = name;
        this.spotifyId = spotifyId;
        this.audioUrl = audioUrl;
    }

}
