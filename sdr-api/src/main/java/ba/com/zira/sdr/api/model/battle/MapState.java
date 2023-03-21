package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MapState implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<CountryState> countries;
    Long numberOfActivePlayerTeams;
    Long numberOfActiveNpcTeams;
    Long numberOfActiveCountries;
    Long numberOfInactiveCountries;
    Long numberOfPassiveCountries;
}
