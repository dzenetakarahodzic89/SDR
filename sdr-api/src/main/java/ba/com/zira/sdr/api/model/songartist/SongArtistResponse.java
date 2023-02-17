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
    private Long songId;
    @Schema(description = "Id of a label that this record has")
    private Long labelId;
    @Schema(description = "Id of an artist that wrote this song")
    private Long artistId;
    @Schema(description = "Id of an album that this song belongs to")
    private Long albumId;

    @Schema(description = "Name of a song that this artist wrote")
    private String songName;
    @Schema(description = "Name of a label that this record has")
    private String labelName;
    @Schema(description = "Name of an artist that wrote this song")
    private String artistName;
    @Schema(description = "Name of an album that this song belongs to")
    private String albumName;

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