package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TeamStructure implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ArtistStructure> teamArtists;
    Long id;
    Long countryId;
    String countryName;
    Long numberOfWins;
    Long numberOfLoses;
    Long lastActiveTurn;
    private List<Long> eligibleCountryIds;

}
