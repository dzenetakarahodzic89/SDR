package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class BattleLog implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<Long, String> textHistory;
    private List<BattleLogEntry> turnHistory;
}