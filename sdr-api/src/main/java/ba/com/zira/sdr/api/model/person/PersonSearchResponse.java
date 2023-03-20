package ba.com.zira.sdr.api.model.person;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Schema(description = "Properties of a person response")
public class PersonSearchResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String surname;
    private String outlineText;
    private String gender;
    private LocalDateTime modified;
    private String imageUrl;

    public PersonSearchResponse(Long id, String name, String surname, String outlineText, String gender, LocalDateTime modified) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.outlineText = outlineText;
        this.gender = gender;
        this.modified = modified;
    }

}
