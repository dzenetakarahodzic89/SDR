package ba.com.zira.sdr.api.model.person;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Properties of an Sample response")
public class PersonResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the sample")
    private Long id;

    @Schema(description = "Creation date")
    private LocalDateTime created;

    @Schema(description = "User that created the sample")
    private String createdBy;

    @Schema(description = "Date of birth")
    private LocalDateTime dateOfBirth;

    @Schema(description = "Date of death")
    private LocalDateTime dateOfDeath;

    @Schema(description = "User's gender")
    private String gender;

    @Schema(description = "Information about user")
    private String information;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the sample")
    private String modifiedBy;

    @Schema(description = "User name")
    private String name;

    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "User surname")
    private String surname;

    private String imageUrl;

    private String fullName;

    private String outlineText;

    private Long countryId;

    private String flagAbbreviation;

}
