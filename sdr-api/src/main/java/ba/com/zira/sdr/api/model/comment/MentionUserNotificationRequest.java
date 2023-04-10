package ba.com.zira.sdr.api.model.comment;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ba.com.zira.sdr.api.enums.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Properties for notification request when user(s) is(are) mentioned")
public class MentionUserNotificationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Content of comment")
    private String commentContent;
    @NotNull
    @Schema(description = "User that tagged another user")
    private String mentionInitiator;
    @NotEmpty
    @Schema(description = "Users that were tagged")
    private List<String> mentionTargets;
    @NotNull
    @Schema(description = " Type of the object_type")
    private ObjectType objectType;
    @NotBlank
    @Schema(description = " Name of the object")
    private String objectName;
    @NotBlank
    @Schema(description = " URL of the overview in whose comment the user was tagged")
    private String overviewUrl;

}
