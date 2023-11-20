package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ArtistByEras implements Serializable {
    private List<Artist> artistGroup;
    private List<Artist> artistSolo;

    private Long soloCount;
    private Long groupCount;
    private String eraName;

    public ArtistByEras(String eraName, Long soloCount, Long groupCount) {
        this.eraName = eraName;
        this.soloCount = soloCount;
        this.groupCount = groupCount;

    }

    public ArtistByEras(List<Artist> artistGroup, List<Artist> artistSolo) {
        this.artistGroup = artistGroup;
        this.artistSolo = artistSolo;

    }
}
