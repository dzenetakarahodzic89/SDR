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
}
