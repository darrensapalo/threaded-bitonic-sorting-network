package sort;

import sort.network.Network;
import sort.task.BitonicThread;
import sort.task.Gate;

import java.util.Iterator;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class BitonicNotTasks extends BitonicTemplate {

	@Override
	protected String getApproachName() {
		return "Bitonic approach that uses a number of threads without repeated submission of tasks (Generates "
				+ Bitonic.POOL_SIZE + " thread/s)";
	}

	/**
	 * Thread pool
	 */
	private ExecutorService pool;

	private BitonicThread[] threads;
	private Semaphore[] locks;
	private Semaphore mainLock;
	
	private int finished;
	@Override
	protected void before(Network network, int[] numbers) {
		finished = 0;
		/**
		 * Create a thread pool
		 */
		if (pool == null)
			pool = Executors.newFixedThreadPool(Bitonic.POOL_SIZE);

		if (locks == null)
			locks = new Semaphore[Bitonic.POOL_SIZE];
		
		if (threads == null){
			threads = new BitonicThread[Bitonic.POOL_SIZE];
		
			for (int j = 0; j < Bitonic.POOL_SIZE; j++){
				
				// Generate locks and threads
				
				locks[j] = new Semaphore(0);
				threads[j] = new BitonicThread(j, locks[j], this, network, numbers);
				threads[j].start();
			}
		}
		
		if (mainLock == null)
			mainLock = new Semaphore(0);

		for (int j = 0; j < Bitonic.POOL_SIZE; j++){
			threads[j].init();
		}
		
	}

	protected void sort(Network network, int[] numbers) {
		int step = 0;
		
		mainLock.release();

		// start the threads
		for(int i = 0; i < threads.length; i++)
			locks[i].release();
		
		do {
			try {
				mainLock.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			network.display(step);
		}while(step++ < network.size());
	}

	@Override
	protected void after() {
		pool.shutdown();
	}

	public synchronized void finish() {
		finished++;
		if (finished == threads.length)
		{
			// Everyone is finished. 
			finished = 0;
			
			// Allow all to proceed
			for(int i = 0; i < threads.length; i++)
				locks[i].release();
			
			mainLock.release();
		}
	}
}
