package ba.com.zira.sdr.api.model.songartist;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of song-artist response")
public class SongArtistResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of song-artist record")
    private Long id;
    @Schema(description = "Id of a song that this artist wrote")
    private Long song;
    @Schema(description = "Id of a label that this record has")
    private Long label;
    @Schema(description = "Id of an artist that wrote this song")
    private Long artist;
    @Schema(description = "Id of an album that this song belongs to")
    private Long album;

    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the sample")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the sample")
    private String modifiedBy;

}