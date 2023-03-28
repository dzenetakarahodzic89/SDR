package ba.com.zira.sdr.ga.impl;

import java.util.Map;

import ba.com.zira.sdr.api.enums.ServiceType;
import ba.com.zira.sdr.api.model.playlistga.Rankable;
import lombok.Data;

@Data
public class SongGene implements Rankable {

    private Map<ServiceType, Double> serviceScores;

    private Long genreId;
    private String genreName;

    private Long songId;
    private String songName;

    private Long playtimeInSeconds;

    private Double fitness;

    public SongGene() {
        fitness = (double) 0;
        playtimeInSeconds = 0L;
    }

    @Override
    public Double calculateFitness(Map<ServiceType, Double> serviceWeights, Map<Long, Double> genreIdWeights) {
        fitness = (double) 0;

        if (serviceWeights.get(ServiceType.DEEZER) != null) {
            fitness += serviceScores.get(ServiceType.DEEZER) * serviceWeights.get(ServiceType.DEEZER);
        }

        if (serviceWeights.get(ServiceType.GOOGLE_PLAY) != null) {
            fitness += serviceScores.get(ServiceType.GOOGLE_PLAY) * serviceWeights.get(ServiceType.GOOGLE_PLAY);
        }

        if (serviceWeights.get(ServiceType.ITUNES) != null) {
            fitness += serviceScores.get(ServiceType.ITUNES) * serviceWeights.get(ServiceType.ITUNES);
        }

        if (serviceWeights.get(ServiceType.SDR) != null) {
            fitness += serviceScores.get(ServiceType.SDR) * serviceWeights.get(ServiceType.SDR);
        }

        if (serviceWeights.get(ServiceType.SPOTIFY) != null) {
            fitness += serviceScores.get(ServiceType.SPOTIFY) * serviceWeights.get(ServiceType.SPOTIFY);
        }

        if (serviceWeights.get(ServiceType.TIDAL) != null) {
            fitness += serviceScores.get(ServiceType.TIDAL) * serviceWeights.get(ServiceType.TIDAL);
        }

        if (serviceWeights.get(ServiceType.YT_MUSIC) != null) {
            fitness += serviceScores.get(ServiceType.YT_MUSIC) * serviceWeights.get(ServiceType.YT_MUSIC);
        }

        if (genreIdWeights.get(genreId) != null) {
            fitness += 10 * genreIdWeights.get(genreId);
        }

        return fitness;
    }

    @Override
    public String toString() {
        String text = "Fitness of this gene is: " + this.fitness.toString() + "\n";

        text += "Song name: " + songName + "\n";
        text += "Genre name: " + genreName + "\n";
        text += "Playtime in seconds: " + playtimeInSeconds + "\n";

        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        var song = (SongGene) obj;
        return this.songId.equals(song.getSongId());
    }
}
