package ba.com.zira.sdr.api.model.countryrelations;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CountryRelationSingleResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CountryRelation> relations;
}
