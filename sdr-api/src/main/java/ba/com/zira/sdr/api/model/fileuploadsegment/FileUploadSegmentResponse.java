package ba.com.zira.sdr.api.model.fileuploadsegment;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for response of file upload segment")
public class FileUploadSegmentResponse implements Serializable {
    private static final long serialVersionUID = 1;
    @Schema(description = "file upload segment id")
    private String id;
    @Schema(description = "File name")
    private String fileName;
    @Schema(description = "Current file segment")
    private Long fileSegment;
    @Schema(description = "Total number of segments")
    private Long fileSegmentTotal;
    @Schema(description = "File segment content - base64 string")
    private String fileSegmentContent;
    @Schema(description = "Media id - song, person etc id")
    private Long mediaId;

    public FileUploadSegmentResponse(String id, String fileName, Long fileSegment, Long fileSegmentTotal, String fileSegmentContent,
            Long mediaId) {
        this.id = id;
        this.fileName = fileName;
        this.fileSegment = fileSegment;
        this.fileSegmentTotal = fileSegmentTotal;
        this.fileSegmentContent = fileSegmentContent;
        this.mediaId = mediaId;
    }
}
