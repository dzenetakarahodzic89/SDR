package ba.com.zira.sdr.api.model.event;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Properties of event response")
public class EventResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Id of the event")
    private Long id;

    @Schema(description = "Name of the event")
    private String name;

    @Schema(description = "Start time")
    private LocalDate startDate;

    @Schema(description = "End time")
    private LocalDate endDate;

    @Schema(description = "Last edited")
    private LocalDateTime lastUpdated;

    @Schema(description = "Comment")
    private String comment;

    @Schema(description = "Ended")
    private Boolean ended;

    @Schema(description = "Cancelled")
    private Boolean cancelled;

    @Schema(description = "Time of the event")
    private LocalTime time;
}
