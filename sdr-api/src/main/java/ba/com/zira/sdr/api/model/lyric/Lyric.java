package ba.com.zira.sdr.api.model.lyric;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an Lyric response")
public class Lyric implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the lyric")
    private Long id;

    @Schema(description = "Creation date")
    private LocalDateTime created;

    @Schema(description = "User that created the lyric")
    private String createdBy;

    @Schema(description = "Language used in the lyric")
    private String language;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the lyric")
    private String modifiedBy;

    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "The text of the song")
    private String text;

}
