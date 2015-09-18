package sort;

import sort.network.Network;
import test.SortTest;

/**
 * Created by Darren on 9/19/2015.
 */
public abstract class BitonicTemplate {

    protected abstract void sort(Network network, int[] numbers);

    protected abstract String getApproachName();

    protected void before(){}
    protected void after(){}



    public void start(Network network, int[] numbers){
        System.out.println(getApproachName());

        before();
        long startTime = System.currentTimeMillis();
        sort(network, numbers);
        long endTime = System.currentTimeMillis();
        after();

        long duration = endTime - startTime;

        System.out.println("Duration: " + duration + "ms");
        SortTest test = new SortTest(numbers);
        boolean result = test.isSortedAscending();
        System.out.println("Sorted: " + result + "\n");
    }
}
