package ba.com.zira.sdr.api.battle;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BattleResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier")
    protected Long id;
    @Schema(description = "Name of the battle")
    protected String name;

    @Schema(description = "Last Turn")
    private Long lastTurn;

    @Schema(description = "Country name")
    private String countryName;

}
