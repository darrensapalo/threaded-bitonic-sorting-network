import sort.Bitonic;
import sort.erroneous.NoBlockingBitonic;
import sort.NoThreadBitonic;
import sort.network.Network;
import util.Dataset;

public class Driver {
	public static void main(String[] args) {
		Bitonic.SIZE = 1024;
		Bitonic.POOL_SIZE = 32;

		int[] numbers = Dataset.generate(Bitonic.SIZE);
        Network network = new Network(Bitonic.SIZE);

		NoThreadBitonic noThreadBitonic = new NoThreadBitonic();
		noThreadBitonic.start(network, numbers);

		Bitonic bitonic = new Bitonic();
		bitonic.start(network, numbers);

		sort.NoThreadPoolBitonic noThreadPoolBitonic = new sort.NoThreadPoolBitonic();
		noThreadPoolBitonic.start(network, numbers);

	}
}
