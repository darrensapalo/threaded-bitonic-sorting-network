package sort;

import sort.network.Network;
import sort.task.Gate;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bitonic extends BitonicTemplate{


    @Override
    protected String getApproachName() {
        return "Bitonic with Thread Pool and Blocking (Generates " + POOL_SIZE + " thread/s and creates tasks)";
    }

    /**
     * Verbose comments
     */
    public final static boolean VERBOSE_COMMENTS = false;

    /**
     * The total number of elements to sort
     */
    public static int SIZE = 1024 * 32;

    /**
     * The size of the thread pool
     */
    public static int POOL_SIZE = 2;

    /**
     * Thread pool
     */
    private ExecutorService pool;

    /**
     * Waits for the thread pool to finish
     */
    private ExecutorCompletionService ecs;

    @Override
    protected void before() {

        /**
         * Create a thread pool
         */
        pool = Executors.newFixedThreadPool(Bitonic.POOL_SIZE);

        /**
         * Notifies us when the executor service is finished
         */
        ecs = new ExecutorCompletionService(pool);
    }

    protected void sort(Network network, int[] numbers) {
        for (int i = 0; i < network.size(); i++){
            Gate gate = network.getGate(i);
            gate.processWithBlocking(ecs, numbers);
        }
    }

    @Override
    protected void after() {
        pool.shutdown();
    }
}
