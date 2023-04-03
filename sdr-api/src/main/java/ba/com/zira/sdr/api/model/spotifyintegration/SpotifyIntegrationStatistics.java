package ba.com.zira.sdr.api.model.spotifyintegration;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpotifyIntegrationStatistics implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long songTotalCount;
    private Long songSpotifyCount;
    private Long artistTotalCount;
    private Long artistSpotifyCount;
    private Long albumTotalCount;
    private Long albumSpotifyCount;
    private List<SpotifyIntegrationTypes> spotifyTypes;
    private List<SpotifyIntegrationTypesData> spotifyTypeData;

}
