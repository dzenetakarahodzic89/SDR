package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update of person request")
public class PersonUpdateRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "ID of record for update")
	private Long id;

	@Schema(description = "Information about user")
	private String information;

	@Schema(description = "User name")
	private String Name;

	@Schema(description = "User surname")
	private String Surname;

	@Schema(description = "User's gender")
	private String gender;

}
