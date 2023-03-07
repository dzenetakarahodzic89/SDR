package ba.com.zira.sdr.api.model.song;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a song person response")
public class SongPersonResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "songID")
    private Long id;

    @Schema(description = "created")
    private LocalDateTime created;

    @Schema(description = "created_by")
    private String createdBy;

    @Schema(description = "date_of_release")
    private LocalDateTime dateOfRelease;

    @Schema(description = "information")
    private String information;

    @Schema(description = "modified")
    private LocalDateTime modified;

    @Schema(description = "modified_by")
    private String modifiedBy;

    @Schema(description = "name")
    private String name;

    @Schema(description = "playtime")
    private String playtime;

    @Schema(description = "status")
    private String status;

    @Schema(description = "outline_text")
    private String outlineText;

    @Schema(description = "spotify_id")
    private Long spotifyId;

    public SongPersonResponse(Long id, LocalDateTime created, String createdBy, LocalDateTime dateOfRelease, String information,
            LocalDateTime modified, String modifiedBy, String name, String playtime, String outlineText, Long spotifyId) {
        super();
        this.id = id;
        this.created = created;
        this.createdBy = createdBy;
        this.dateOfRelease = dateOfRelease;
        this.information = information;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.name = name;
        this.playtime = playtime;
        this.outlineText = outlineText;
        this.spotifyId = spotifyId;
    }
}