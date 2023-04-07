package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistSongInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    Long artistId;
    String artistName;
    Long songId;
    String songName;
    String spotifyId;
    String audioUrl;
    String playtime;
    String imageUrl;
}
