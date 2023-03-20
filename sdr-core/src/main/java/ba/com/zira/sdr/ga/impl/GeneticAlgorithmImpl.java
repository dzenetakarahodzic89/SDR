package ba.com.zira.sdr.ga.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import ba.com.zira.sdr.api.enums.SelectionType;
import ba.com.zira.sdr.api.model.playlistga.GeneticAlgorithm;
import ba.com.zira.sdr.api.model.playlistga.PlaylistRequestGA;
import ba.com.zira.sdr.api.model.playlistga.Population;
import ba.com.zira.sdr.api.model.playlistga.Rankable;
import ba.com.zira.sdr.api.model.playlistga.Selection;
import lombok.Data;

@Data
public class GeneticAlgorithmImpl implements GeneticAlgorithm {
    private final Double SUPER_MUTATION_THRESHOLD = 1400.0;
    private Long elitismSize;
    private Long numberOfParentChromosomes;
    private Long numberOfCrossPoints;
    private Double childrenRate;
    private Double mutationRate;
    private Long numberOfGenes;
    private Selection selection;

    private List<SongGene> allValidGenes;

    public GeneticAlgorithmImpl(PlaylistRequestGA playlistRequestGA) {
        elitismSize = playlistRequestGA.getElitismSize();
        numberOfParentChromosomes = playlistRequestGA.getNumberOfParentChromosomes();
        numberOfCrossPoints = playlistRequestGA.getNumberOfCrossPoints();
        childrenRate = playlistRequestGA.getChildrenRate();
        mutationRate = playlistRequestGA.getMutationRate();
        numberOfGenes = playlistRequestGA.getNumberOfGenes();

        if (playlistRequestGA.getSelectionType() == SelectionType.TOURNAMENT) {
            selection = new TournamentSelection(playlistRequestGA.getTournamentSize(), playlistRequestGA.getTournamentRate());
        } else if (playlistRequestGA.getSelectionType() == SelectionType.SUS) {
            selection = new SUSSelection();
        }
    }

    @Override
    public Rankable crossOver(List<Rankable> parents) {
        PlaylistChromosome mixup = new PlaylistChromosome();

        List<Long> randomCrossPointsList = Randomizer.generateRandomListOfNumbers(numberOfCrossPoints, 1L, numberOfGenes);
        Collections.sort(randomCrossPointsList);

        var currentParentIndex = 0;
        var currentCrossPointIndex = 0;
        var listOfGenes = new ArrayList<SongGene>();

        for (var i = 0; i < numberOfGenes; i++) {
            if (currentCrossPointIndex < randomCrossPointsList.size() && i >= randomCrossPointsList.get(currentCrossPointIndex)) {
                currentCrossPointIndex++;
                currentParentIndex = (currentParentIndex + 1) % parents.size();
            }

            listOfGenes.add(((PlaylistChromosome) parents.get(currentParentIndex)).getGenes().get(i));
        }

        // get rid of duplicates & add new chromosomes
        var genesWithoutDuplicate = new ArrayList<SongGene>();
        genesWithoutDuplicate.addAll(new HashSet<>(listOfGenes));

        while (genesWithoutDuplicate.size() < numberOfGenes) {
            var randomIndex = Randomizer.getRandomNumber(0, allValidGenes.size());
            if (!genesWithoutDuplicate.contains(allValidGenes.get(randomIndex))) {
                genesWithoutDuplicate.add(allValidGenes.get(randomIndex));
            }
        }

        mixup.setGenes(genesWithoutDuplicate);
        return mixup;
    }

    @Override
    public Rankable mutation(Rankable chromosome, Boolean pickWorstGene) {
        var oldGeneIndex = 0;

        if (pickWorstGene) {
            oldGeneIndex = ((PlaylistChromosome) chromosome).worstGenePosition().intValue();
            if (((PlaylistChromosome) chromosome).getGenes().get(oldGeneIndex).getFitness() > SUPER_MUTATION_THRESHOLD) {
                return chromosome;
            }

        } else {
            oldGeneIndex = Randomizer.getRandomNumber(0, ((PlaylistChromosome) chromosome).getGenes().size());
        }

        var randomNewGeneIndex = Randomizer.getRandomNumber(0, allValidGenes.size());

        while (PlaylistChromosome.geneExistsInChromosome((PlaylistChromosome) chromosome, allValidGenes.get(randomNewGeneIndex))) {
            randomNewGeneIndex = Randomizer.getRandomNumber(0, allValidGenes.size());
        }

        ((PlaylistChromosome) chromosome).getGenes().set(oldGeneIndex, allValidGenes.get(randomNewGeneIndex));

        return chromosome;
    }

    @Override
    public Population reproduction(Population population) {
        var reproductedChromosomes = population.getNFittestChromosomes(this.elitismSize);

        PlaylistChromosome newChromosome = null;

        for (var i = 0; i < (population.size() - elitismSize); i++) {
            if (Randomizer.getRandomProbability() < this.childrenRate) {
                var parents = this.selection.select(population, this.numberOfParentChromosomes);
                newChromosome = (PlaylistChromosome) this.crossOver(parents);
            } else {
                newChromosome = (PlaylistChromosome) selection.select(population, 1L).get(0);
            }

            if (Randomizer.getRandomProbability() < this.mutationRate) {
                newChromosome = (PlaylistChromosome) this.mutation(newChromosome, false);
            }

            reproductedChromosomes.add(newChromosome);
        }

        // super mutation
        reproductedChromosomes.forEach(chromosome -> this.mutation(chromosome, true));

        population.setChromosomes(reproductedChromosomes);

        return population;
    }

}
