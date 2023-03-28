package ba.com.zira.sdr.api.artist;


import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class ArtistSingleResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String imageUrl;
    private LocalDateTime dateOfBirth;
    private Long albumCount;

    public ArtistSingleResponse(Long id, String name, String imageUrl, LocalDateTime dateOfBirth, Long albumCount) {
        super();
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.dateOfBirth = dateOfBirth;
        this.albumCount = albumCount;
    }

    public ArtistSingleResponse(Long id, String name, LocalDateTime dateOfBirth, Long albumCount) {
        super();
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.albumCount = albumCount;
    }


}
