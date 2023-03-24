package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "Battle generation properties")
public class BattleGenerateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name of the battle")
    private String name;

    @Schema(description = "Number of songs for battle")
    private Long songSize;

    @Schema(description = "Size of team")
    private Long teamSize;

    @Schema(description = "Player countries")
    private List<Long> countries;
}
