package ba.com.zira.sdr.api.instrument;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response of number of songs by eras")
public class ResponseSongInstrumentEra implements Serializable  {
    private static final long serialVersionUID = 1L;


    @Schema(description = "Name of era")
    private String eraName;

    @Schema(description = "Unique identifier of the song")
    private Long songCount;

    public ResponseSongInstrumentEra(String eraName,Long songCount)
    {

        this.eraName=eraName;
        this.songCount=songCount;
    }

    public String getEraName() {
        return eraName;
    }
    public Long getSongCount() {
        return songCount;
    }
    public void setEraName(String eraName) {
        this.eraName = eraName;
    }
    public void setSongCount(Long songCount) {
        this.songCount = songCount;
    }

}




