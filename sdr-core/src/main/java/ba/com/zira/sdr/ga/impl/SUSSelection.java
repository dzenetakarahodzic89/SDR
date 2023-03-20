package ba.com.zira.sdr.ga.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ba.com.zira.sdr.api.model.playlistga.Population;
import ba.com.zira.sdr.api.model.playlistga.Rankable;
import ba.com.zira.sdr.api.model.playlistga.Selection;

public class SUSSelection implements Selection {

    @Override
    public List<Rankable> select(Population population, Long numberOfChromosomes) {
        List<Rankable> parents = new ArrayList<>();

        population.calculateFitnessForAllChromosomes();

        for (int i = 0; i < numberOfChromosomes; i++) {
            Collections.shuffle(population.getChromosomes());
            parents.add(SUSSingleSelector(population));
        }

        return parents;
    }

    private Rankable SUSSingleSelector(Population population) {
        var j = 0;
        var sum = ((PlaylistChromosome) population.getChromosomes().get(j)).getFitnessPercentage();
        var u = Randomizer.randomDouble(0.0, 1.0);

        while (sum < u) {
            j++;
            sum += ((PlaylistChromosome) population.getChromosomes().get(j)).getFitnessPercentage();
        }
        return population.getChromosomes().get(j);
    }
}
