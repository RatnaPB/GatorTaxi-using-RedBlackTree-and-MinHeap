import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class implementing Gator Taxi
 */
public class gatorTaxi {

    /**
     * Object of red-black tree
     */
    static redBlackTree<redBlackTreeRide> redBlackTree = new redBlackTree<>();

    /**
     * Object of min heap
     */
    static minHeap minHeap = new minHeap(2000);

    /**
     * Method to insert ride into red-black tree and min heap
     *
     * @param rideNumber   ride number
     * @param rideCost     cost of ride
     * @param tripDuration trip duration of ride
     * @return error message if any
     */
    public static String insert(int rideNumber, int rideCost, int tripDuration) {
        redBlackTreeRide ride = new redBlackTreeRide(rideNumber, rideCost, tripDuration);
        redBlackTreeNode<redBlackTreeRide> redBlackTreeNode = redBlackTree.insert(ride);

        if (redBlackTreeNode == null) {
            return "Duplicate RideNumber";
        }

        minHeapRide minHeapRide = new minHeapRide(rideNumber, rideCost, tripDuration);
        minHeapNode minHeapNode = minHeap.insert(minHeapRide);

        if (minHeapNode == null) {
            return "MinHeap size exceeded";
        }

        ride.minHeapNodePointer = minHeapNode;
        minHeapRide.redBlackTreeNodePointer = redBlackTreeNode;
        return "";
    }

    /**
     * Method to return range of rides between rideNumber1 and rideNumber2
     *
     * @param rideNumber1 start limit of range
     * @param rideNumber2 end limit of range
     * @return list of rides in range
     */
    public static String getRidesInRange(int rideNumber1, int rideNumber2) {
        redBlackTreeRide[] results = new redBlackTreeRide[2000];

        int size = redBlackTree.printInRange(rideNumber1, rideNumber2, results);

        if (size == 0) {
            return "(0,0,0)";
        }

        StringBuilder result = new StringBuilder();

        int index = 0;
        while (index != (size - 1)) {
            result.append(results[index].toString()).append(",");
            index++;
        }

        return result.toString() + results[size - 1];
    }

    /**
     * Method to return ride details
     *
     * @param rideNumber given ride number
     * @return ride details
     */
    public static String getRidesInRange(int rideNumber) {
        redBlackTreeRide redBlackTreeRide = redBlackTree.find(rideNumber);
        return redBlackTreeRide != null ? redBlackTreeRide.toString() : "(0,0,0)";
    }

    /**
     * Method to return next active ride with the lowest cost
     *
     * @return lowest cost ride
     */
    public static String getNextRide() {
        minHeapRide lowestCostRide = minHeap.deleteMin();
        if (lowestCostRide == null) {
            return "No active ride requests";
        }
        redBlackTree.deleteNode(lowestCostRide.redBlackTreeNodePointer);
        return lowestCostRide.toString();
    }

    /**
     * Method to cancel a ride
     *
     * @param rideNumber ride number of ride to cancel
     */
    public static void cancelRide(int rideNumber) {
        redBlackTreeRide deletedRide = redBlackTree.deleteData(rideNumber);
        if (deletedRide != null) {
            minHeap.arbitraryDelete(deletedRide.minHeapNodePointer);
        }
    }

    /**
     * Method to update trip duration of a ride
     *
     * @param rideNumber       ride number of ride to update
     * @param new_tripDuration new trip duration
     */
    public static void updateTrip(int rideNumber, int new_tripDuration) {
        redBlackTreeRide findRide = redBlackTree.find(rideNumber);

        if (new_tripDuration <= findRide.tripDuration) {
            minHeapNode minFindRide = findRide.minHeapNodePointer;
            findRide.tripDuration = new_tripDuration;
            minFindRide.data.tripDuration = new_tripDuration;
        } else if (new_tripDuration <= 2 * findRide.tripDuration) {
            cancelRide(rideNumber);
            insert(rideNumber, findRide.rideCost + 10, new_tripDuration);
        } else {
            cancelRide(rideNumber);
        }
    }

    /**
     * Start of the program
     *
     * @param args input file
     */
    public static void main(String[] args) {
        String outputFileName = "output.txt";

        if (args.length != 1) {
            System.out.println("Usage: java Main <input_file>");
            return;
        }

        try (FileReader fileReader = new FileReader(args[0]); FileWriter fileWriter = new FileWriter(outputFileName); BufferedReader reader = new BufferedReader(fileReader); BufferedWriter writer = new BufferedWriter(fileWriter)) {

            String line;
            boolean isFirst = true;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\(");
                String methodName = tokens[0];
                String[] argsTokens = tokens[1].split("\\)");
                String[] methodArgs = new String[0];

                if (argsTokens.length > 0 && !argsTokens[0].isEmpty()) {
                    methodArgs = argsTokens[0].split(",");
                }

                String output = "";

                switch (methodName) {
                    case "Insert":
                        if (methodArgs.length != 3) {
                            System.out.println("Incorrect number of parameters in Insert");
                            break;
                        }
                        output = insert(Integer.parseInt(methodArgs[0]), Integer.parseInt(methodArgs[1]), Integer.parseInt(methodArgs[2]));
                        break;
                    case "Print":
                        if (methodArgs.length == 1) {
                            output = getRidesInRange(Integer.parseInt(methodArgs[0]));
                        } else if (methodArgs.length == 2) {
                            output = getRidesInRange(Integer.parseInt(methodArgs[0]), Integer.parseInt(methodArgs[1]));
                        } else {
                            System.out.println("Incorrect number of parameters in Print");
                            break;
                        }
                        break;
                    case "GetNextRide":
                        if (methodArgs.length != 0) {
                            System.out.println("Incorrect number of parameters in GetNextRide");
                            break;
                        }
                        output = getNextRide();
                        break;
                    case "CancelRide":
                        if (methodArgs.length != 1) {
                            System.out.println("Incorrect number of parameters in CancelRide");
                            break;
                        }
                        cancelRide(Integer.parseInt(methodArgs[0]));
                        break;
                    case "UpdateTrip":
                        if (methodArgs.length != 2) {
                            System.out.println("Incorrect number of parameters in UpdateTrip");
                            break;
                        }
                        updateTrip(Integer.parseInt(methodArgs[0]), Integer.parseInt(methodArgs[1]));
                        break;
                    default:
                        System.out.println("Invalid operation name");
                        break;
                }

                writer.write((isFirst || output.isEmpty()) ? output : ("\n" + output));
                isFirst = output.isEmpty() && isFirst;

                if (output.contains("Duplicate RideNumber")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer");
            e.printStackTrace();
        }
    }
}