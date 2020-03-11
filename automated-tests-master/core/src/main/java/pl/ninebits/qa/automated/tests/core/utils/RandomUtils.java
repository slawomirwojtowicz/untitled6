package pl.ninebits.qa.automated.tests.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomUtils {

    public static int randomInt(final int min, final int max) {
        Random generator = new Random();
        int i = generator.nextInt(max);
        if (i < min) {
            return min;
        }
        return i;
    }

    public static double randomDouble(final int min, final int max, final int scale) {
        if (min < 0 || max <= min || scale < 0) {
            throw new IllegalArgumentException();
        }

        Random generator = new Random();
        return new BigDecimal(generator.nextDouble() * (max - min) + min).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }
}
