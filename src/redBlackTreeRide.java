/**
 * data of red-black tree - ride
 */
public class redBlackTreeRide implements Comparable<Object> {

    /**
     * ride number
     */
    public int rideNumber;

    /**
     * cost of the ride
     */
    public int rideCost;

    /**
     * trip duration of the ride
     */
    public int tripDuration;

    /**
     * Pointer pointing to the corresponding min heap node
     */
    public minHeapNode minHeapNodePointer;

    /**
     * Constructor of reb black tree ride
     *
     * @param rideNumber   ride number
     * @param rideCost     cost of the ride
     * @param tripDuration trip duration of the ride
     */
    public redBlackTreeRide(int rideNumber, int rideCost, int tripDuration) {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
    }

    /**
     * Method to compare object with ride
     *
     * @param object the object to be compared.
     * @return -1 if less than, 0 if equal to and 1 if greater than object
     */
    @Override
    public int compareTo(Object object) {
        if (object instanceof redBlackTreeRide) {
            return Integer.compare(rideNumber, ((redBlackTreeRide)object).rideNumber);
        } else if (object instanceof Integer) {
            return Integer.compare(this.rideNumber, (Integer)object);
        }
        throw new UnsupportedOperationException("Cannot compare objects of different types.");
    }

    /**
     * Method to get string format of ride
     *
     * @return String format of ride
     */
    @Override
    public String toString() {
        return "(" + rideNumber + "," + rideCost + "," + tripDuration + ")";
    }
}