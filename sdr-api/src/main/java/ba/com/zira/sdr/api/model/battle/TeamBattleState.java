package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TeamBattleState implements Serializable {
    private static final long serialVersionUID = 1L;

    Long id;
    Long countryId;
    String countryName;
    List<Long> availableArtistsByTurn;

    public TeamBattleState(Long id, Long countryId, String countryName) {
        this.id = id;
        this.countryId = countryId;
        this.countryName = countryName;
        this.availableArtistsByTurn = new ArrayList<>();

    }

}
