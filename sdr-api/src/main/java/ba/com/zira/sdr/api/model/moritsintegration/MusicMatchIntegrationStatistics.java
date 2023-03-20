package ba.com.zira.sdr.api.model.moritsintegration;

import java.io.Serializable;
import java.util.List;

import ba.com.zira.sdr.api.model.lov.DoubleStringLoV;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicMatchIntegrationStatistics implements Serializable {

    private List<SongLyricData> songLyricData;
    private List<MusicMatchIntegrationStatus> musicMatchIntegrationStatus;
    private List<DoubleStringLoV> musicMatchStatusDistribution;

}
