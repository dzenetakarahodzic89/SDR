package ba.com.zira.sdr.api.artist;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistSheetResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "Unique identifier")
    private Long id;
    @Schema(description = "Country id")
    private Long countryId;
    @Schema(description = "Country id")
    private String flagAbbriviation;
    @Schema(description = "Full stage name")
    private String fullName;
    @Schema(description = "Unique identifier")
    private Long personId;
    @Schema(description = "Full real name")
    private String personName;

}
