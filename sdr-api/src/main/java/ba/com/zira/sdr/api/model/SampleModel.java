package ba.com.zira.sdr.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an Sample response")
public class SampleModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the sample")
    private Long id;
    @Schema(description = "Name of the sample document")
    private String documentName;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the sample")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the sample")
    private String modifiedBy;
    
}