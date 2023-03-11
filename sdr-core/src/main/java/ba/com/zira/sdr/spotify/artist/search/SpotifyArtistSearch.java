
package ba.com.zira.sdr.spotify.artist.search;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "artists" })
@Generated("jsonschema2pojo")
@Data
public class SpotifyArtistSearch {

    @JsonProperty("artists")
    private SpotifyArtists artists;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
