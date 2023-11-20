package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class BattleTurnUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    Long battleTurnId;
    String attackerName;
    String attackedName;
    Long attackerCountryId;
    Long attackedCountryId;
    private List<String> songBattleExplained;
    private List<BattleLogEntry> songBattles;
    String wonCase;
}
