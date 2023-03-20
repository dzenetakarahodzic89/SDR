package ba.com.zira.sdr.ga.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ba.com.zira.sdr.api.enums.ServiceType;
import ba.com.zira.sdr.api.model.playlistga.Rankable;
import lombok.Data;

@Data
public class PlaylistChromosome implements Rankable {
    private List<SongGene> genes;
    private Double fitness;
    private Double fitnessPercentage;

    public static Boolean geneExistsInChromosome(PlaylistChromosome chromosome, SongGene gene) {
        return chromosome.getGenes().contains(gene);
    }

    public PlaylistChromosome() {
        genes = new ArrayList<>();
        fitness = (double) 0;
    }

    public PlaylistChromosome addGene(SongGene gene) {
        genes.add(gene);
        return this;
    }

    public Long getPlaytime() {
        return genes.stream().map(SongGene::getPlaytimeInSeconds).mapToLong(Long::longValue).sum();
    }

    public Long worstGenePosition() {
        Long position = 0L;
        Double currentFitness = 100000.0;

        for (var i = 0; i < genes.size(); i++) {
            if (currentFitness > genes.get(i).getFitness()) {
                currentFitness = genes.get(i).getFitness();
                position = (long) i;
            }
        }

        return position;
    }

    @Override
    public Double calculateFitness(Map<ServiceType, Double> serviceWeights, Map<Long, Double> genreIdWeights) {
        fitness = (double) 0;

        for (SongGene songGene : genes) {
            fitness += songGene.calculateFitness(serviceWeights, genreIdWeights);
        }

        fitness /= (double) genes.size();

        return fitness;
    }

    @Override
    public String toString() {
        String text = "Fitness of this playlist is: " + this.fitness.toString() + "\n";

        for (var i = 0; i < genes.size(); i++) {
            text += "Gen redni broj " + i + ". : \n";
            text += genes.get(i).toString();
        }

        text += "Total playtime of this playlist: " + this.getPlaytime() + "\n";

        return text;
    }
}
