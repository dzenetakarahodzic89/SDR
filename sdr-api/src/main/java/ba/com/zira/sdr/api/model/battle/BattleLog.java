package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BattleLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Long, String> textHistory;
    private List<BattleLogEntry> turnHistory;
    private List<BattleLogBattleResult> battleResults;
    @JsonIgnore
    private Long battleWinnerTeamId;
    @JsonIgnore
    private Long battleLoserTeamId;

    public BattleLog(Map<Long, String> textHistory, List<BattleLogEntry> turnHistory, List<BattleLogBattleResult> battleResults) {

        this.textHistory = textHistory;
        this.turnHistory = turnHistory;
        this.battleResults = battleResults;
    }
}
