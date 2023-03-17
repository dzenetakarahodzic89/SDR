package ba.com.zira.sdr.api.playlistga;

import java.util.List;

public interface Population {
    List<Chromosome> getNFittestChromosomes(Integer n);

    Chromosome findFittestChromosome();

    void calculateFitnessForAllChromosomes();
}
