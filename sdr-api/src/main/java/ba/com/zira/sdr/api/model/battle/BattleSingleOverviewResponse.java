package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleSingleOverviewResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<Long, TeamBattleState> teamBattleStates;
    List<BattleArtistStateResponse> artistState;
    int numberOfSongsWon;
    int numberOfSongsLost;
    float winPercentage;

}
