package ba.com.zira.sdr.api.model.playlistga;

import java.util.List;

public interface GeneticAlgorithm {

    Population reproduction(Population population);

    Rankable crossOver(List<Rankable> parents);

    Rankable mutation(Rankable chromosome, Boolean pickWorstGene);
}
