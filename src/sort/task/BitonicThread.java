package sort.task;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import sort.Bitonic;
import sort.BitonicNotTasks;
import sort.network.Network;

public class BitonicThread extends Thread {
	private Network network;
	private int[] numbers;
	private BitonicNotTasks bitonicNotTasks;
	private Semaphore locks;
	private int step;
	private ArrayList<Comparator> list;
	private int numberOfComparators;
	private int partition;
	

	public BitonicThread(int threadNumber, Semaphore locks, BitonicNotTasks bitonicNotTasks,
			Network network, int[] numbers) {
		this.locks = locks;
		this.bitonicNotTasks = bitonicNotTasks;
		this.network = network;
		this.numbers = numbers;
		this.list = new ArrayList<Comparator>();
		
		for (int i = 0; i < network.size(); i++){
			Gate gate = network.getGate(i);
			gate.getSubset(list, threadNumber, network);
		}
	}

	public void init() {
		step = 0;
		
		numberOfComparators = Bitonic.SIZE / 2;
		partition = numberOfComparators / Bitonic.POOL_SIZE;
	}


	@Override
	public void run() {
		do {
			try {
				locks.acquire();
			} catch (Exception e) {
				e.printStackTrace();
			}

			int start = step * partition;
			int end = (partition * (step + 1)) - 1;
			
			for (int i = start; i < end; i++){
				synchronized (bitonicNotTasks) {
					list.get(i).run();
				}
			}
			step++;
			bitonicNotTasks.finish();
		} while (step < network.size());
	}
}
