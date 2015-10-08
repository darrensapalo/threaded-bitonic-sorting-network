package sort.network;

import sort.Bitonic;
import sort.task.Comparator;
import sort.task.Gate;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Darren on 9/19/2015.
 */
public class Network implements Serializable{
    private LinkedList<Gate> network;

    public Network(int SIZE) {
        network = new LinkedList<Gate>();

        int HALF = SIZE / 2;

        if ((SIZE & (SIZE - 1)) != 0)
            throw new RuntimeException("Cannot have data size which is not a power of two!");

        int gate = 1;
        int region = 1;

        /* Loop for the whole bitonic sorting algorithm. Begin at half the size of the data elements. */
        for (int i = HALF; i >= 1; i /= 2) {
            if (Bitonic.VERBOSE_COMMENTS)
                System.out.println("Phase " + region++);

            int alternate = HALF / i;    //Used for toggling direction whether ascending or descending

			/* j represents the number of comparators in this blocking gate. */
            for (int j = i; j < SIZE; j *= 2) {
                if (Bitonic.VERBOSE_COMMENTS)
                    System.out.println("Gate " + (gate++) + " uses " + j + " comparators");

                int inverse = HALF / j;

                Gate currentGate = new Gate();

                /* Generate all the comparators that will block on this gate */
                for (int id = 0; id < HALF; id++) {
                    int firstElement = id % inverse + id / inverse * inverse * 2;
                    int secondElement = firstElement + inverse;

                    boolean isAscending = (id / alternate) % 2 == 0;

                    /* Submit the comparator task */
                    currentGate.addTask(new Comparator(isAscending, id, firstElement, secondElement));
                }
                network.add(currentGate);
            }
        }
    }

    public int size(){
        return network.size();
    }

    public Gate getGate(int i){
        return network.get(i);
    }

	public void display(int i) {
		Gate gate = getGate(i);
		gate.display();
	}


}
