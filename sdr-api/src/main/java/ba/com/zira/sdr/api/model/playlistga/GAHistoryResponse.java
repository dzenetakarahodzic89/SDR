package ba.com.zira.sdr.api.model.playlistga;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GAHistoryResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Id of history record")
    private Long id;

    @Schema(description = "Population size")
    private Long populationSize;

    @Schema(description = "Number of iterations")
    private Long numberOfIterations;

    @Schema(description = "Maximum value of fitness")
    private Double maxFitness;

    @Schema(description = "Fitness progress over time")
    private String fitnessProgress;

    @Schema(description = "Name of this history record")
    private String name;
}
