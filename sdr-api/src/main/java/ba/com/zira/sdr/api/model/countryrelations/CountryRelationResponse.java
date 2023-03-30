package ba.com.zira.sdr.api.model.countryrelations;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Properties of country relation response")
public class CountryRelationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of country record")
    private Long id;

    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created country relation")
    private String createdBy;
    @Schema(description = "Modified date")
    private LocalDateTime modified;
    @Schema(description = "User that modified country relation")
    private String modifiedBy;
    @Schema(description = "Status")
    private String status;
    @Schema(description = "Home country id")
    private Long country;
    @Schema(description = "The text field that combines foreign country name and a type of link")
    private String countryRelation;

    public CountryRelationResponse(Long id, LocalDateTime created, String createdBy, LocalDateTime modified, String modifiedBy,
            Long country, String countryRelation) {
        super();
        this.id = id;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.country = country;
        this.countryRelation = countryRelation;
    }

}
