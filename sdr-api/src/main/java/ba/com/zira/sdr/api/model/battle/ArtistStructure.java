package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ArtistStructure implements Serializable {

    private static final long serialVersionUID = 1L;
    Long artistId;
    String name;
    Long countryId;
    String countryName;
    private List<SongStructure> songs;

}
