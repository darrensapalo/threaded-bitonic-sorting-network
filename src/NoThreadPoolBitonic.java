import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoThreadPoolBitonic {

    /**
     * Verbose comments
     */

    final static boolean VERBOSE_COMMENTS = false;

    public void main(String[] args) {
        /**
         * The total number of elements to sort
         */
        final int SIZE = Bitonic.SIZE;

        /**
         * The total number of comparators
         */
        final int HALF = SIZE / 2;

        /**
         * Container for all the elements to sort
         */
        final int[] numbers = new int[SIZE];

        /**
         * Utility class for selecting random numbers
         */
        Random random = new Random();

        /**
         * Create a thread pool
         */
        ExecutorService pool = Executors.newFixedThreadPool(Bitonic.poolSize);


        /**
         * Notifies us when the executor service is finished
         */
        ExecutorCompletionService ecs = new ExecutorCompletionService(pool);

		/* Generate random numbers and place them in the list of elements */
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = random.nextInt();
            // System.out.println(i + " " + numbers[i]);
        }

        /* On what region of the bitonic sort are we in? */
        int region = 1;
        int gate = 1;

        
        Instant start = Instant.now();

		/* Loop for the whole bitonic sorting algorithm. Begin at half the size of the data elements. */
        for (int i = HALF; i >= 1; i /= 2) {
            if (VERBOSE_COMMENTS)
                System.out.println("Phase " + region++);

            int alternate = HALF / i;    //Used for toggling direction whether ascending or descending

			/* j represents the number of comparators in this blocking gate. */
            for (int j = i; j < SIZE; j *= 2) {
                if (VERBOSE_COMMENTS)
                    System.out.println("Gate " + (gate++) + " uses " + j + " comparators");

                int inverse = HALF / j;


                /* Generate all the comparators that will block on this gate */
                for (int id = 0; id < HALF; id++) {
                    int firstElement = id % inverse + id / inverse * inverse * 2;
                    int secondElement = firstElement + inverse;

                    boolean isAscending = (id / alternate) % 2 == 0;

                    /* Submit the comparator task */
                    // ecs.submit(new Comparator(isAscending, id, firstElement, secondElement, numbers));
                    new Thread(new Comparator(isAscending, id, firstElement, secondElement, numbers)).start();
                }
                // System.out.println();
            }
        }

        Instant end = Instant.now();
        
        if (false)
        /* Display the sorted list */
        for (int l = 0; l < SIZE; l++) {
            System.out.println(l + " " + numbers[l]);
        }
        
        long duration = start.until(end, ChronoUnit.MILLIS);

        System.out.println("Sorting took " + duration + "ms.");
        
        SortTest test = new SortTest(numbers);
        boolean result = test.isSortedAscending();
        System.out.println("Is the array sorted? " + result);
    }
}
