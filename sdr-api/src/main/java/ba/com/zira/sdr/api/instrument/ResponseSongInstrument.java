package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseSongInstrument implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name")
    private Long id;

    @Schema(description = "Creating date")
    private LocalDateTime created;

    @Schema(description = "crated by")
    private String created_by;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the songinstrument")
    private String modifiedBy;

    @Schema(description = "Name of instrument")
    private String name;

    @Schema(description = "Name song id")
    private Long songId;

    @Schema(description = "Name instrument id")
    private Long instrumentId;

    @Schema(description = "Name person id")
    private Long personId;

}