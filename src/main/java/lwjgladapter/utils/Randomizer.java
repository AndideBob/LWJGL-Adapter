package lwjgladapter.utils;

import java.util.Collection;
import java.util.Random;

public class Randomizer {

    private static long RANDOM_SEED = System.currentTimeMillis();

    private static Random random = null;

    private static Random getRandom() {
        if (random == null) {
            random = new Random(RANDOM_SEED);
        }
        return random;
    }

    public static long getSeed() {
        return RANDOM_SEED;
    }

    public static void setSeed(long value) {
        RANDOM_SEED = value;
        // Reset random to use new seed
        random = new Random(RANDOM_SEED);
    }

    public static double nextDouble(double min, double max) {
        double diff = max - min;
        if (diff < 0) {
            throw new IllegalArgumentException("Can't generate Random Number, because min(" + min + ") is bigger than max(" + max + ")!");
        }
        return min + getRandom().nextDouble(diff);
    }

    public static int nextInt(int min, int max) {
        int diff = max - min;
        if (diff < 0) {
            throw new IllegalArgumentException("Can't generate Random Number, because min(" + min + ") is bigger than max(" + max + ")!");
        }
        return min + getRandom().nextInt(diff);
    }

    public static boolean nextBoolean() {
        return getRandom().nextBoolean();
    }

    public static <T> T getRandomElement(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        return (T) getRandomElement(collection.toArray());
    }

    public static <T> T getRandomElement(T[] array) {
        int pickedIndex = nextInt(0, array.length);
        return array[pickedIndex];
    }

}
