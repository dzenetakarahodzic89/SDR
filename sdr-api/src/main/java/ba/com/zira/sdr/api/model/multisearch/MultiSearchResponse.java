package ba.com.zira.sdr.api.model.multisearch;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MultiSearchResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String type;

    private String imageUrl;

    private String spotifyId;

    public MultiSearchResponse(Long id, String name, String type) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public MultiSearchResponse(String name, String type, String spotifyId) {
        super();
        this.name = name;
        this.type = type;
        this.spotifyId = spotifyId;
    }

}
