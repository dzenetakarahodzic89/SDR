package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Battle generation properties")
public class BattleGenerateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the battle")
    private String name;

    @NotBlank
    @Schema(description = "Number of songs for battle")
    private Long songsNumber;

    @NotBlank
    @Schema(description = "Size of team")
    private Long teamSize;

    @NotBlank
    @Schema(description = "Player countries")
    private List<Long> countries;
}
