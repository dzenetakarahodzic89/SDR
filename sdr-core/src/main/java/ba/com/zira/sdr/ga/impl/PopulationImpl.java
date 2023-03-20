package ba.com.zira.sdr.ga.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ba.com.zira.sdr.api.enums.ServiceType;
import ba.com.zira.sdr.api.model.playlistga.Population;
import ba.com.zira.sdr.api.model.playlistga.Rankable;
import lombok.Data;

@Data
public class PopulationImpl implements Population {
    private List<Rankable> chromosomes;
    private Map<ServiceType, Double> serviceWeights;
    private Map<Long, Double> genreIdWeights;
    private Long maxPlaytimeInSeconds;
    private Double totalFitness;

    public PopulationImpl() {
        chromosomes = new ArrayList<>();
        totalFitness = (double) 0;
    }

    @Override
    public List<Rankable> getChromosomes() {
        return chromosomes;
    }

    @Override
    public void setChromosomes(List<Rankable> chromosomes) {
        this.chromosomes = chromosomes;
    }

    @Override
    public Long size() {
        return Long.valueOf(chromosomes.size());
    }

    public PlaylistChromosome addChromosome(PlaylistChromosome chromosome) {
        chromosomes.add(chromosome);
        return chromosome;
    }

    public static List<Rankable> sortChromosomes(List<Rankable> chromosomes) {
        Collections.sort(chromosomes, (ch1, ch2) -> (int) Math.round(
                (((PlaylistChromosome) ch2).getFitness().longValue() - ((PlaylistChromosome) ch1).getFitness().longValue()) * 100.0));
        return chromosomes;
    }

    @Override
    public List<Rankable> getNFittestChromosomes(Long n) {
        sortChromosomes(this.chromosomes);
        return chromosomes.stream().limit(n).collect(Collectors.toList());
    }

    @Override
    public Rankable findFittestChromosome() {
        sortChromosomes(this.chromosomes);
        return chromosomes.get(0);
    }

    @Override
    public void calculateFitnessForAllChromosomes() {
        for (Rankable chromosomeRankable : chromosomes) {
            PlaylistChromosome chromosome = (PlaylistChromosome) chromosomeRankable;
            chromosome.calculateFitness(serviceWeights, genreIdWeights);

            if (chromosome.getPlaytime() > maxPlaytimeInSeconds) {
                chromosome.setFitness((double) 0);
            } else {
                Double punishmentRate = 1.0 - (maxPlaytimeInSeconds - chromosome.getPlaytime()) / maxPlaytimeInSeconds;
                chromosome.setFitness(chromosome.getFitness() * punishmentRate);
            }
        }

        this.getTotalFitness();
    }

    @Override
    public Double getTotalFitness() {
        totalFitness = (double) 0;
        for (var chromosome : chromosomes) {
            totalFitness += ((PlaylistChromosome) chromosome).getFitness();
        }

        for (var chromosome : chromosomes) {
            ((PlaylistChromosome) chromosome).setFitnessPercentage((((PlaylistChromosome) chromosome).getFitness()) / (totalFitness));
        }

        return totalFitness;
    }

}
