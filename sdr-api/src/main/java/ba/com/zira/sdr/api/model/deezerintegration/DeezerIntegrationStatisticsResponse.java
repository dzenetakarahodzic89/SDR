package ba.com.zira.sdr.api.model.deezerintegration;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DeezerIntegrationStatisticsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long songTotalCount;
    private Long songDeezerCount;
    private Long artistTotalCount;
    private Long artistDeezerCount;
    private List<DeezerIntegrationTypes> deezerTypes;
    private List<DeezerIntegrationTypesData> deezerTypeData;

}
