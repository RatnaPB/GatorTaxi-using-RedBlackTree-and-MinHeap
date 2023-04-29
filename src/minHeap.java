/**
 * Min Heap Node
 */
class minHeapNode {

    /**
     * Data in Min Heap Node
     */
    minHeapRide data;

    /**
     * Represents position of node in min heap array
     */
    int positionIndex;

    /**
     * Constructor of minHeapNode
     *
     * @param data          of type minHeapRide
     * @param positionIndex of type int
     */
    public minHeapNode(minHeapRide data, int positionIndex) {
        this.data = data;
        this.positionIndex = positionIndex;
    }
}

/**
 * Class implementing min heap
 */
public class minHeap {

    /**
     * Heap array to store min heap structure
     */
    private final minHeapNode[] Heap;

    /**
     * Represents min heap's current size
     */
    private int size;

    /**
     * Maximum size limit of min heap
     */
    private final int maximumSize;

    /**
     * Starting position index of min heap
     */
    private static final int startPositionIndex = 1;

    /**
     * Min Heap Constructor
     *
     * @param maximumSize max size of the min heap
     */
    public minHeap(int maximumSize) {
        this.maximumSize = maximumSize;
        this.size = 0;

        Heap = new minHeapNode[maximumSize + 1];
        Heap[0] = new minHeapNode(new minHeapRide(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE), 0);
    }

    /**
     * Method to get node's parent's position index in heap array
     *
     * @param positionIndex position index of node in heap array
     * @return position index of parent in heap array
     */
    private int getParentPositionIndex(int positionIndex) {
        return positionIndex / 2;
    }

    /**
     * Method to get node's left child's position index in heap array
     *
     * @param positionIndex position index of node in heap array
     * @return position index of left child in heap array
     */
    private int getLeftChildPositionIndex(int positionIndex) {
        return (2 * positionIndex);
    }

    /**
     * Method to get node's right child's position index in heap array
     *
     * @param positionIndex position index of node in heap array
     * @return position index of right child in heap array
     */
    private int getRightChildPositionIndex(int positionIndex) {
        return (2 * positionIndex) + 1;
    }

    /**
     * Method to check whether the node is a leaf in min heap
     *
     * @param positionIndex position index of node in heap array
     * @return true/false on whether the node is a leaf in min heap
     */
    private boolean isLeaf(int positionIndex) {
        return positionIndex > (size / 2);
    }

    /**
     * Swaps positions of two nodes within min heap
     *
     * @param positionIndex1 position index of node to be swapped
     * @param positionIndex2 position index of node to be swapped with
     */
    private void swap(int positionIndex1, int positionIndex2) {
        minHeapNode temp;
        temp = Heap[positionIndex1];

        Heap[positionIndex1] = Heap[positionIndex2];
        Heap[positionIndex2] = temp;

        Heap[positionIndex1].positionIndex = positionIndex1;
        Heap[positionIndex2].positionIndex = positionIndex2;
    }

    /**
     * Method to fix min heap with heapify
     *
     * @param positionIndex position index of node in heap array to start heapify from
     */
    private void minHeapify(int positionIndex) {
        if (!isLeaf(positionIndex)) {
            int swapPositionIndex;

            if (getRightChildPositionIndex(positionIndex) <= size)
                swapPositionIndex = (Heap[getLeftChildPositionIndex(positionIndex)].data.compareTo(Heap[getRightChildPositionIndex(positionIndex)].data) < 0) ? getLeftChildPositionIndex(positionIndex) : getRightChildPositionIndex(positionIndex);
            else swapPositionIndex = getLeftChildPositionIndex(positionIndex);

            if ((Heap[positionIndex].data.compareTo(Heap[getLeftChildPositionIndex(positionIndex)].data) > 0) || (Heap[positionIndex].data.compareTo(Heap[getRightChildPositionIndex(positionIndex)].data) > 0)) {
                swap(positionIndex, swapPositionIndex);
                minHeapify(swapPositionIndex);
            }
        }
    }

    /**
     * Method to insert data into min heap
     *
     * @param minHeapRide data to be inserted
     * @return pointer to node in which data is inserted
     */
    public minHeapNode insert(minHeapRide minHeapRide) {
        if (size >= maximumSize) {
            return null;
        }

        minHeapNode node = new minHeapNode(minHeapRide, ++size);

        Heap[size] = node;
        int current = size;

        while (Heap[current].data.compareTo(Heap[getParentPositionIndex(current)].data) < 0) {
            swap(current, getParentPositionIndex(current));
            current = getParentPositionIndex(current);
        }

        return node;
    }

    /**
     * Delete the minimum, i.e., the root node of min heap
     *
     * @return the minimum data value from min heap
     */
    public minHeapRide deleteMin() {
        if (size == 0) {
            return null;
        }

        minHeapNode popped = Heap[startPositionIndex];
        Heap[startPositionIndex] = Heap[size--];
        Heap[startPositionIndex].positionIndex = startPositionIndex;
        minHeapify(startPositionIndex);

        return popped.data;
    }

    /**
     * Delete an arbitrary node from min heap
     *
     * @param minHeapNode pointer to the node to be deleted
     */
    public void arbitraryDelete(minHeapNode minHeapNode) {
        if (size == 0) {
            return;
        }

        int positionIndex = minHeapNode.positionIndex;
        Heap[positionIndex] = Heap[size--];
        Heap[positionIndex].positionIndex = positionIndex;
        minHeapify(positionIndex);
    }
}
