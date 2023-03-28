package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Battle generation properties")
public class BattleGenerateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of the battle")
    private String name;

    @NotNull
    @Schema(description = "Number of songs for battle")
    private Long songSize;

    @NotNull
    @Schema(description = "Size of team")
    private Long teamSize;

    @NotNull
    @Schema(description = "Player countries")
    private List<Long> countries;
}
