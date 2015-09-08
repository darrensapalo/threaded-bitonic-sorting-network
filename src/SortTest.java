/**
 * Created by Darren on 9/8/2015.
 */
public class SortTest {
    private int[] numbers;

    public SortTest(int[] numbers) {
        this.numbers = numbers;
    }

    public boolean isSortedAscending(){

        for (int i = 0; i < numbers.length - 1; i++) {
            int first = numbers[i];
            int second = numbers[i + 1];
            if (first > second)
                return false;
        }
        return true;
    }
}
