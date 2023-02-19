package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for person create request")
public class PersonCreateRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Schema(description = "Unique identifier of the sample")
	private Long id;

	@Schema(description = "Information about user")
	private String personInformation;

	@Schema(description = "User name")
	private String personName;

	@Schema(description = "User surname")
	private String personSurname;

	@Schema(description = "User's gender")
	private String personGender;

	/*
	 * @Schema(description = "Date of birth") private LocalDateTime
	 * personDateOfBirth;
	 * 
	 * @Schema(description = "Date of death") private LocalDateTime
	 * personDateOfDeath;
	 */

}
