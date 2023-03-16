package ba.com.zira.sdr.api.model.deezerintegration;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeezerIntegrationTypesData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tableName;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDateTime lastModified;
    private String dataTypeName;
    private String deezerId;

}