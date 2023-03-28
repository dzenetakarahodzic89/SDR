package ba.com.zira.sdr.api.model.itunesintegration;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Properties of itunesintegration create request")
public class ItunesIntegrationCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "Name of this itunesintegration")
    private String name;

   
    @Schema(description = "Request")
    private String request;

    @Schema(description = "Response")
    private String response;
} 
