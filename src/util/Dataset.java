package util;

import java.util.Random;

/**
 * Created by Darren on 9/19/2015.
 */
public class Dataset {
    public static int[] generate(int size){
        int[] numbers = new int[size];
        Random random = new Random();

		/* Generate random numbers and place them in the list of elements */
        for (int i = 0; i < size; i++) {

            numbers[i] = random.nextInt();
        }
        return numbers;
    }
}
