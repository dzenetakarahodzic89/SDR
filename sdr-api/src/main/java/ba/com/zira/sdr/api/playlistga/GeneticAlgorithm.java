package ba.com.zira.sdr.api.playlistga;

import java.util.List;

public interface GeneticAlgorithm {

    Population reproduction(Population population);

    Chromosome crossOver(List<Chromosome> parents);

    Chromosome mutation(Chromosome chromosome);
}
