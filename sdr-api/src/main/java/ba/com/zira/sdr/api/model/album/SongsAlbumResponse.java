package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;

import lombok.Data;

@Data
public class SongsAlbumResponse implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long songCount;
    private Long playTimeSum;
    public SongsAlbumResponse(Long id, String name, Long songCount, Long playTimeCount) {
        super();
        this.id = id;
        this.name = name;
        this.songCount = songCount;
        this.playTimeSum = playTimeCount;
    }


}
