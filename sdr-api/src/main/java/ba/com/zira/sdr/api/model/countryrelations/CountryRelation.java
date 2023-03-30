package ba.com.zira.sdr.api.model.countryrelations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryRelation {

    private Long foreignCountryId;
    private String foreignCountryName;
    private String typeOfLink;
}
