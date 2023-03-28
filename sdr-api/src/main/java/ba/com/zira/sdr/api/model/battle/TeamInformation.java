package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TeamInformation implements Serializable {

    private TeamStructure teamStructure;
    private List<SongWrapper> allArtistSongs;

}
