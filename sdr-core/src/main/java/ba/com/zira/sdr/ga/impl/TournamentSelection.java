package ba.com.zira.sdr.ga.impl;

import java.util.ArrayList;
import java.util.List;

import ba.com.zira.sdr.api.model.playlistga.Population;
import ba.com.zira.sdr.api.model.playlistga.Rankable;
import ba.com.zira.sdr.api.model.playlistga.Selection;

public class TournamentSelection implements Selection {

    private Long tournamentSize;
    private Double tournamentRate;

    public TournamentSelection(Long size, Double rate) {
        tournamentSize = size;
        tournamentRate = rate;
    }

    @Override
    public List<Rankable> select(Population population, Long numberOfChromosomes) {
        List<Rankable> parents = new ArrayList<>();

        for (var i = 0; i < numberOfChromosomes; i++) {
            parents.add(singleTournamentSelection(population));
        }

        return parents;
    }

    private Rankable singleTournamentSelection(Population population) {
        List<Rankable> tournamentPool = new ArrayList<>();

        for (var i = 0; i < tournamentSize; i++) {
            var randomIndex = Randomizer.getRandomNumber(0, population.size().intValue());
            tournamentPool.add(population.getChromosomes().get(randomIndex));
        }

        if (Randomizer.getRandomProbability() < this.tournamentRate) {
            PopulationImpl.sortChromosomes(tournamentPool);
            return tournamentPool.get(0);
        }

        return tournamentPool.get(Randomizer.getRandomNumber(0, tournamentPool.size()));
    }

}
