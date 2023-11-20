package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistStructure implements Serializable {

    private static final long serialVersionUID = 1L;
    Long artistId;
    String name;
    Long countryId;
    String countryName;
    private List<SongStructure> songs;
    Integer songNumber;
    Integer albumNumber;

    public ArtistStructure(Long artistId, String name, Long countryId, String countryName, Long songNumber, Long albumNumber) {
        this.artistId = artistId;
        this.name = name;
        this.countryId = countryId;
        this.countryName = countryName;
        this.songNumber = songNumber.intValue();
        this.albumNumber = albumNumber.intValue();
        this.songs = new ArrayList<>();
    }

}
