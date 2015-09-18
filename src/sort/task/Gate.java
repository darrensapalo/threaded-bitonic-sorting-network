package sort.task;

import sort.Bitonic;

import java.util.ArrayList;
import java.util.concurrent.ExecutorCompletionService;

/**
 * Created by Darren on 9/19/2015.
 */
public class Gate {
    private ArrayList<Comparator> comparisons;

    public Gate() {
        this.comparisons = new ArrayList<>();
    }

    public void addTask(Comparator task){
        comparisons.add(task);
    }

    public void clear(){
        comparisons.clear();
    }

    public void processWithBlocking(ExecutorCompletionService service, int[] numbers){
        // Work, all ye parallel
        for (Comparator c : comparisons) {
            c.setNumbers(numbers);
            service.submit(c);
        }
        // Block per gate
        int size = Bitonic.SIZE / 2;
        for (int i = 0; i < size; ++i)
        {
            try {
                service.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void immediateCompare(int[] numbers){
        for (Comparator comparison : comparisons) {
            comparison.setNumbers(numbers);
            comparison.run();
        }
    }

    public void generateThreadAndCompare(int[] numbers){
        for (Comparator comparison : comparisons) {
            comparison.setNumbers(numbers);
            Thread thread = new Thread(comparison);
            thread.start();
        }
    }

}
