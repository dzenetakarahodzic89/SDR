package ba.com.zira.sdr.api.model.battle;

import java.io.Serializable;

import lombok.Data;

@Data
public class CountryState implements Serializable {

    private static final long serialVersionUID = 1L;
    Long countryId;
    String countryName;
    Long teamOwnershipId;
    Double mapValue;
    String countryStatus;
}
