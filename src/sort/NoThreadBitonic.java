package sort;

import sort.network.Network;
import sort.task.Comparator;
import sort.task.Gate;

public class NoThreadBitonic extends BitonicTemplate {

    protected void sort(Network network, int[] numbers) {

        int size = network.size();
        for (int i = 0; i < size; i++){
            Gate gate = network.getGate(i);
            gate.immediateCompare(numbers);
        }

    }


    @Override
    protected String getApproachName() {
        return "No Threads at all Bitonic (Immediately compares the comparators)";
    }
}
