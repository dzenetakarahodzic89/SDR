
package ba.com.zira.sdr.spotify.album.songs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "artists",
    "available_markets",
    "disc_number",
    "duration_ms",
    "explicit",
    "external_urls",
    "href",
    "id",
    "is_local",
    "name",
    "preview_url",
    "track_number",
    "type",
    "uri"
})
@Generated("jsonschema2pojo")
public class SpotifyAlbumsTrackItem {

    @JsonProperty("artists")
    private List<Artist__1> artists = new ArrayList<Artist__1>();
    @JsonProperty("available_markets")
    private List<String> availableMarkets = new ArrayList<String>();
    @JsonProperty("disc_number")
    private Integer discNumber;
    @JsonProperty("duration_ms")
    private Integer durationMs;
    @JsonProperty("explicit")
    private Boolean explicit;
    @JsonProperty("external_urls")
    private ExternalUrls__3 externalUrls;
    @JsonProperty("href")
    private String href;
    @JsonProperty("id")
    private String id;
    @JsonProperty("is_local")
    private Boolean isLocal;
    @JsonProperty("name")
    private String name;
    @JsonProperty("preview_url")
    private String previewUrl;
    @JsonProperty("track_number")
    private Integer trackNumber;
    @JsonProperty("type")
    private String type;
    @JsonProperty("uri")
    private String uri;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("artists")
    public List<Artist__1> getArtists() {
        return artists;
    }

    @JsonProperty("artists")
    public void setArtists(List<Artist__1> artists) {
        this.artists = artists;
    }

    public SpotifyAlbumsTrackItem withArtists(List<Artist__1> artists) {
        this.artists = artists;
        return this;
    }

    @JsonProperty("available_markets")
    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    @JsonProperty("available_markets")
    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public SpotifyAlbumsTrackItem withAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
        return this;
    }

    @JsonProperty("disc_number")
    public Integer getDiscNumber() {
        return discNumber;
    }

    @JsonProperty("disc_number")
    public void setDiscNumber(Integer discNumber) {
        this.discNumber = discNumber;
    }

    public SpotifyAlbumsTrackItem withDiscNumber(Integer discNumber) {
        this.discNumber = discNumber;
        return this;
    }

    @JsonProperty("duration_ms")
    public Integer getDurationMs() {
        return durationMs;
    }

    @JsonProperty("duration_ms")
    public void setDurationMs(Integer durationMs) {
        this.durationMs = durationMs;
    }

    public SpotifyAlbumsTrackItem withDurationMs(Integer durationMs) {
        this.durationMs = durationMs;
        return this;
    }

    @JsonProperty("explicit")
    public Boolean getExplicit() {
        return explicit;
    }

    @JsonProperty("explicit")
    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    public SpotifyAlbumsTrackItem withExplicit(Boolean explicit) {
        this.explicit = explicit;
        return this;
    }

    @JsonProperty("external_urls")
    public ExternalUrls__3 getExternalUrls() {
        return externalUrls;
    }

    @JsonProperty("external_urls")
    public void setExternalUrls(ExternalUrls__3 externalUrls) {
        this.externalUrls = externalUrls;
    }

    public SpotifyAlbumsTrackItem withExternalUrls(ExternalUrls__3 externalUrls) {
        this.externalUrls = externalUrls;
        return this;
    }

    @JsonProperty("href")
    public String getHref() {
        return href;
    }

    @JsonProperty("href")
    public void setHref(String href) {
        this.href = href;
    }

    public SpotifyAlbumsTrackItem withHref(String href) {
        this.href = href;
        return this;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public SpotifyAlbumsTrackItem withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("is_local")
    public Boolean getIsLocal() {
        return isLocal;
    }

    @JsonProperty("is_local")
    public void setIsLocal(Boolean isLocal) {
        this.isLocal = isLocal;
    }

    public SpotifyAlbumsTrackItem withIsLocal(Boolean isLocal) {
        this.isLocal = isLocal;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public SpotifyAlbumsTrackItem withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("preview_url")
    public String getPreviewUrl() {
        return previewUrl;
    }

    @JsonProperty("preview_url")
    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public SpotifyAlbumsTrackItem withPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
        return this;
    }

    @JsonProperty("track_number")
    public Integer getTrackNumber() {
        return trackNumber;
    }

    @JsonProperty("track_number")
    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public SpotifyAlbumsTrackItem withTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public SpotifyAlbumsTrackItem withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    public SpotifyAlbumsTrackItem withUri(String uri) {
        this.uri = uri;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public SpotifyAlbumsTrackItem withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpotifyAlbumsTrackItem.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("artists");
        sb.append('=');
        sb.append(((this.artists == null)?"<null>":this.artists));
        sb.append(',');
        sb.append("availableMarkets");
        sb.append('=');
        sb.append(((this.availableMarkets == null)?"<null>":this.availableMarkets));
        sb.append(',');
        sb.append("discNumber");
        sb.append('=');
        sb.append(((this.discNumber == null)?"<null>":this.discNumber));
        sb.append(',');
        sb.append("durationMs");
        sb.append('=');
        sb.append(((this.durationMs == null)?"<null>":this.durationMs));
        sb.append(',');
        sb.append("explicit");
        sb.append('=');
        sb.append(((this.explicit == null)?"<null>":this.explicit));
        sb.append(',');
        sb.append("externalUrls");
        sb.append('=');
        sb.append(((this.externalUrls == null)?"<null>":this.externalUrls));
        sb.append(',');
        sb.append("href");
        sb.append('=');
        sb.append(((this.href == null)?"<null>":this.href));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("isLocal");
        sb.append('=');
        sb.append(((this.isLocal == null)?"<null>":this.isLocal));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("previewUrl");
        sb.append('=');
        sb.append(((this.previewUrl == null)?"<null>":this.previewUrl));
        sb.append(',');
        sb.append("trackNumber");
        sb.append('=');
        sb.append(((this.trackNumber == null)?"<null>":this.trackNumber));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("uri");
        sb.append('=');
        sb.append(((this.uri == null)?"<null>":this.uri));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.previewUrl == null)? 0 :this.previewUrl.hashCode()));
        result = ((result* 31)+((this.trackNumber == null)? 0 :this.trackNumber.hashCode()));
        result = ((result* 31)+((this.externalUrls == null)? 0 :this.externalUrls.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.uri == null)? 0 :this.uri.hashCode()));
        result = ((result* 31)+((this.isLocal == null)? 0 :this.isLocal.hashCode()));
        result = ((result* 31)+((this.explicit == null)? 0 :this.explicit.hashCode()));
        result = ((result* 31)+((this.discNumber == null)? 0 :this.discNumber.hashCode()));
        result = ((result* 31)+((this.artists == null)? 0 :this.artists.hashCode()));
        result = ((result* 31)+((this.availableMarkets == null)? 0 :this.availableMarkets.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.href == null)? 0 :this.href.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.durationMs == null)? 0 :this.durationMs.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpotifyAlbumsTrackItem) == false) {
            return false;
        }
        SpotifyAlbumsTrackItem rhs = ((SpotifyAlbumsTrackItem) other);
        return ((((((((((((((((this.previewUrl == rhs.previewUrl)||((this.previewUrl!= null)&&this.previewUrl.equals(rhs.previewUrl)))&&((this.trackNumber == rhs.trackNumber)||((this.trackNumber!= null)&&this.trackNumber.equals(rhs.trackNumber))))&&((this.externalUrls == rhs.externalUrls)||((this.externalUrls!= null)&&this.externalUrls.equals(rhs.externalUrls))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.uri == rhs.uri)||((this.uri!= null)&&this.uri.equals(rhs.uri))))&&((this.isLocal == rhs.isLocal)||((this.isLocal!= null)&&this.isLocal.equals(rhs.isLocal))))&&((this.explicit == rhs.explicit)||((this.explicit!= null)&&this.explicit.equals(rhs.explicit))))&&((this.discNumber == rhs.discNumber)||((this.discNumber!= null)&&this.discNumber.equals(rhs.discNumber))))&&((this.artists == rhs.artists)||((this.artists!= null)&&this.artists.equals(rhs.artists))))&&((this.availableMarkets == rhs.availableMarkets)||((this.availableMarkets!= null)&&this.availableMarkets.equals(rhs.availableMarkets))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.href == rhs.href)||((this.href!= null)&&this.href.equals(rhs.href))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.durationMs == rhs.durationMs)||((this.durationMs!= null)&&this.durationMs.equals(rhs.durationMs))));
    }

}
