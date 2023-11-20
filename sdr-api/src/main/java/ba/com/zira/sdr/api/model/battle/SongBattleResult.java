package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SongBattleResult implements Serializable {
    private static final long serialVersionUID = 1L;

    String songAName;
    String songAStatus;
    String songBName;
    String songBStatus;

}
