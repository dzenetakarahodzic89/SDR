package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TurnState implements Serializable {

    private static final long serialVersionUID = 1L;
    TeamsState teams;
    String status;
    List<BattleLog> battleLogs;
}
