package ba.com.zira.sdr.api.model.release;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Properties of release")
public class ReleaseSearchResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Id of the release")
    private Long id;

    @Schema(description = "Id of the release country")
    private Long releaseCountryId;

    @Schema(description = "Name of the release")
    private String name;

    @Schema(description = "Id of the artist credit")
    private Long artistCredit;

    @Schema(description = "Id of the release group")
    private Long releaseGroup;

    @Schema(description = "Name of the country")
    private String country;

    @Schema(description = "Status of the release")
    private Long status;

    @Schema(description = "Packaging of the release")
    private Long packaging;

    @Schema(description = "Language of the release")
    private Long language;

    @Schema(description = "Comment")
    private String comment;

    @Schema(description = "Last edited")
    private LocalDateTime lastUpdated;

    public ReleaseSearchResponse(final Long releaseCountryId, final String name, final String country) {
        this.releaseCountryId = releaseCountryId;
        this.name = name;
        this.country = country;
    }

}
