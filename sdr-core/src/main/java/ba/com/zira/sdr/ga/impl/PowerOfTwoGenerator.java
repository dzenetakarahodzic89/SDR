package ba.com.zira.sdr.ga.impl;

import java.util.ArrayList;
import java.util.List;

import ba.com.zira.sdr.api.model.playlistga.WeightGenerator;

public class PowerOfTwoGenerator implements WeightGenerator {

    @Override
    public List<Double> generate(Integer numberOfPriorities) {
        var weights = new ArrayList<Double>();
        var num = 1L;
        var den = Math.pow(2L, numberOfPriorities) - 1L;

        for (var i = 0; i < numberOfPriorities; i++) {
            weights.add((num * 100.0) / den);
            num *= 2;
        }

        return weights;
    }

}
