package ba.com.zira.sdr.api.model.generateplaylist;

import lombok.Data;

@Data
public class GeneratedPlaylistSongDbResponse {
    private String song;
    private String genre;
    private String artists;
    private String albums;
    private String countries;

    public GeneratedPlaylistSongDbResponse(String song, String artists, String albums, String genre, String countries) {
        this.song = song;
        this.genre = genre;
        this.artists = artists;
        this.albums = albums;
        this.countries = countries;
    }
}
