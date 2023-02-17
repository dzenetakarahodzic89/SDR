package ba.com.zira.sdr.api.model.comment;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data; 

@Data
@Schema(description = "Properties for creation of a comment")
public class CommentModelCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Content of comment")
    private String content;
    @Schema(description = "User that created the comment")
    private String createdBy;
    @Schema(description = "Id of the object")
    private long objectId;
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    @Schema(description = " Type of the object_type")
    private String objectType;
}