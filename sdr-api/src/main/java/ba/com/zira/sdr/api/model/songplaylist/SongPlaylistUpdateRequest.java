package ba.com.zira.sdr.api.model.songplaylist;

import java.io.Serializable;

import lombok.Data;

@Data

public class SongPlaylistUpdateRequest implements Serializable {
    private Long id;
    private Long playlistId;
    private Long songId;

}