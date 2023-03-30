package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TurnCombatState implements Serializable {

    private static final long serialVersionUID = 1L;
    String status;
    private List<BattleLog> battleLogs;

    public TurnCombatState(String status, List<BattleLog> battleLogs) {
        this.status = status;
        this.battleLogs = battleLogs;
    }
}
