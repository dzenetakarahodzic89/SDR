
package ba.com.zira.sdr.spotify.song.search;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "tracks" })
@Generated("jsonschema2pojo")
@Data
public class SpotifyTrackSearch {

    @JsonProperty("tracks")
    private SpotifyTracks tracks;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
