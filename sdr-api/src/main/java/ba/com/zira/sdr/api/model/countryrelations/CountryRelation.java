package ba.com.zira.sdr.api.model.countryrelations;

import java.io.Serializable;

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
public class CountryRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long foreignCountryId;
    private String foreignCountryName;
    private String typeOfLink;
}
