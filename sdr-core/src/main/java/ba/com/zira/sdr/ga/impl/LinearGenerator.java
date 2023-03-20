package ba.com.zira.sdr.ga.impl;

import java.util.ArrayList;
import java.util.List;

import ba.com.zira.sdr.api.model.playlistga.WeightGenerator;

public class LinearGenerator implements WeightGenerator {

    @Override
    public List<Double> generate(Integer numberOfPriorities) {
        var weights = new ArrayList<Double>();
        var den = numberOfPriorities * (numberOfPriorities + 1L) / 2L;

        for (var i = 1; i <= numberOfPriorities; i++) {
            weights.add(Double.valueOf(i * 100.0) / den);
        }

        return weights;
    }

}
