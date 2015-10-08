
package sort;

import sort.network.Network;
import test.SortTest;

/**
 * Created by Darren on 9/19/2015.
 */
public abstract class BitonicTemplate {

    protected abstract void sort(Network network, int[] numbers);

    protected abstract String getApproachName();

    protected void before(Network network, int[] numbers){}
    protected void after(){}


    /**
     *
     * @param network the bitonic sorting network to use
     * @param numbers the dataset array
     * @return The sorting duration in ms
     */
    public long start(Network network, int[] numbers){
        System.out.println(getApproachName());

        before(network, numbers);
        long startTime = System.currentTimeMillis();
        sort(network, numbers);
        long endTime = System.currentTimeMillis();
        after();

        long duration = endTime - startTime;

        System.out.println(" -> " + duration + "ms");
        SortTest test = new SortTest(numbers);
        boolean result = test.isSortedAscending();

        if (!result)
            System.out.println("Sorted: " + result + "\n");

        return duration;
    }
}
