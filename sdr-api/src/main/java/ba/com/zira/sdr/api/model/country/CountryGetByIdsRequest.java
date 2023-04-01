package ba.com.zira.sdr.api.model.country;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CountryGetByIdsRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Long> countryIds;
}
