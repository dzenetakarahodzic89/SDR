package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TurnStateResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    long turnNumber;
    List<TurnCountryStateResponse> countriesStates;

    public TurnStateResponse(Long turnNumber) {
        this.turnNumber = turnNumber;
        this.countriesStates = new ArrayList<>();
    }
}
