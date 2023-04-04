package ba.com.zira.sdr.api.model.comment;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for comments fetch request")
public class CommentsFetchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id of the object")
    private Long objectId;

    @Schema(description = " Type of the object_type")
    private String objectType;

}
