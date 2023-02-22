package ba.com.zira.sdr.api.model.songinstrument;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Properties of a songinstrument response")
public class SongInstrument implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name")
    private Long id;

    @Schema(description = "Creatin date")
    private LocalDateTime created;

    @Schema(description = "created_by")
    private String createdBy;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the songinstrument")
    private String modifiedBy;

    @Schema(description = "Name of instrument")
    private String name;

    @Schema(description = "Id of the object")
    private Long objectId;

    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "user code")
    private String userCode;

    @Schema(description = "Name song_id")
    private Long songId;

}
