package ba.com.zira.sdr.api.model.songinstrument;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a song instrument person response")
public class SongInstrumentPersonResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the song instrument")
    private Long id;
    @Schema(description = "Name of song instrument")
    private String name;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the song instrument")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the song")
    private String modifiedBy;
    @Schema(description = "Id of the song")
    private Long songId;
    @Schema(description = "Name of the song")
    private String song;
    @Schema(description = "Id of the instrument")
    private Long instrumentId;
    @Schema(description = "Name of the instrument")
    private String instrument;
    @Schema(description = "Outline text")
    private String outlineText;

    public SongInstrumentPersonResponse(final Long id, final Long songId, final String song, final Long instrumentId,
            final String instrument) {
        super();
        this.id = id;
        this.songId = songId;
        this.song = song;
        this.instrumentId = instrumentId;
        this.instrument = instrument;
    }

}