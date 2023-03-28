package ba.com.zira.sdr.api.model.moritsintegration;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicMatchIntegrationStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "Table name")
    private String tableName;
    @Schema(description = "Unique identifier of the song")
    private Long id;
    @Schema(description = "Name of the song")
    private String name;
    @Schema(description = "Last modification date")
    private LocalDateTime lastEdit;
    @Schema(description = "Music match status")
    private String musicMatchStatus;
}
