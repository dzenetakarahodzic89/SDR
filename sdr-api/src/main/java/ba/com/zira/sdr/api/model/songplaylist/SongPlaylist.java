package ba.com.zira.sdr.api.model.songplaylist;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an  Song Playlist response")

public class SongPlaylist implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the  Song Playlist")
    private Long id;

    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the  Song Playlist")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the  Song Playlist")
    private String modifiedBy;
    @Schema(description = "Song ID")
    private Long songId;
    @Schema(description = "Playlist ID")
    private Long playlistId;

}