package ba.com.zira.sdr.api.model.album;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AlbumSearchResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    Long id;
    LocalDateTime dateOfRelease;
    String name;
    String information;
    Long eraId;
    LocalDateTime modified;
    Long playtimeInSeconds;

    String imageUrl;

    public AlbumSearchResponse(Long id, LocalDateTime dateOfRelease, String name, String information, Long eraId, LocalDateTime modified,
            Long playtimeInSeconds) {
        super();
        this.id = id;
        this.dateOfRelease = dateOfRelease;
        this.name = name;
        this.information = information;
        this.eraId = eraId;
        this.modified = modified;
        this.playtimeInSeconds = playtimeInSeconds;
    }

}
