
package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import ba.com.zira.sdr.api.model.label.LabelResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for  create persons information")
public class PersonArtistSingleResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String surname;
    private LocalDateTime dateOfBirth;
    private List<LabelResponse> labels;

}
