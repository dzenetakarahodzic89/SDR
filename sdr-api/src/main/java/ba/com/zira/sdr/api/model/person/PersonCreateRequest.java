package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for  create request")
public class PersonCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "User name")
    private String name;

    @Schema(description = "User surname")
    private String surname;

    @Schema(description = "Information about user")
    private String information;

    @Schema(description = "User's gender")
    private String gender;

    @Schema(description = "Date of birth")
    private LocalDateTime dateOfBirth;

    @Schema(description = "Date of death")
    private LocalDateTime dateOfDeath;

    @Schema(description = "Outline text")
    private String outlineText;

    @Schema(description = "Country id")
    private Long countryId;

    @Schema(description = "Cover image data")
    private String coverImageData;

    @Schema(description = "Cover Image")
    private String coverImage;

}
