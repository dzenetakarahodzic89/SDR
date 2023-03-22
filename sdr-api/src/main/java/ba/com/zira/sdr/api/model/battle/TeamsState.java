package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TeamsState implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<TeamStructure> activePlayerTeams;
    private List<TeamStructure> activeNpcTeams;
    private List<TeamStructure> inactivePlayerTeams;
    private List<TeamStructure> inactiveNpcTeams;
}
