package ba.com.zira.sdr.api.model.playlist;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a playlist response")
public class PlaylistResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name of the song")
    private Long songId;

    @Schema(description = "Name of the song")
    private String songName;

    @Schema(description = "Name of the album")
    private String albumName;

    @Schema(description = "Name of the artist")
    private String artistName;

    @Schema(description = "Name of the playlist")
    private String playlistName;

    @Schema(description = "Number of plays")
    private Long numberOfPlays;

    @Schema(description = "Number of shares")
    private Long numberOfShares;

    @Schema(description = "Playtime of the song")
    private String playtime;
    private String outlineText;
    private String spotifyId;
    private String genre;
    private String country;

    private String songImageUrl;
    private String songAudioUrl;

    public PlaylistResponse(Long songId, String songName, String albumName, String artistName, String playtime, String spotifyId,
            String playlistName, Long numberOfPlays, Long numberOfShares, String outlineText) {
        this.songId = songId;
        this.songName = songName;
        this.albumName = albumName;
        this.artistName = artistName;
        this.playtime = playtime;
        this.spotifyId = spotifyId;
        this.playlistName = playlistName;
        this.numberOfPlays = numberOfPlays;
        this.numberOfShares = numberOfShares;
        this.outlineText = outlineText;
    }

    public PlaylistResponse(String playlistName, Long songId, String songName, String spotifyId, String genre, String country,
            String artistName, String playtime) {
        this.genre = genre;
        this.country = country;
        this.artistName = artistName;
        this.songId = songId;
        this.songName = songName;
        this.playtime = playtime;
        this.spotifyId = spotifyId;
        this.playlistName = playlistName;
    }

}
