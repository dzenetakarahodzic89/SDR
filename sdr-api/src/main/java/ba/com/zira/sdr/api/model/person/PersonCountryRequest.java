package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for  create request")
public class PersonCountryRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long personId;
	private Long countryId;
}
