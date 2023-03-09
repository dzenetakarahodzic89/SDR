
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
    "album_group",
    "album_type",
    "artists",
    "available_markets",
    "copyrights",
    "external_ids",
    "external_urls",
    "genres",
    "href",
    "id",
    "images",
    "is_playable",
    "label",
    "name",
    "popularity",
    "release_date",
    "release_date_precision",
    "total_tracks",
    "tracks",
    "type",
    "uri"
})
@Generated("jsonschema2pojo")
public class SpotifyGetAlbumsSongs {

    @JsonProperty("album_group")
    private String albumGroup;
    @JsonProperty("album_type")
    private String albumType;
    @JsonProperty("artists")
    private List<Artist> artists = new ArrayList<Artist>();
    @JsonProperty("available_markets")
    private List<String> availableMarkets = new ArrayList<String>();
    @JsonProperty("copyrights")
    private List<Copyright> copyrights = new ArrayList<Copyright>();
    @JsonProperty("external_ids")
    private ExternalIds externalIds;
    @JsonProperty("external_urls")
    private ExternalUrls__1 externalUrls;
    @JsonProperty("genres")
    private List<Object> genres = new ArrayList<Object>();
    @JsonProperty("href")
    private String href;
    @JsonProperty("id")
    private String id;
    @JsonProperty("images")
    private List<Image> images = new ArrayList<Image>();
    @JsonProperty("is_playable")
    private Boolean isPlayable;
    @JsonProperty("label")
    private String label;
    @JsonProperty("name")
    private String name;
    @JsonProperty("popularity")
    private Integer popularity;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("release_date_precision")
    private String releaseDatePrecision;
    @JsonProperty("total_tracks")
    private Integer totalTracks;
    @JsonProperty("tracks")
    private SpotifyAlbumsTracks tracks;
    @JsonProperty("type")
    private String type;
    @JsonProperty("uri")
    private String uri;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("album_group")
    public String getAlbumGroup() {
        return albumGroup;
    }

    @JsonProperty("album_group")
    public void setAlbumGroup(String albumGroup) {
        this.albumGroup = albumGroup;
    }

    public SpotifyGetAlbumsSongs withAlbumGroup(String albumGroup) {
        this.albumGroup = albumGroup;
        return this;
    }

    @JsonProperty("album_type")
    public String getAlbumType() {
        return albumType;
    }

    @JsonProperty("album_type")
    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public SpotifyGetAlbumsSongs withAlbumType(String albumType) {
        this.albumType = albumType;
        return this;
    }

    @JsonProperty("artists")
    public List<Artist> getArtists() {
        return artists;
    }

    @JsonProperty("artists")
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public SpotifyGetAlbumsSongs withArtists(List<Artist> artists) {
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

    public SpotifyGetAlbumsSongs withAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
        return this;
    }

    @JsonProperty("copyrights")
    public List<Copyright> getCopyrights() {
        return copyrights;
    }

    @JsonProperty("copyrights")
    public void setCopyrights(List<Copyright> copyrights) {
        this.copyrights = copyrights;
    }

    public SpotifyGetAlbumsSongs withCopyrights(List<Copyright> copyrights) {
        this.copyrights = copyrights;
        return this;
    }

    @JsonProperty("external_ids")
    public ExternalIds getExternalIds() {
        return externalIds;
    }

    @JsonProperty("external_ids")
    public void setExternalIds(ExternalIds externalIds) {
        this.externalIds = externalIds;
    }

    public SpotifyGetAlbumsSongs withExternalIds(ExternalIds externalIds) {
        this.externalIds = externalIds;
        return this;
    }

    @JsonProperty("external_urls")
    public ExternalUrls__1 getExternalUrls() {
        return externalUrls;
    }

    @JsonProperty("external_urls")
    public void setExternalUrls(ExternalUrls__1 externalUrls) {
        this.externalUrls = externalUrls;
    }

    public SpotifyGetAlbumsSongs withExternalUrls(ExternalUrls__1 externalUrls) {
        this.externalUrls = externalUrls;
        return this;
    }

    @JsonProperty("genres")
    public List<Object> getGenres() {
        return genres;
    }

    @JsonProperty("genres")
    public void setGenres(List<Object> genres) {
        this.genres = genres;
    }

    public SpotifyGetAlbumsSongs withGenres(List<Object> genres) {
        this.genres = genres;
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

    public SpotifyGetAlbumsSongs withHref(String href) {
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

    public SpotifyGetAlbumsSongs withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("images")
    public List<Image> getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(List<Image> images) {
        this.images = images;
    }

    public SpotifyGetAlbumsSongs withImages(List<Image> images) {
        this.images = images;
        return this;
    }

    @JsonProperty("is_playable")
    public Boolean getIsPlayable() {
        return isPlayable;
    }

    @JsonProperty("is_playable")
    public void setIsPlayable(Boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    public SpotifyGetAlbumsSongs withIsPlayable(Boolean isPlayable) {
        this.isPlayable = isPlayable;
        return this;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    public SpotifyGetAlbumsSongs withLabel(String label) {
        this.label = label;
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

    public SpotifyGetAlbumsSongs withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("popularity")
    public Integer getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public SpotifyGetAlbumsSongs withPopularity(Integer popularity) {
        this.popularity = popularity;
        return this;
    }

    @JsonProperty("release_date")
    public String getReleaseDate() {
        return releaseDate;
    }

    @JsonProperty("release_date")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public SpotifyGetAlbumsSongs withReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    @JsonProperty("release_date_precision")
    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

    @JsonProperty("release_date_precision")
    public void setReleaseDatePrecision(String releaseDatePrecision) {
        this.releaseDatePrecision = releaseDatePrecision;
    }

    public SpotifyGetAlbumsSongs withReleaseDatePrecision(String releaseDatePrecision) {
        this.releaseDatePrecision = releaseDatePrecision;
        return this;
    }

    @JsonProperty("total_tracks")
    public Integer getTotalTracks() {
        return totalTracks;
    }

    @JsonProperty("total_tracks")
    public void setTotalTracks(Integer totalTracks) {
        this.totalTracks = totalTracks;
    }

    public SpotifyGetAlbumsSongs withTotalTracks(Integer totalTracks) {
        this.totalTracks = totalTracks;
        return this;
    }

    @JsonProperty("tracks")
    public SpotifyAlbumsTracks getTracks() {
        return tracks;
    }

    @JsonProperty("tracks")
    public void setTracks(SpotifyAlbumsTracks tracks) {
        this.tracks = tracks;
    }

    public SpotifyGetAlbumsSongs withTracks(SpotifyAlbumsTracks tracks) {
        this.tracks = tracks;
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

    public SpotifyGetAlbumsSongs withType(String type) {
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

    public SpotifyGetAlbumsSongs withUri(String uri) {
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

    public SpotifyGetAlbumsSongs withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SpotifyGetAlbumsSongs.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("albumGroup");
        sb.append('=');
        sb.append(((this.albumGroup == null)?"<null>":this.albumGroup));
        sb.append(',');
        sb.append("albumType");
        sb.append('=');
        sb.append(((this.albumType == null)?"<null>":this.albumType));
        sb.append(',');
        sb.append("artists");
        sb.append('=');
        sb.append(((this.artists == null)?"<null>":this.artists));
        sb.append(',');
        sb.append("availableMarkets");
        sb.append('=');
        sb.append(((this.availableMarkets == null)?"<null>":this.availableMarkets));
        sb.append(',');
        sb.append("copyrights");
        sb.append('=');
        sb.append(((this.copyrights == null)?"<null>":this.copyrights));
        sb.append(',');
        sb.append("externalIds");
        sb.append('=');
        sb.append(((this.externalIds == null)?"<null>":this.externalIds));
        sb.append(',');
        sb.append("externalUrls");
        sb.append('=');
        sb.append(((this.externalUrls == null)?"<null>":this.externalUrls));
        sb.append(',');
        sb.append("genres");
        sb.append('=');
        sb.append(((this.genres == null)?"<null>":this.genres));
        sb.append(',');
        sb.append("href");
        sb.append('=');
        sb.append(((this.href == null)?"<null>":this.href));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("images");
        sb.append('=');
        sb.append(((this.images == null)?"<null>":this.images));
        sb.append(',');
        sb.append("isPlayable");
        sb.append('=');
        sb.append(((this.isPlayable == null)?"<null>":this.isPlayable));
        sb.append(',');
        sb.append("label");
        sb.append('=');
        sb.append(((this.label == null)?"<null>":this.label));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("popularity");
        sb.append('=');
        sb.append(((this.popularity == null)?"<null>":this.popularity));
        sb.append(',');
        sb.append("releaseDate");
        sb.append('=');
        sb.append(((this.releaseDate == null)?"<null>":this.releaseDate));
        sb.append(',');
        sb.append("releaseDatePrecision");
        sb.append('=');
        sb.append(((this.releaseDatePrecision == null)?"<null>":this.releaseDatePrecision));
        sb.append(',');
        sb.append("totalTracks");
        sb.append('=');
        sb.append(((this.totalTracks == null)?"<null>":this.totalTracks));
        sb.append(',');
        sb.append("tracks");
        sb.append('=');
        sb.append(((this.tracks == null)?"<null>":this.tracks));
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
        result = ((result* 31)+((this.isPlayable == null)? 0 :this.isPlayable.hashCode()));
        result = ((result* 31)+((this.images == null)? 0 :this.images.hashCode()));
        result = ((result* 31)+((this.copyrights == null)? 0 :this.copyrights.hashCode()));
        result = ((result* 31)+((this.releaseDate == null)? 0 :this.releaseDate.hashCode()));
        result = ((result* 31)+((this.externalIds == null)? 0 :this.externalIds.hashCode()));
        result = ((result* 31)+((this.externalUrls == null)? 0 :this.externalUrls.hashCode()));
        result = ((result* 31)+((this.label == null)? 0 :this.label.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.albumGroup == null)? 0 :this.albumGroup.hashCode()));
        result = ((result* 31)+((this.uri == null)? 0 :this.uri.hashCode()));
        result = ((result* 31)+((this.tracks == null)? 0 :this.tracks.hashCode()));
        result = ((result* 31)+((this.artists == null)? 0 :this.artists.hashCode()));
        result = ((result* 31)+((this.totalTracks == null)? 0 :this.totalTracks.hashCode()));
        result = ((result* 31)+((this.genres == null)? 0 :this.genres.hashCode()));
        result = ((result* 31)+((this.albumType == null)? 0 :this.albumType.hashCode()));
        result = ((result* 31)+((this.availableMarkets == null)? 0 :this.availableMarkets.hashCode()));
        result = ((result* 31)+((this.popularity == null)? 0 :this.popularity.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.releaseDatePrecision == null)? 0 :this.releaseDatePrecision.hashCode()));
        result = ((result* 31)+((this.href == null)? 0 :this.href.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SpotifyGetAlbumsSongs) == false) {
            return false;
        }
        SpotifyGetAlbumsSongs rhs = ((SpotifyGetAlbumsSongs) other);
        return (((((((((((((((((((((((this.isPlayable == rhs.isPlayable)||((this.isPlayable!= null)&&this.isPlayable.equals(rhs.isPlayable)))&&((this.images == rhs.images)||((this.images!= null)&&this.images.equals(rhs.images))))&&((this.copyrights == rhs.copyrights)||((this.copyrights!= null)&&this.copyrights.equals(rhs.copyrights))))&&((this.releaseDate == rhs.releaseDate)||((this.releaseDate!= null)&&this.releaseDate.equals(rhs.releaseDate))))&&((this.externalIds == rhs.externalIds)||((this.externalIds!= null)&&this.externalIds.equals(rhs.externalIds))))&&((this.externalUrls == rhs.externalUrls)||((this.externalUrls!= null)&&this.externalUrls.equals(rhs.externalUrls))))&&((this.label == rhs.label)||((this.label!= null)&&this.label.equals(rhs.label))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.albumGroup == rhs.albumGroup)||((this.albumGroup!= null)&&this.albumGroup.equals(rhs.albumGroup))))&&((this.uri == rhs.uri)||((this.uri!= null)&&this.uri.equals(rhs.uri))))&&((this.tracks == rhs.tracks)||((this.tracks!= null)&&this.tracks.equals(rhs.tracks))))&&((this.artists == rhs.artists)||((this.artists!= null)&&this.artists.equals(rhs.artists))))&&((this.totalTracks == rhs.totalTracks)||((this.totalTracks!= null)&&this.totalTracks.equals(rhs.totalTracks))))&&((this.genres == rhs.genres)||((this.genres!= null)&&this.genres.equals(rhs.genres))))&&((this.albumType == rhs.albumType)||((this.albumType!= null)&&this.albumType.equals(rhs.albumType))))&&((this.availableMarkets == rhs.availableMarkets)||((this.availableMarkets!= null)&&this.availableMarkets.equals(rhs.availableMarkets))))&&((this.popularity == rhs.popularity)||((this.popularity!= null)&&this.popularity.equals(rhs.popularity))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.releaseDatePrecision == rhs.releaseDatePrecision)||((this.releaseDatePrecision!= null)&&this.releaseDatePrecision.equals(rhs.releaseDatePrecision))))&&((this.href == rhs.href)||((this.href!= null)&&this.href.equals(rhs.href))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
