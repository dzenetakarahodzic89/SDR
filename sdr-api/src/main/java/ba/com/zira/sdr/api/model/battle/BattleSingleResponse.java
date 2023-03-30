package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private MapState mapState;

    private TeamsState teamState;

    public BattleSingleResponse(Long id, String name, Long turn, String mapStateJson, String teamStateJson, String turnCombatJSON) {
        this.id = id;
        this.name = name;
        this.turn = turn;
        this.mapStateJson = mapStateJson;
        this.teamStateJson = teamStateJson;
        this.turnCombatStateJson = turnCombatJSON;
    }
}
