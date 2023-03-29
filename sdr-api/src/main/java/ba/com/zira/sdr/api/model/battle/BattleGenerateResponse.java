package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "Battle generation properties")
public class BattleGenerateResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private LocalDateTime created;
    private String createdBy;
    private String status;
    private Long lastTurn;
    private Long songSize;
    private Long teamSize;

}
