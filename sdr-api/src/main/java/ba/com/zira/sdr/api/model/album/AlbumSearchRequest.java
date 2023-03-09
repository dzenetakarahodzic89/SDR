package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AlbumSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    public AlbumSearchRequest(List<Long> eras, List<Long> genres, List<Long> artists, String name)

    {

        this.eras = eras;
        this.genres = genres;
        this.artists = artists;
        this.name = name;

    }

    List<Long> eras;
    List<Long> genres;
    List<Long> artists;
    String name;

}
