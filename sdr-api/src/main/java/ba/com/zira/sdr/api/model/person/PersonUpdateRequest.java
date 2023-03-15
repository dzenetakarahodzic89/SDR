package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update of  request")
public class PersonUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID of record for update")
    private Long id;

    @Schema(description = "Information about user")
    private String information;

    @Schema(description = "User name")
    private String name;

    @Schema(description = "User surname")
    private String surname;

    @Schema(description = "User's gender")
    private String gender;

    @Schema(description = "Date of birth")
    private LocalDateTime dateOfBirth;

    @Schema(description = "Date of death")
    private LocalDateTime dateOfDeath;
    @Schema(description = "outline text")
    private String outlineText;
    @Schema(description = "country id")
    private Long countryId;
    @Schema(description = "cover image data")
    private String coverImageData;
    @Schema(description = "cover image")
    private String coverImage;

}
