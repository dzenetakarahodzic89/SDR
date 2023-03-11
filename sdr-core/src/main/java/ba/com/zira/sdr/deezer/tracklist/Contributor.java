
package ba.com.zira.sdr.deezer.tracklist;

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
@JsonPropertyOrder({
    "id",
    "name",
    "link",
    "share",
    "picture",
    "picture_small",
    "picture_medium",
    "picture_big",
    "picture_xl",
    "radio",
    "tracklist",
    "type",
    "role"
})
@Generated("jsonschema2pojo")
public class Contributor {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("link")
    private String link;
    @JsonProperty("share")
    private String share;
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
    @JsonProperty("radio")
    private Boolean radio;
    @JsonProperty("tracklist")
    private String tracklist;
    @JsonProperty("type")
    private String type;
    @JsonProperty("role")
    private String role;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Contributor withId(Integer id) {
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

    public Contributor withName(String name) {
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

    public Contributor withLink(String link) {
        this.link = link;
        return this;
    }

    @JsonProperty("share")
    public String getShare() {
        return share;
    }

    @JsonProperty("share")
    public void setShare(String share) {
        this.share = share;
    }

    public Contributor withShare(String share) {
        this.share = share;
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

    public Contributor withPicture(String picture) {
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

    public Contributor withPictureSmall(String pictureSmall) {
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

    public Contributor withPictureMedium(String pictureMedium) {
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

    public Contributor withPictureBig(String pictureBig) {
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

    public Contributor withPictureXl(String pictureXl) {
        this.pictureXl = pictureXl;
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

    public Contributor withRadio(Boolean radio) {
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

    public Contributor withTracklist(String tracklist) {
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

    public Contributor withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    public Contributor withRole(String role) {
        this.role = role;
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

    public Contributor withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Contributor.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("link");
        sb.append('=');
        sb.append(((this.link == null)?"<null>":this.link));
        sb.append(',');
        sb.append("share");
        sb.append('=');
        sb.append(((this.share == null)?"<null>":this.share));
        sb.append(',');
        sb.append("picture");
        sb.append('=');
        sb.append(((this.picture == null)?"<null>":this.picture));
        sb.append(',');
        sb.append("pictureSmall");
        sb.append('=');
        sb.append(((this.pictureSmall == null)?"<null>":this.pictureSmall));
        sb.append(',');
        sb.append("pictureMedium");
        sb.append('=');
        sb.append(((this.pictureMedium == null)?"<null>":this.pictureMedium));
        sb.append(',');
        sb.append("pictureBig");
        sb.append('=');
        sb.append(((this.pictureBig == null)?"<null>":this.pictureBig));
        sb.append(',');
        sb.append("pictureXl");
        sb.append('=');
        sb.append(((this.pictureXl == null)?"<null>":this.pictureXl));
        sb.append(',');
        sb.append("radio");
        sb.append('=');
        sb.append(((this.radio == null)?"<null>":this.radio));
        sb.append(',');
        sb.append("tracklist");
        sb.append('=');
        sb.append(((this.tracklist == null)?"<null>":this.tracklist));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("role");
        sb.append('=');
        sb.append(((this.role == null)?"<null>":this.role));
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
        result = ((result* 31)+((this.tracklist == null)? 0 :this.tracklist.hashCode()));
        result = ((result* 31)+((this.role == null)? 0 :this.role.hashCode()));
        result = ((result* 31)+((this.link == null)? 0 :this.link.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.picture == null)? 0 :this.picture.hashCode()));
        result = ((result* 31)+((this.radio == null)? 0 :this.radio.hashCode()));
        result = ((result* 31)+((this.pictureBig == null)? 0 :this.pictureBig.hashCode()));
        result = ((result* 31)+((this.pictureSmall == null)? 0 :this.pictureSmall.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.share == null)? 0 :this.share.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.pictureMedium == null)? 0 :this.pictureMedium.hashCode()));
        result = ((result* 31)+((this.pictureXl == null)? 0 :this.pictureXl.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Contributor) == false) {
            return false;
        }
        Contributor rhs = ((Contributor) other);
        return (((((((((((((((this.tracklist == rhs.tracklist)||((this.tracklist!= null)&&this.tracklist.equals(rhs.tracklist)))&&((this.role == rhs.role)||((this.role!= null)&&this.role.equals(rhs.role))))&&((this.link == rhs.link)||((this.link!= null)&&this.link.equals(rhs.link))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.picture == rhs.picture)||((this.picture!= null)&&this.picture.equals(rhs.picture))))&&((this.radio == rhs.radio)||((this.radio!= null)&&this.radio.equals(rhs.radio))))&&((this.pictureBig == rhs.pictureBig)||((this.pictureBig!= null)&&this.pictureBig.equals(rhs.pictureBig))))&&((this.pictureSmall == rhs.pictureSmall)||((this.pictureSmall!= null)&&this.pictureSmall.equals(rhs.pictureSmall))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.share == rhs.share)||((this.share!= null)&&this.share.equals(rhs.share))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.pictureMedium == rhs.pictureMedium)||((this.pictureMedium!= null)&&this.pictureMedium.equals(rhs.pictureMedium))))&&((this.pictureXl == rhs.pictureXl)||((this.pictureXl!= null)&&this.pictureXl.equals(rhs.pictureXl))));
    }

}
