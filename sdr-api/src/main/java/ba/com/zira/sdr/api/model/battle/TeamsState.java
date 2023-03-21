package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TeamsState implements Serializable {

    private static final long serialVersionUID = 1L;
    List<TeamStructure> activePlayerTeams;
    List<TeamStructure> activeNpcTeams;
    List<TeamStructure> inactivePlayerTeams;
    List<TeamStructure> inactiveNpcTeams;
}
