package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligibleArtistsInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<ArtistStructure> eligibleArtists;
    private List<SongWrapper> eligibleSongs;

}