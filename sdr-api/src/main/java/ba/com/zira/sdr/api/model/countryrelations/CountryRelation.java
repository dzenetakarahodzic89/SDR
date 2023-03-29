package ba.com.zira.sdr.api.model.countryrelations;

import java.io.Serializable;

import lombok.Data;

@Data
public class CountryRelation implements Serializable {

    private static final long serialVersionUID = 1L;
    private String typeOfLink;
    private String countryName;
    private Long countryId;
}
