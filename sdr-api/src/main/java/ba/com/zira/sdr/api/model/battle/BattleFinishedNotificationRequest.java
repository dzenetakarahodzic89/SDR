package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BattleFinishedNotificationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    String battleName;
    Long turnNumber;
    String userDecided;
    String countriesCombat;
    private List<SongBattleResult> results;
    String userSentTo;

}
