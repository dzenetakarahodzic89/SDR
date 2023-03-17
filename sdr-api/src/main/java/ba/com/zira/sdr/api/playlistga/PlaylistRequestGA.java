package ba.com.zira.sdr.api.playlistga;

import java.io.Serializable;
import java.util.Map;

import ba.com.zira.sdr.api.enums.SelectionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Properties for creation of a artist")
public class PlaylistRequestGA implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Population size")
    private Long populationSize;

    @Schema(description = "Number of generations")
    private Long numberOfGenerations;

    @Schema(description = "Elitisim size")
    private Long elitismSize;

    @Schema(description = "Number of parent chromosomes")
    private Long numberOfParentChromosomes;

    @Schema(description = "Number of cross points")
    private Long numberOfCrossPoints;

    @Schema(description = "Children rate")
    private Long childrenRate;

    @Schema(description = "Mutation rate")
    private Long mutationRate;

    @Schema(description = "Number of genes")
    private Long numberOfGenes;

    @Schema(description = "Selection type")
    private SelectionType selectionType;

    @Schema(description = "Tournament size")
    private Long tournamentSize;

    @Schema(description = "Tournament rate")
    private Long tournamentRate;

    @Schema(description = "Service priorities")
    private Map<String, Long> servicePriorities;

    @Schema(description = "Genre priorities")
    private Map<String, Long> genrePriorities;

    @Schema(description = "Total playtime")
    private Long totalPlaytime;
}
