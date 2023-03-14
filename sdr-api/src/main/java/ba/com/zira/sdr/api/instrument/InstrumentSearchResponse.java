package ba.com.zira.sdr.api.instrument;

import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a instrument search response")
public class InstrumentSearchResponse implements Serializable {
    private static final long serialVersionUID = 1L;

<<<<<<< Updated upstream
    @NotBlank
    @Schema(description = "Unique identifier of the instrument")
    private Long id;

    @Schema(description = "Name of the instrument")
    private String name;

    @Schema(description = "Instrument type")
    private String type;

    @Schema(description = "Instrument information")
    private String information;

    @NotBlank
    @Schema(description = "Creation date")
    private LocalDateTime created;

    @NotBlank
    @Schema(description = "User that created the instrument")
    private String createdBy;

    @Schema(description = "Last modification date")
    private LocalDateTime modified;

    @Schema(description = "User that modified the instrument")
    private String modifiedBy;

    @Schema(description = "The status", allowableValues = { "Inactive", "Active" })
    private String status;

    @Schema(description = "Outline text")
    private String outlineText;

    @Schema(description = "Cover image URL")
    private String imageUrl;

    public InstrumentSearchResponse(Long id, String name, String type, String information, LocalDateTime created, String createdBy,
            LocalDateTime modified, String modifiedBy, String status, String outlineText) {
        super();
        this.id = id;
        this.type = type;
        this.name = name;
        this.information = information;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.status = status;
        this.outlineText = outlineText;
    }
=======
	@NotBlank

	private Long id;
	private String name;
	private LocalDateTime modified;
	private String outlineText;
	private String imageUrl;

	public InstrumentSearchResponse(Long id, String name, LocalDateTime modified, String outlineText) {
		this.id = id;
		this.name = name;
		this.modified = modified;
		this.outlineText = outlineText;

	}
>>>>>>> Stashed changes

}
