package ba.com.zira.sdr.api.model.userrecommendation;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a user recommendation")
public class UserRecommendationCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Description of the User recommendation")
    private String description;

    @NotBlank
    @Schema(description = "Name of the User recommendation")
    private String name;

}
