package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BattleLogBattleResult implements Serializable {

    private static final long serialVersionUID = 1L;
    Long turnNumber;
    Long winnerTeamId;
    Long loserTeamId;
    private List<Long> winnerEligibleCountryIds;
    private List<Long> loserEligibleCountryIds;

}
