package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.Data;

@Data
public class Battle implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long lastTurn;

    private Long teamSize;

    private Long songSize;

    private Long winnerCountryId;

    private String winnerCountryName;

}
