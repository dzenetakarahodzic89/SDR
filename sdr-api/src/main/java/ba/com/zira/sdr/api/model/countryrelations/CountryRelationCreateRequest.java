package ba.com.zira.sdr.api.model.countryrelations;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Properties for creation of country relation")
public class CountryRelationCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Created at")
    private LocalDateTime created;

    @Schema(description = "Created by")
    private String createdBy;

    @Schema(description = "Modified at")
    private LocalDateTime modified;

    @Schema(description = "Modified by")
    private String modifiedBy;

    @Schema(description = "Status")
    private String status;

    @Schema(description = "The text field that combines foreign country name and a type of link")
    private CountryRelation countryRelation;

    @Schema(description = "Home country id")
    private Long countryId;
}