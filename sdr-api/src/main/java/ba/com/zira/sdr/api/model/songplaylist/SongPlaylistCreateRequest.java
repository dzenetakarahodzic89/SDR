package ba.com.zira.sdr.api.model.songplaylist;

import java.io.Serializable;

import lombok.Data;

@Data

public class SongPlaylistCreateRequest implements Serializable {
    private Long playlistId;
    private Long songId;

}
