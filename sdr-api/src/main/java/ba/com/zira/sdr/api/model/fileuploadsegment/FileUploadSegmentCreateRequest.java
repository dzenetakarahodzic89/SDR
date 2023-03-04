package ba.com.zira.sdr.api.model.fileuploadsegment;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a chord progression")
public class FileUploadSegmentCreateRequest implements Serializable {
    private static final long serialVersionUID = 1;

    @Min(1)
    @Schema(description = "Media id - song, person etc id")
    private Long id;

    @NotBlank
    @Schema(description = "Name of the file")
    private String fileName;

    @NotBlank
    @Schema(description = "Type of object", allowableValues = { "SONG" })
    private String type;

    @Min(1)
    @Schema(description = "Latest number of segment")
    private Long fileSegment;

    @Min(1)
    @Schema(description = "Total number of segments")
    private Long fileSegmentTotal;

    @NotBlank
    @Schema(description = "Content of segment")
    private String fileSegmentContent;
}
