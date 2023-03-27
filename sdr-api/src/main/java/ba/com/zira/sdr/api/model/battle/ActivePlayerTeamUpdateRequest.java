package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivePlayerTeamUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private TeamStructure teamStructure;
    private Long battleId;

}
