package ba.com.zira.sdr.api.model.era;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an era response")
public class EraResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the era")
    private Long id;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the era")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the era")
    private String modifiedBy;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Name of the era")
    private String name;
    @Schema(description = "Information of the era")
    private String information;
    @Schema(description = "Start date of the era")
    private LocalDateTime startDate;
    @Schema(description = "End date of the era")
    private LocalDateTime endDate;
    @Schema(description = "Scope of the era")
    private String scope;
    @Schema(description = "Era outline text")
    private String outlinetext;
    private String imageUrl;
}
