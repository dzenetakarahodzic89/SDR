package ba.com.zira.sdr.api.model.songplaylist;

import java.io.Serializable;

import lombok.Data;

@Data

public class SongPlaylistCreateRequest implements Serializable {
    private Long playlistId;
    private Long songId;

    public SongPlaylistCreateRequest(Long playlistId, Long songId) {

        this.playlistId = playlistId;
        this.songId = songId;
    }

    public SongPlaylistCreateRequest() {
    }
}
