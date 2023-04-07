package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinnerCountryNamesResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    Long id;
    Long countryId;
    String countryName;
    String battleName;
    List<ArtistSongInformation> artistsSongs;
    List<CountryResults> countryResults;

}
