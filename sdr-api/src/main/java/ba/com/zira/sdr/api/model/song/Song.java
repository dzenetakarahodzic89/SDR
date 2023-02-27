package ba.com.zira.sdr.api.model.song;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Properties of a Song response")
public class Song implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the song")
    private Long id;

    @Schema(description = "Creation date")
    private LocalDateTime created;

    @Schema(description = "User that created the song")
    private String createdBy;

    @Schema(description = "Release date of the song")
    private LocalDateTime dateOfRelease;

    @Schema(description = "Information about the song")
    private String information;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the song")
    private String modifiedBy;

    @Schema(description = "Name of the song")
    private String name;

    @Schema(description = "Playtime of the song")
    private String playtime;
    
    @Schema(description = "Outline text")
    private String outlineText;

    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "Cover of the song")
    private Song cover;

    @Schema(description = "Remix of the song")
    private Song remix;

    @Schema(description = ("Id of chord progression of the song"))
    private Long chordProgressionId;

    @Schema(description = "Id of song genre")
    private Long genreId;

}
