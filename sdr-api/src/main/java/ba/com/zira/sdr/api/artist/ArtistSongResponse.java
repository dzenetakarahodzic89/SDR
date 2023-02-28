package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ArtistSongResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	 @Schema(description = "Unique identifier")
	private Long id;
	 @Schema(description = "Full stage name")
	private String name;
	 @Schema(description="Person Id")
	 private Long personId;
	 @Schema(description = "Full real name")
	private String personName;
	 @Schema(description = "Date of Birth")
	private LocalDateTime dateOfBirth;
	 @Schema(description = "Instrument name")
	private String instrument;
	 @Schema(description = "Album Name")
	private String album;
	 @Schema(description = "Label Name")
	private String label;
}
