package ba.com.zira.sdr.api.model.countryrelations;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryRelation implements Serializable {
    private String typeOfLink;
    private String countryName;
    private Long countryId;

}
