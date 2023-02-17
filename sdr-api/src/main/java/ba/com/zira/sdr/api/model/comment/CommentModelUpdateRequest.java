package ba.com.zira.sdr.api.model.comment;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for update a comment")
public class CommentModelUpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(description = "Unique identifier of the comment")
    private long id;
    @NotBlank
    @Schema(description = "Content of comment")
    private String content;
}