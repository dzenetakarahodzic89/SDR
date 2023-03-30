package ba.com.zira.sdr.api.model.countryrelations;

import java.io.Serializable;

import lombok.Data;

@Data
public class CountryRelationPre implements Serializable {
    private static final long serialVersionUID = 1L;
    String typeOfLink;
    String countryName;
    Long countryId;
}
