package ba.com.zira.sdr.api.model.playlist;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "Response of a playlist of a user")
@NoArgsConstructor
public class PlaylistOfUserResponse implements Serializable  {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Playlist id")
    private Long playlistId;

    @Schema(description = "Song id")
    private Long songId;

    @Schema(description = "Song name")
    private String songName;

    @Schema(description = "Playtime in seconds")
    private Long playtimeInSeconds;

    @Schema(description = "Audio url of song")
    private String audioUrl;

    @Schema(description = "Spotify id of song")
    private String spotifyId;


    public PlaylistOfUserResponse(Long playlistId,Long songId, String songName, Long playtimeInSeconds,String spotifyId)
    {
        this.playlistId=playlistId;
        this.songId=songId;
        this.songName=songName;
        this.playtimeInSeconds=playtimeInSeconds;
        this.spotifyId=spotifyId;
    }




}
