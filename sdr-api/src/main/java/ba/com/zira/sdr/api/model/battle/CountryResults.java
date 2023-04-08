package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryResults implements Serializable {
    private static final long serialVersionUID = 1L;

    String countryWon;
    Long countryLoserId;
    String countryLoserName;
    String winnerLoserName;
    String type;
    Long turn;

}
