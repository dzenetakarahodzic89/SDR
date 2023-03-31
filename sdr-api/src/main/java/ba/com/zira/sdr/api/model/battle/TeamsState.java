package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamsState implements Serializable {

    private static final long serialVersionUID = 1L;
    private TeamStructure activePlayerTeam;
    private List<TeamStructure> activeNpcTeams;
    private List<TeamStructure> inactiveNpcTeams;

    public TeamsState(TeamStructure activePlayerTeam, List<TeamStructure> activeNpcTeams, List<TeamStructure> inactiveNpcTeams) {
        this.activePlayerTeam = activePlayerTeam;
        this.activeNpcTeams = activeNpcTeams;
        this.inactiveNpcTeams = inactiveNpcTeams;
    }

}
