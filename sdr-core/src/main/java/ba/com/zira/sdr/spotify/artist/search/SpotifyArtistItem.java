
package ba.com.zira.sdr.spotify.artist.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ba.com.zira.sdr.spotify.SpotifyImage;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "images", "name" })
@Generated("jsonschema2pojo")
@Data
public class SpotifyArtistItem {

    @JsonProperty("id")
    private String id;
    @JsonProperty("images")
    private List<SpotifyImage> images = new ArrayList<>();
    @JsonProperty("name")
    private String name;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
