
package ba.com.zira.sdr.deezer;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "link", "picture", "picture_small", "picture_medium", "picture_big", "picture_xl", "nb_album", "nb_fan",
        "radio", "tracklist", "type" })
@Generated("jsonschema2pojo")
public class Datum {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("link")
    private String link;
    @JsonProperty("picture")
    private String picture;
    @JsonProperty("picture_small")
    private String pictureSmall;
    @JsonProperty("picture_medium")
    private String pictureMedium;
    @JsonProperty("picture_big")
    private String pictureBig;
    @JsonProperty("picture_xl")
    private String pictureXl;
    @JsonProperty("nb_album")
    private Integer nbAlbum;
    @JsonProperty("nb_fan")
    private Integer nbFan;
    @JsonProperty("radio")
    private Boolean radio;
    @JsonProperty("tracklist")
    private String tracklist;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Datum withId(Integer id) {
        this.id = id;
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

    public Datum withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    public Datum withLink(String link) {
        this.link = link;
        return this;
    }

    @JsonProperty("picture")
    public String getPicture() {
        return picture;
    }

    @JsonProperty("picture")
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Datum withPicture(String picture) {
        this.picture = picture;
        return this;
    }

    @JsonProperty("picture_small")
    public String getPictureSmall() {
        return pictureSmall;
    }

    @JsonProperty("picture_small")
    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public Datum withPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
        return this;
    }

    @JsonProperty("picture_medium")
    public String getPictureMedium() {
        return pictureMedium;
    }

    @JsonProperty("picture_medium")
    public void setPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
    }

    public Datum withPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
        return this;
    }

    @JsonProperty("picture_big")
    public String getPictureBig() {
        return pictureBig;
    }

    @JsonProperty("picture_big")
    public void setPictureBig(String pictureBig) {
        this.pictureBig = pictureBig;
    }

    public Datum withPictureBig(String pictureBig) {
        this.pictureBig = pictureBig;
        return this;
    }

    @JsonProperty("picture_xl")
    public String getPictureXl() {
        return pictureXl;
    }

    @JsonProperty("picture_xl")
    public void setPictureXl(String pictureXl) {
        this.pictureXl = pictureXl;
    }

    public Datum withPictureXl(String pictureXl) {
        this.pictureXl = pictureXl;
        return this;
    }

    @JsonProperty("nb_album")
    public Integer getNbAlbum() {
        return nbAlbum;
    }

    @JsonProperty("nb_album")
    public void setNbAlbum(Integer nbAlbum) {
        this.nbAlbum = nbAlbum;
    }

    public Datum withNbAlbum(Integer nbAlbum) {
        this.nbAlbum = nbAlbum;
        return this;
    }

    @JsonProperty("nb_fan")
    public Integer getNbFan() {
        return nbFan;
    }

    @JsonProperty("nb_fan")
    public void setNbFan(Integer nbFan) {
        this.nbFan = nbFan;
    }

    public Datum withNbFan(Integer nbFan) {
        this.nbFan = nbFan;
        return this;
    }

    @JsonProperty("radio")
    public Boolean getRadio() {
        return radio;
    }

    @JsonProperty("radio")
    public void setRadio(Boolean radio) {
        this.radio = radio;
    }

    public Datum withRadio(Boolean radio) {
        this.radio = radio;
        return this;
    }

    @JsonProperty("tracklist")
    public String getTracklist() {
        return tracklist;
    }

    @JsonProperty("tracklist")
    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public Datum withTracklist(String tracklist) {
        this.tracklist = tracklist;
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

    public Datum withType(String type) {
        this.type = type;
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

    public Datum withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        String nullValue = "<null>";
        StringBuilder sb = new StringBuilder();
        sb.append(Datum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? nullValue : this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? nullValue : this.name));
        sb.append(',');
        sb.append("link");
        sb.append('=');
        sb.append(((this.link == null) ? nullValue : this.link));
        sb.append(',');
        sb.append("picture");
        sb.append('=');
        sb.append(((this.picture == null) ? nullValue : this.picture));
        sb.append(',');
        sb.append("pictureSmall");
        sb.append('=');
        sb.append(((this.pictureSmall == null) ? nullValue : this.pictureSmall));
        sb.append(',');
        sb.append("pictureMedium");
        sb.append('=');
        sb.append(((this.pictureMedium == null) ? nullValue : this.pictureMedium));
        sb.append(',');
        sb.append("pictureBig");
        sb.append('=');
        sb.append(((this.pictureBig == null) ? nullValue : this.pictureBig));
        sb.append(',');
        sb.append("pictureXl");
        sb.append('=');
        sb.append(((this.pictureXl == null) ? nullValue : this.pictureXl));
        sb.append(',');
        sb.append("nbAlbum");
        sb.append('=');
        sb.append(((this.nbAlbum == null) ? nullValue : this.nbAlbum));
        sb.append(',');
        sb.append("nbFan");
        sb.append('=');
        sb.append(((this.nbFan == null) ? nullValue : this.nbFan));
        sb.append(',');
        sb.append("radio");
        sb.append('=');
        sb.append(((this.radio == null) ? nullValue : this.radio));
        sb.append(',');
        sb.append("tracklist");
        sb.append('=');
        sb.append(((this.tracklist == null) ? nullValue : this.tracklist));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null) ? nullValue : this.type));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null) ? nullValue : this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.tracklist == null) ? 0 : this.tracklist.hashCode()));
        result = ((result * 31) + ((this.nbFan == null) ? 0 : this.nbFan.hashCode()));
        result = ((result * 31) + ((this.link == null) ? 0 : this.link.hashCode()));
        result = ((result * 31) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((result * 31) + ((this.picture == null) ? 0 : this.picture.hashCode()));
        result = ((result * 31) + ((this.radio == null) ? 0 : this.radio.hashCode()));
        result = ((result * 31) + ((this.pictureBig == null) ? 0 : this.pictureBig.hashCode()));
        result = ((result * 31) + ((this.pictureSmall == null) ? 0 : this.pictureSmall.hashCode()));
        result = ((result * 31) + ((this.nbAlbum == null) ? 0 : this.nbAlbum.hashCode()));
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode()));
        result = ((result * 31) + ((this.pictureMedium == null) ? 0 : this.pictureMedium.hashCode()));
        result = ((result * 31) + ((this.pictureXl == null) ? 0 : this.pictureXl.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Datum)) {
            return false;
        }
        Datum rhs = ((Datum) other);
        return (((((((((((((((this.tracklist == rhs.tracklist) || ((this.tracklist != null) && this.tracklist.equals(rhs.tracklist)))
                && ((this.nbFan == rhs.nbFan) || ((this.nbFan != null) && this.nbFan.equals(rhs.nbFan))))
                && ((this.link == rhs.link) || ((this.link != null) && this.link.equals(rhs.link))))
                && ((this.type == rhs.type) || ((this.type != null) && this.type.equals(rhs.type))))
                && ((this.picture == rhs.picture) || ((this.picture != null) && this.picture.equals(rhs.picture))))
                && ((this.radio == rhs.radio) || ((this.radio != null) && this.radio.equals(rhs.radio))))
                && ((this.pictureBig == rhs.pictureBig) || ((this.pictureBig != null) && this.pictureBig.equals(rhs.pictureBig))))
                && ((this.pictureSmall == rhs.pictureSmall) || ((this.pictureSmall != null) && this.pictureSmall.equals(rhs.pictureSmall))))
                && ((this.nbAlbum == rhs.nbAlbum) || ((this.nbAlbum != null) && this.nbAlbum.equals(rhs.nbAlbum))))
                && ((this.name == rhs.name) || ((this.name != null) && this.name.equals(rhs.name))))
                && ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id))))
                && ((this.additionalProperties == rhs.additionalProperties)
                        || ((this.additionalProperties != null) && this.additionalProperties.equals(rhs.additionalProperties))))
                && ((this.pictureMedium == rhs.pictureMedium)
                        || ((this.pictureMedium != null) && this.pictureMedium.equals(rhs.pictureMedium))))
                && ((this.pictureXl == rhs.pictureXl) || ((this.pictureXl != null) && this.pictureXl.equals(rhs.pictureXl))));
    }

}
