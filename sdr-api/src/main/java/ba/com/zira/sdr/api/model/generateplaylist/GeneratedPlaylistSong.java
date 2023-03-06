package ba.com.zira.sdr.api.model.generateplaylist;

import java.io.Serializable;
import java.util.List;

import ba.com.zira.sdr.api.artist.ArtistResponse;
import ba.com.zira.sdr.api.model.album.AlbumResponse;
import ba.com.zira.sdr.api.model.country.CountryResponse;
import ba.com.zira.sdr.api.model.genre.Genre;
import ba.com.zira.sdr.api.model.song.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a generated playlist response")
public class GeneratedPlaylistSong implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Song that is going back to frontend")
    private Song song;

    @Schema(description = "Genre of this song")
    private Genre genre;

    @Schema(description = "Artists that do this song")
    private List<ArtistResponse> artists;

    @Schema(description = "Albums where this song can be found")
    private List<AlbumResponse> albums;

    @Schema(description = "Countries of persons participating in song's artists")
    private List<CountryResponse> countries;

    public GeneratedPlaylistSong(Song song, Genre genre, List<ArtistResponse> artistResponses, List<AlbumResponse> albumResponses,
            List<CountryResponse> countryResponses) {
        this.song = song;
        this.genre = genre;
        this.artists = artistResponses;
        this.albums = albumResponses;
        this.countries = countryResponses;
    }
}
