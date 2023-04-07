package ba.com.zira.sdr.api.model.comment;

import java.io.Serializable;

import ba.com.zira.sdr.api.enums.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a comment")
public class CommentNotificationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "User that created the object")
    private String createdBy;
    @Schema(description = "User that created the comment")
    private String userName;
    @Schema(description = "Id of the object")
    private String objectName;
    @Schema(description = "Type of the object_type")
    private ObjectType objectType;
}