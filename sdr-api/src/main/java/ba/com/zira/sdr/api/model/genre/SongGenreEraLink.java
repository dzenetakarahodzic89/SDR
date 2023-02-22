package ba.com.zira.sdr.api.model.genre;

import java.io.Serializable;

import lombok.Data;

@Data
public class SongGenreEraLink implements Serializable {

    private static final long serialVersionUID = 1L;
    Long songId;
    String songName;
    Long genreId;
    String genreName;
    // Long mainGenreId;
    // String mainGenreName;
    Long eraId;
    String eraName;

    public SongGenreEraLink(Long songId, String songName, Long genreId, String genreName, Long eraId, String eraName) {
        super();
        this.songId = songId;
        this.songName = songName;
        this.genreId = genreId;
        this.genreName = genreName;
        // this.mainGenreId = mainGenreId;
        // this.mainGenreName = mainGenreName;
        this.eraId = eraId;
        this.eraName = eraName;
    }

}
