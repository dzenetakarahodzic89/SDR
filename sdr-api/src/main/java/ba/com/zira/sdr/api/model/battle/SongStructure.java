package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongStructure implements Serializable {

    private static final long serialVersionUID = 1L;
    Long songId;
    String name;
    String spotifyId;
    String audioUrl;
    String playtime;

}
