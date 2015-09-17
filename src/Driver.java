
public class Driver {
	public static void main(String[] args) {
		Bitonic.SIZE = 1024 * 1024;
		Bitonic.poolSize = Bitonic.SIZE / 2;


		System.out.println();
		System.out.println("No threads, compare immediately");
		NoThreadBitonic noThreadBitonic = new NoThreadBitonic();
		noThreadBitonic.main(null);
		
		
		System.out.println();
		System.out.println("With threads, no blocking");
		NoBlockingBitonic noBlockingBitonic = new NoBlockingBitonic();
		noBlockingBitonic.main(null);


		System.out.println();
		System.out.println("With threads, with blocking");
		Bitonic bitonic = new Bitonic();
		bitonic.main(null);
		
		/*
		System.out.println();
		System.out.println("Generate a thread, compare immediately");
		NoThreadPoolBitonic noThreadPoolBitonic = new NoThreadPoolBitonic();
		noThreadPoolBitonic.main(null);
		*/
	}
}
