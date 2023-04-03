package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;

import ba.com.zira.commons.configuration.N2bObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BattleSingleResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Long turn;
    @JsonIgnore
    private String mapStateJson;
    @JsonIgnore
    private String teamStateJson;
    @JsonIgnore
    private String turnCombatStateJson;

    private TurnCombatState turnCombatState;

    private MapState mapState;

    private TeamsState teamState;
    private Long battleTurnId;

    public BattleSingleResponse(Long id, String name, Long turn, String mapStateJson, String teamStateJson, String turnCombatJSON,
            Long battleturnId) throws JsonProcessingException {
        this.id = id;
        this.name = name;
        this.turn = turn;
        this.mapStateJson = mapStateJson;
        this.teamStateJson = teamStateJson;
        this.turnCombatStateJson = turnCombatJSON;
        this.battleTurnId = battleturnId;

        var mapper = new N2bObjectMapper();

        this.turnCombatState = mapper.readValue(turnCombatJSON, TurnCombatState.class);
        this.mapState = mapper.readValue(mapStateJson, MapState.class);
        this.teamState = mapper.readValue(teamStateJson, TeamsState.class);
    }
}
