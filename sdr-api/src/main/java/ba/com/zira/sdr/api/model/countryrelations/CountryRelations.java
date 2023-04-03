package ba.com.zira.sdr.api.model.countryrelations;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryRelations implements Serializable {
    List<CountryRelation> countryRelations;

}
