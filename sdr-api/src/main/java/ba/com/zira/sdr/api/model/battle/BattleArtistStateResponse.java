package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class BattleArtistStateResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    Long id;
    String name;
    List<SongStructure> songs;
    int numberOfSongsWon = 0;
    int numberOfSongsLost = 0;
    Long countryId;

    public BattleArtistStateResponse(Long id, String name, List<SongStructure> songs, Long countryId) {
        this.id = id;
        this.name = name;
        this.songs = songs;
        this.countryId = countryId;
        this.numberOfSongsLost = 0;
        this.numberOfSongsWon = 0;
    }

}
