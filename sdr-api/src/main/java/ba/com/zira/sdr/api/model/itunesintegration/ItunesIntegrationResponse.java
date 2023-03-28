package ba.com.zira.sdr.api.model.itunesintegration;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Properties of itunesintegration response")
public class ItunesIntegrationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of itunesintegration record")
    private Long id;

    @Schema(description = "Name of this itunesintegration")
    private String name;
    
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    
    @Schema(description = "Creation date")
    private LocalDateTime created;
    
    @Schema(description = "User that created this itunesintegration")
    private String createdBy;

    @Schema(description = "Modified date")
    private LocalDateTime modified;
    
    @Schema(description = "User that modified this itunesintegration")
    private String modifiedBy;
    
    @Schema(description = "Request")
    private String request;
    
    @Schema(description = "Response")
    private String response;
    
    public ItunesIntegrationResponse(Long id, String name, String request, String response) {
        this.id = id;
        this.name = name;
        this.request = request;
        this.response = response;
        
    } 

}
