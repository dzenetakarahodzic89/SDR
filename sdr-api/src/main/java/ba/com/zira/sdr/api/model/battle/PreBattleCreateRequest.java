package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.Data;

@Data
public class PreBattleCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long attackerId;
    private Long attackedId;
    private String attackerName;
    private String attackedName;
    private Boolean isAttackedPassive;
    private Long battleId;
}
