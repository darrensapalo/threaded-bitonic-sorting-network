public class Switch implements Runnable {

	private boolean asc;
	private final int index;
	private final int first;
	private final int last;
	private final int[] numbers;

	public Switch(boolean asc, int index, int first, int last, int[] numbers) {
		this.asc = asc;
		this.index = index;
		this.first = first;
		this.last = last;
		this.numbers = numbers;
		System.out.println("Created thread #" + index + " " + first + " " + last + (asc ? " asc" : " desc"));
	}

	/**
	 * Switches the two elements if the first element is larger then the last.
	 *
	 * @return true if there is a switch, false if otherwise
	 */
	@Override
	public void run() {
		if (asc && numbers[first] > numbers[last] || !asc && numbers[first] < numbers[last]) {
			int temp = numbers[first];
			numbers[first] = numbers[last];
			numbers[last] = temp;
			System.out.println("Thread #" + index + " performed switching");
		}
	}
}
