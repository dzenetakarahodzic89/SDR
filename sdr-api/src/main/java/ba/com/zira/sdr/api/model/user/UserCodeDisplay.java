package ba.com.zira.sdr.api.model.user;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties of an UserCodeDisplay response")
public class UserCodeDisplay implements Serializable {
    private static final long serialVersionUID = 1L;
    private String usercode;
    private String displayname;
}
