package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleLogEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    Long playerASongId;
    Long playerBSongId;
    String playerASongName;
    String playerBSongName;
    String songASpotifyId;
    String songBSpotifyId;
    String songAAudioUrl;
    String songBAudioUrl;
    Long winnerSongId;
    Long loserSongId;
    String userCodeOfDecider;
    Long battleResultId;
}
