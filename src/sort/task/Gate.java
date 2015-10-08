package sort.task;

import sort.Bitonic;
import sort.network.Network;

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

	public void addTask(Comparator task) {
		comparisons.add(task);
	}

	public void clear() {
		comparisons.clear();
	}

	public void processWithBlocking(ExecutorCompletionService service,
			int[] numbers) {
		// Work, all ye parallel
		for (Comparator c : comparisons) {
			c.setNumbers(numbers);
			service.submit(c);
		}
		// Block at this gate
		int size = Bitonic.SIZE / 2;
		for (int i = 0; i < size; ++i) {
			try {
				service.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void immediateCompare(int[] numbers) {

		// Just compare immedaitely
		for (Comparator comparison : comparisons) {
			comparison.setNumbers(numbers);
			comparison.run();
		}
	}

	public void generateThreadAndCompare(int[] numbers) {
		int size = Bitonic.SIZE / 2;
		Thread[] threads = new Thread[size];

		// Work, all ye parallel
		int count = 0;
		for (Comparator comparison : comparisons) {
			comparison.setNumbers(numbers);
			Thread thread = new Thread(comparison);
			threads[count++] = thread;
			thread.start();
		}

		// Block at this gate
		for (int i = 0; i < size; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void getSubset(ArrayList<Comparator> list, int threadNumber, Network network) {
		// 0-3
		// 4-7
		// 8-11
		// 12-15

		// (length * threadnumber)
		// ( (length + 1) * threadnumber) - 1
		int numberOfComparators = Bitonic.SIZE / 2;
		int partition = numberOfComparators / Bitonic.POOL_SIZE;
		int start = threadNumber * partition;
		int end = ((threadNumber + 1) * partition) - 1;
		
		for (int j = start; j <= end; j++){
			Comparator comparator = comparisons.get(j);
			list.add(comparator);
		}
	}

	public void display() {
		for(int i = 0; i < comparisons.size(); i++)
		{
			System.out.println(comparisons.get(i).toString());
		}
	}

}
