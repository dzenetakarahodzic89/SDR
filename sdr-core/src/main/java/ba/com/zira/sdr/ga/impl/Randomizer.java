package ba.com.zira.sdr.ga.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer {
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.ints(min, max).findFirst().getAsInt();
    }

    public static double getRandomProbability() {
        Random random = new Random();
        return random.ints(0, 101).findFirst().getAsInt() / 100.0;
    }

    public static List<Long> generateRandomListOfNumbers(Long size, Long min, Long max) {
        List<Long> generatedList = new ArrayList<>();

        for (var i = 0; i < size; i++) {
            generatedList.add((long) getRandomNumber(min.intValue(), max.intValue()));
        }

        return generatedList;
    }

    public static Double randomDouble(Double min, Double max) {
        Random random = new Random();
        return random.doubles(min, max).findFirst().getAsDouble();
    }
}
