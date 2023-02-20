package ba.com.zira.sdr.api.artist;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonArtistResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime endOfRelationship;
    private LocalDateTime modified;
    private String modifiedBy;
    private LocalDateTime startOfRelaltionship;
    private String status;

    public PersonArtistResponse(Long id, LocalDateTime endOfRelationship, LocalDateTime startOfRelaltionship, String status) {
        super();
        this.id = id;
        this.endOfRelationship = endOfRelationship;
        this.startOfRelaltionship = startOfRelaltionship;
        this.status = status;

    };

    public PersonArtistResponse() {
    };

}
