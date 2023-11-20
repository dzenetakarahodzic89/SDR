package ba.com.zira.sdr.api.model.playlistga;

import java.util.List;

public interface Population {
    Long size();

    void setChromosomes(List<Rankable> chromosomes);

    List<Rankable> getChromosomes();

    List<Rankable> getNFittestChromosomes(Long n);

    Rankable findFittestChromosome();

    Double getTotalFitness();

    void calculateFitnessForAllChromosomes();
}
