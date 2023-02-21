package ba.com.zira.sdr.api.model.songinstrument;



import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "Properties of a songinstrument response")
public class SongInstrument implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Name")
    private Long id;
    
    @Schema(description = "Creatin date")
    private LocalDateTime created;
    
    @Schema(description= "Name crated_by")
    private String created_by;
    
    @Schema(description = "Last modification date")
    private LocalDateTime modified;
    
    @Schema(description = "User that modified the songinstrument") 
    private String modifiedBy;
    
    @Schema(description = "Name of instrument")
    private String name;
    
    @Schema(description = "Id of the object")
    private Long objectId;
     
    @Schema(description = "User that created the songinstrument")
    private String createdBy;
    
    @Schema(description = "The status of the engine", allowableValues = { "Inactive", "Active" })
    private String status;
    
    @Schema(description = "user code")
    private String userCode;
    
    @Schema(description = "Name song_id")
    private Long songId;
    
}
