import sort.Bitonic;
import sort.NoThreadBitonic;
import sort.network.Network;
import util.Dataset;

public class Driver {
    public static void main(String[] args) {
        Bitonic.SIZE = 1024 * 64;
        Bitonic.POOL_SIZE = 32;

        /**
         * Perform tests for 1024*64 and the four following data points
         */
        int i = Bitonic.SIZE * 2 * 2 * 2 * 2;

        /**
         * Perform multiple tests and get the average performance
         */
        int numberOfTests = 25;

        for (int size = Bitonic.SIZE; size <= i; size *= 2) {

            int[] numbers = Dataset.generate(Bitonic.SIZE);

            System.out.println("Dataset (" + size + ") generated.");

            Network network = new Network(Bitonic.SIZE);

            System.out.println("Network generated.");

            long duration = 0;
            for (int count = 0; count < numberOfTests; count++) {
                NoThreadBitonic noThreadBitonic = new NoThreadBitonic();
                long durationSub = noThreadBitonic.start(network, numbers);
                duration += durationSub;
            }
            duration /= numberOfTests;
            System.out.println("Average: " + duration + "ms");

            duration = 0;
            for (int count = 0; count < numberOfTests; count++) {
                Bitonic bitonic = new Bitonic();
                long durationSub = bitonic.start(network, numbers);
                duration += durationSub;
            }
            duration /= numberOfTests;
            System.out.println("Average: " + duration + "ms");

        }

        // NoThreadPoolBitonic noThreadPoolBitonic = new sort.NoThreadPoolBitonic();
        // noThreadPoolBitonic.start(network, numbers);

    }
}
