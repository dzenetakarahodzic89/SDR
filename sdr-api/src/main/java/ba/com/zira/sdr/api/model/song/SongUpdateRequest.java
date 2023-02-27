package ba.com.zira.sdr.api.model.song;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@Schema(description = "Properties for update of a song")
public class SongUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the song")
    private Long id;

    @NotBlank
    @Schema(description = "Name of the song")
    private String name;

    @Schema(description = "Release date of the song")
    private LocalDateTime dateOfRelease;

    @Schema(description = "Information about the song")
    private String information;
    
    @Schema(description = "Outline text")
    private String outlineText;

    @Schema(description = "Playtime of the song")
    private String playtime;

    @Schema(description = "Ids of song lyrics")
    private List<Long> lyricIds;

    @Schema(description = "Ids of note sheets of the song")
    private List<Long> noteSheetIds;

    @Schema(description = "Id of chord progression of the song")
    private Long chordProgressionId;

    @NotNull
    @Schema(description = "Id of song genre")
    private Long genreId;

    @Schema(description = "Id of song remix")
    private Long remixId;

    @Schema(description = "Id of song cover")
    private Long coverId;

}
