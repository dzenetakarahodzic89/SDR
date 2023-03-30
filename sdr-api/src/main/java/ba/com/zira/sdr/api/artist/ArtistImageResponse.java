package ba.com.zira.sdr.api.artist;

import java.io.Serializable;

import lombok.Data;

@Data
public class ArtistImageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    Long id;
    String imageUrl;
    String name;

    public ArtistImageResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
