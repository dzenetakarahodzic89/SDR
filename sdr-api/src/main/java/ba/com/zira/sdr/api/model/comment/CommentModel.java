package ba.com.zira.sdr.api.model.comment;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of a comment response")
public class CommentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the comment")
    private Long id;
    @Schema(description = "Contet of comment")
    private String content;
    //@Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    //private String status;
    @Schema(description = "Creation date")
    private LocalDateTime created;
    @Schema(description = "User that created the comment")
    private String createdBy;
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    @Schema(description = "User that modified the comment") //only person who create comment can edit
    private String modifiedBy;
    @Schema(description = "Id of the object")
    private long objectId;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = "user code")
    private String userCode;
}
