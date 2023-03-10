
package ba.com.zira.sdr.spotify.album.search;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "albums" })
@Generated("jsonschema2pojo")
@Data
public class SpotifyAlbumSearch {

    @JsonProperty("albums")
    private SpotifyAlbums albums;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
