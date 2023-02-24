package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Properties of an Sample response")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the sample")
    private Long id;

}
