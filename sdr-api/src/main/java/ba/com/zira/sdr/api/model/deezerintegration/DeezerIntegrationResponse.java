package ba.com.zira.sdr.api.model.deezerintegration;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeezerIntegrationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String status;
    private String response;
    private String objectType;
    private Long objectId;

}
