package sort.erroneous;

import sort.Bitonic;
import sort.task.Comparator;
import test.SortTest;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoBlockingBitonic {

    /**
     * Verbose comments
     */

    final static boolean VERBOSE_COMMENTS = false;

    /**
     *
     * @param numbers Container for all the elements to sort
     */
    public void main(int[] numbers) {
        /**
         * The total number of comparators
         */
        final int HALF = Bitonic.SIZE / 2;

        /**
         * Create a thread pool
         */
        ExecutorService pool = Executors.newFixedThreadPool(Bitonic.POOL_SIZE);

        /**
         * Notifies us when the executor service is finished
         */
        ExecutorCompletionService ecs = new ExecutorCompletionService(pool);

        /* On what region of the bitonic sort are we in? */
        int region = 1;
        int gate = 1;

        long startTime = System.currentTimeMillis();

		/* Loop for the whole bitonic sorting algorithm. Begin at half the size of the data elements. */
        for (int i = HALF; i >= 1; i /= 2) {
            if (VERBOSE_COMMENTS)
                System.out.println("Phase " + region++);

            int alternate = HALF / i;    //Used for toggling direction whether ascending or descending

			/* j represents the number of comparators in this blocking gate. */
            for (int j = i; j < Bitonic.SIZE; j *= 2) {
                if (VERBOSE_COMMENTS)
                    System.out.println("Gate " + (gate++) + " uses " + j + " comparators");

                int inverse = HALF / j;


                /* Generate all the comparators that will block on this gate */
                for (int id = 0; id < HALF; id++) {
                    int firstElement = id % inverse + id / inverse * inverse * 2;
                    int secondElement = firstElement + inverse;

                    boolean isAscending = (id / alternate) % 2 == 0;

                    /* Submit the comparator task */
                    pool.execute(new Comparator(isAscending, id, firstElement, secondElement));
                    // new sort.task.Comparator(isAscending, id, firstElement, secondElement, numbers).run();
                }
                // System.out.println();
            }
        }

        pool.shutdown();


        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        System.out.println("Sorting took " + duration + "ms.");

        SortTest test = new SortTest(numbers);
        boolean result = test.isSortedAscending();
        System.out.println("Is the array sorted? " + result);
    }
}
