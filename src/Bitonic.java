import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bitonic {

	public static void main(String[] args) {

		/*Initialization of values*/
		final int SIZE = 16;
		final int HALF = SIZE / 2;
		final int[] numbers = new int[SIZE];
		Random random = new Random();
		ExecutorService pool = Executors.newFixedThreadPool(HALF);

		/*Generate 16,384 random numbers*/
		for (int i = 0; i < SIZE; i++) {
			numbers[i] = random.nextInt(100);
			System.out.println(i + " " + numbers[i]);
		}

        System.out.println();

        /*Loop for the whole bitonic sorting algorithm*/
		for (int i = HALF; i >= 1; i /= 2) {
			// System.out.println("\nSize: " + i);
			int alternate = HALF / i;    //Used for toggling direction whether ascending or descending

			/*Bitonic merge*/
			for (int j = i; j < SIZE; j *= 2) {
				int inverse = HALF / j;
				for (int k = 0; k < HALF; k++) {
					int first = k % inverse + k / inverse * inverse * 2;
					pool.execute(new Switch((k / alternate) % 2 == 0, k, first, first + inverse, numbers));
				}
				// System.out.println();


			}
		}
		pool.shutdown();

        /*Print numbers*/
        for (int l = 0; l < SIZE; l++) {
            System.out.println(l + " " + numbers[l]);
        }
        System.out.println();
	}
}
