package ba.com.zira.sdr.api.artist;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistSearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Long album;
    private Long genre;
    private Boolean isSolo;
    private String sortBy;

}
