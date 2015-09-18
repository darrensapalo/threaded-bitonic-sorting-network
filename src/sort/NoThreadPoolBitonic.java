package sort;

import sort.network.Network;
import sort.task.Gate;

public class NoThreadPoolBitonic extends BitonicTemplate{

    @Override
    protected void sort(Network network, int[] numbers) {
        for (int i = 0; i < network.size(); i++){
            Gate gate = network.getGate(i);
            gate.generateThreadAndCompare(numbers);
        }
    }

    @Override
    protected String getApproachName() {
        return "No Thread Pool Bitonic (Generates thread for each comparator)";
    }
}
