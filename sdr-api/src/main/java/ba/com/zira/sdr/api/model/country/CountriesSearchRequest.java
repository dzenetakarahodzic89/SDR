package ba.com.zira.sdr.api.model.country;

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
@Schema(description = "Properties of countries search")
public class CountriesSearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id of the country that will not be on the list")
    private Long id;
}
