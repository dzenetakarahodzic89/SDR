package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
@Data
public class AlbumArtistSongResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long songCount;
    private Long playTimeSum;
    private LocalDateTime dateOfRelease;
    private List<AlbumArtistSongResponse> album;


    public AlbumArtistSongResponse(Long id, String name, LocalDateTime dateOfRelease, Long songCount, Long playTimeSum) {
        this.id = id;
        this.name = name;
        this.dateOfRelease = dateOfRelease;
        this.songCount = songCount;
        this.playTimeSum = playTimeSum;
    }


}
