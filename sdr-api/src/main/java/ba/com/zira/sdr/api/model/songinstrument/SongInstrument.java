package ba.com.zira.sdr.api.model.songinstrument;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a songinstrument response")
public class SongInstrument implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Song instrument id")
    private Long id;

    @Schema(description = "Creation date")
    private LocalDateTime created;

    @Schema(description = "created_by")
    private String createdBy;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the songinstrument")
    private String modifiedBy;

    @Schema(description = "Instrument id")
    private String instrumentId;

    @Schema(description = "Name of instrument")
    private String instrumentName;

    @Schema(description = "Id of the person")
    private Long personId;

    @Schema(description = "Person name")
    private String personName;

    @Schema(description = "Person's date of birth")
    private String personDob;

    @Schema(description = "Id of the song")
    private Long songId;

    @Schema(description = "Song name")
    private String songName;

    @Schema(description = "Outline text")
    private String outlineText;
}
