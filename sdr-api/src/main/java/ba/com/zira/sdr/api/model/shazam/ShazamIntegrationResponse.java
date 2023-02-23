package ba.com.zira.sdr.api.model.shazam;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an Shazam integration response")
public class ShazamIntegrationResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "Unique identifier of the Shazam integration")
	private Long id;

	@Schema(description = "Name of the Shazam integration")
	private String name;

	@Schema(description = "Creation date")
	private LocalDateTime created;

	@Schema(description = "User that created the Shazam integration")
	private String createdBy;

	@Schema(description = "Last modification date")
	private LocalDateTime modified;

	@Schema(description = "User that modified the Shazam integration")
	private String modifiedBy;

	@Schema(description = "Request of the Shazam integration")
	private String request;

	@Schema(description = "Response of the Shazam integration")
	private String response;

	@Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
	private String status;

}