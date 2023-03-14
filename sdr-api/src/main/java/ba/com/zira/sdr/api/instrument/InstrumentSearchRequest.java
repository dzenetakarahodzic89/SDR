package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Search filters for instrument")
public class InstrumentSearchRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	@Schema(description = "Sorting method", allowableValues = { "NoOfPersons", "Alphabetical", "LastEdit" })
	public String sortBy;

}