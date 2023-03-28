package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class MapState implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<CountryState> countries;
    Long numberOfActivePlayerTeams;
    Long numberOfActiveNpcTeams;
    Long numberOfActiveCountries;
    Long numberOfInactiveCountries;
    Long numberOfPassiveCountries;

    public MapState(List<CountryState> countries, Long numberOfActivePlayerTeams, Long numberOfActiveNpcTeams, Long numberOfActiveCountries,
            Long numberOfInactiveCountries, Long numberOfPassiveCountries) {
        this.countries = countries;
        this.numberOfActivePlayerTeams = numberOfActivePlayerTeams;
        this.numberOfActiveNpcTeams = numberOfActiveNpcTeams;
        this.numberOfActiveCountries = numberOfActiveCountries;
        this.numberOfInactiveCountries = numberOfInactiveCountries;
        this.numberOfPassiveCountries = numberOfPassiveCountries;
    }

}
