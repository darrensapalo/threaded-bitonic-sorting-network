package sort.task;

import sort.Bitonic;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * This switch class will ensure that when the run method is called,
 * the first element is already the larger number.
 */
public class Comparator implements Serializable, Callable<Boolean>, Runnable {

	/**
	 * This flag is true when the first element must be greater than the second element.
	 */
	private boolean isAscending;

    /**
     * The unique identifier for this task
     */
	private final int id;

    /**
     * The index of the first element, referenced in the array of numbers.
     */
	private final int firstElement;

    /**
     * The index of the second element, referenced in the array of numbers.
     */
	private final int secondElement;

    /**
     * The array of numbers which holds all the data elements.
     */
	private int[] numbers;

	public Comparator(boolean isAscending, int taskID, int firstElement, int secondElement) {
		this.isAscending = isAscending;
		this.id = taskID;
		this.firstElement = firstElement;
		this.secondElement = secondElement;
        if (Bitonic.VERBOSE_COMMENTS)
            System.out.printf("Compare elem %4d %4d %s\n", firstElement, secondElement, (isAscending ? " asc" : " desc"));
	}

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * <p>If <code>isAscending</code> is true, this comparator ensures that <code>numbers[firstElement]</code>
     * is greater than <code>numbers[secondElement]</code></p>
     *
     * <p>If <code>isAscending</code> is false, this comparator ensures that <code>numbers[secondElement]</code>
     * is greater than <code>numbers[firstElement]</code></p>
     *
     * @return true - if the function performed a swap
	 */
	@Override
	public Boolean call() {
        if (isAscending && numbers[firstElement] > numbers[secondElement] || !isAscending && numbers[firstElement] < numbers[secondElement]) {
            int temp = numbers[firstElement];
            numbers[firstElement] = numbers[secondElement];
            numbers[secondElement] = temp;
            // System.out.println("Task #" + id + " performed the switch.");
            return true;
        }
        return false;
	}

    /**
     * <p>If <code>isAscending</code> is true, this comparator ensures that <code>numbers[firstElement]</code>
     * is greater than <code>numbers[secondElement]</code></p>
     *
     * <p>If <code>isAscending</code> is false, this comparator ensures that <code>numbers[secondElement]</code>
     * is greater than <code>numbers[firstElement]</code></p>
     */
    @Override
    public void run() {
        if (isAscending && numbers[firstElement] > numbers[secondElement] || !isAscending && numbers[firstElement] < numbers[secondElement]) {
            int temp = numbers[firstElement];
            numbers[firstElement] = numbers[secondElement];
            numbers[secondElement] = temp;
        }
    }
    
    @Override
    public String toString() {
    	if (isAscending)
    		return "Checking if numbers[" + firstElement + "] > numbers[" + secondElement + "] .. " + numbers[firstElement] +">" + numbers[secondElement];
    	else
    		return "Checking if numbers[" + firstElement + "] < numbers[" + secondElement + "] .. " + numbers[firstElement] +"<" + numbers[secondElement];
    }
}
