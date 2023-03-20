package ba.com.zira.sdr.api.model.playlistga;

import java.util.Map;

import ba.com.zira.sdr.api.enums.ServiceType;

public interface Rankable {
    Double calculateFitness(Map<ServiceType, Double> serviceWeights, Map<Long, Double> genreIdWeights);
}
