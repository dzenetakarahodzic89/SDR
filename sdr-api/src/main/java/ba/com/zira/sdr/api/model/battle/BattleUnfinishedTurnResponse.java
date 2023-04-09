package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Battle generation properties")
public class BattleUnfinishedTurnResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    Long battleTurnId;
    Long countryAId;
    Long countryBId;
    String countryAName;
    String countryBName;
    private List<ArtistStructure> countryATeamArtists;
    private List<ArtistStructure> countryBTeamArtists;
}
