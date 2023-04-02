package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CountryState implements Serializable {

    private static final long serialVersionUID = 1L;
    Long countryId;
    String countryName;
    Long teamOwnershipId;
    Double mapValue;
    String countryStatus;

    public CountryState(Long countryId, String countryName, Long teamOwnershipId, Double mapValue, String countryStatus) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.teamOwnershipId = teamOwnershipId;
        this.mapValue = mapValue;
        this.countryStatus = countryStatus;
    }

}
