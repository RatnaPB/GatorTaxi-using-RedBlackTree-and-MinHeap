/**
 * Enum for Red-Black Tree redBlackTreeNode colour
 */
enum colour {
    Red, Black
}

/**
 * Red-Black Tree Node
 *
 * @param <T>
 */
class redBlackTreeNode<T extends Comparable<Object>> {

    /**
     * Represents data in the red-black tree node
     */
    public T data;

    /**
     * Represents parent of the red-black tree node
     */
    public redBlackTreeNode<T> parent;

    /**
     * Represents left child of the red-black tree node
     */
    public redBlackTreeNode<T> leftChild;

    /**
     * Represents right child of the red-black tree node
     */
    public redBlackTreeNode<T> rightChild;

    /**
     * Represents colour(Red, Black) of the red-black tree node
     */
    public colour colour;

    /**
     * Red-Black Tree Node constructor
     *
     * @param data       data present in node
     * @param parent     parent of the node
     * @param leftChild  left child of the node
     * @param rightChild right child of the node
     * @param colour     color of the node (Red, Black)
     */
    public redBlackTreeNode(T data, redBlackTreeNode<T> parent, redBlackTreeNode<T> leftChild, redBlackTreeNode<T> rightChild, colour colour) {
        this.data = data;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.colour = colour;
    }
}

/**
 * Class implementing red-black tree
 *
 * @param <T>
 */
public class redBlackTree<T extends Comparable<Object>> {
    private redBlackTreeNode<T> root;
    private final redBlackTreeNode<T> externalRedBlackTreeNode;

    /**
     * Red-black tree constructor
     */
    public redBlackTree() {
        externalRedBlackTreeNode = new redBlackTreeNode<>(null, null, null, null, colour.Black);
        root = externalRedBlackTreeNode;
    }

    /**
     * Method to help finding red black tree data
     *
     * @param redBlackTreeNode root of the tree/subtree to search from
     * @param value            value to search
     * @return data to be found
     */
    private T findHelper(redBlackTreeNode<T> redBlackTreeNode, Object value) {
        if (redBlackTreeNode == externalRedBlackTreeNode || redBlackTreeNode.data.compareTo(value) == 0) {
            return redBlackTreeNode.data;
        }

        if (redBlackTreeNode.data.compareTo(value) > 0) {
            return findHelper(redBlackTreeNode.leftChild, value);
        }
        return findHelper(redBlackTreeNode.rightChild, value);
    }

    int size;

    /**
     * Method to get data values between value1 and value2 in red black tree
     *
     * @param value1  start limit of the range
     * @param value2  end limit of the range
     * @param results data in the range
     * @return size of data in the range
     */
    public int printInRange(Object value1, Object value2, T[] results) {
        size = 0;
        printInRangeHelper(root, value1, value2, results);
        return size;
    }

    /**
     * Method to help get data values between value1 and value2 in red black tree
     *
     * @param redBlackTreeNode root of tree/subtree to search from
     * @param value1           start limit of the range
     * @param value2           end limit of the range
     * @param results          data in the range
     */
    private void printInRangeHelper(redBlackTreeNode<T> redBlackTreeNode, Object value1, Object value2, T[] results) {
        if (redBlackTreeNode == externalRedBlackTreeNode) {
            return;
        }
        if (redBlackTreeNode.data.compareTo(value1) > 0 && redBlackTreeNode.data.compareTo(value2) < 0) {
            printInRangeHelper(redBlackTreeNode.leftChild, value1, value2, results);
            results[size++] = redBlackTreeNode.data;
            printInRangeHelper(redBlackTreeNode.rightChild, value1, value2, results);
        } else if (redBlackTreeNode.data.compareTo(value1) == 0) {
            results[size++] = redBlackTreeNode.data;
            printInRangeHelper(redBlackTreeNode.rightChild, value1, value2, results);
        } else if (redBlackTreeNode.data.compareTo(value2) == 0) {
            printInRangeHelper(redBlackTreeNode.leftChild, value1, value2, results);
            results[size++] = redBlackTreeNode.data;
        } else if (redBlackTreeNode.data.compareTo(value1) < 0) {
            printInRangeHelper(redBlackTreeNode.rightChild, value1, value2, results);
        } else {
            printInRangeHelper(redBlackTreeNode.leftChild, value1, value2, results);
        }
    }

    /**
     * Method to fix red black tree after deletion
     *
     * @param redBlackTreeNode node to be refactored in red-black tree
     */
    private void refactorDelete(redBlackTreeNode<T> redBlackTreeNode) {
        redBlackTreeNode<T> siblingRedBlackTreeNode;
        while (redBlackTreeNode != root && redBlackTreeNode.colour == colour.Black) {
            if (redBlackTreeNode == redBlackTreeNode.parent.leftChild) {
                siblingRedBlackTreeNode = redBlackTreeNode.parent.rightChild;
                if (siblingRedBlackTreeNode.colour == colour.Red) {
                    siblingRedBlackTreeNode.colour = colour.Black;
                    redBlackTreeNode.parent.colour = colour.Red;
                    rotateToLeft(redBlackTreeNode.parent);
                    siblingRedBlackTreeNode = redBlackTreeNode.parent.rightChild;
                }

                if (siblingRedBlackTreeNode.leftChild.colour == colour.Black && siblingRedBlackTreeNode.rightChild.colour == colour.Black) {
                    siblingRedBlackTreeNode.colour = colour.Red;
                    redBlackTreeNode = redBlackTreeNode.parent;
                } else {
                    if (siblingRedBlackTreeNode.rightChild.colour == colour.Black) {
                        siblingRedBlackTreeNode.leftChild.colour = colour.Black;
                        siblingRedBlackTreeNode.colour = colour.Red;
                        rotateToRight(siblingRedBlackTreeNode);
                        siblingRedBlackTreeNode = redBlackTreeNode.parent.rightChild;
                    }

                    siblingRedBlackTreeNode.colour = redBlackTreeNode.parent.colour;
                    redBlackTreeNode.parent.colour = colour.Black;
                    siblingRedBlackTreeNode.rightChild.colour = colour.Black;
                    rotateToLeft(redBlackTreeNode.parent);
                    redBlackTreeNode = root;
                }
            } else {
                siblingRedBlackTreeNode = redBlackTreeNode.parent.leftChild;
                if (siblingRedBlackTreeNode.colour == colour.Red) {
                    siblingRedBlackTreeNode.colour = colour.Black;
                    redBlackTreeNode.parent.colour = colour.Red;
                    rotateToRight(redBlackTreeNode.parent);
                    siblingRedBlackTreeNode = redBlackTreeNode.parent.leftChild;
                }

                if (siblingRedBlackTreeNode.rightChild.colour == colour.Black) {
                    siblingRedBlackTreeNode.colour = colour.Red;
                    redBlackTreeNode = redBlackTreeNode.parent;
                } else {
                    if (siblingRedBlackTreeNode.leftChild.colour == colour.Black) {
                        siblingRedBlackTreeNode.rightChild.colour = colour.Black;
                        siblingRedBlackTreeNode.colour = colour.Red;
                        rotateToLeft(siblingRedBlackTreeNode);
                        siblingRedBlackTreeNode = redBlackTreeNode.parent.leftChild;
                    }

                    siblingRedBlackTreeNode.colour = redBlackTreeNode.parent.colour;
                    redBlackTreeNode.parent.colour = colour.Black;
                    siblingRedBlackTreeNode.leftChild.colour = colour.Black;
                    rotateToRight(redBlackTreeNode.parent);
                    redBlackTreeNode = root;
                }
            }
        }
        redBlackTreeNode.colour = colour.Black;
    }

    /**
     * Method to replace red-black tree node with another node
     *
     * @param oldRedBlackTreeNode node to be replaced in red-black tree
     * @param newRedBlackTreeNode node to be replaced with
     */
    private void replaceNode(redBlackTreeNode<T> oldRedBlackTreeNode, redBlackTreeNode<T> newRedBlackTreeNode) {
        if (oldRedBlackTreeNode.parent == null) {
            root = newRedBlackTreeNode;
        } else if (oldRedBlackTreeNode == oldRedBlackTreeNode.parent.leftChild) {
            oldRedBlackTreeNode.parent.leftChild = newRedBlackTreeNode;
        } else {
            oldRedBlackTreeNode.parent.rightChild = newRedBlackTreeNode;
        }
        newRedBlackTreeNode.parent = oldRedBlackTreeNode.parent;
    }

    /**
     * Method to help delete red-black tree data
     *
     * @param redBlackTreeNode root of the tree/subtree to be deleted from
     * @param deleteValue      value to be deleted from red-black tree
     * @return the deleted data
     */
    private T deleteDataHelper(redBlackTreeNode<T> redBlackTreeNode, Object deleteValue) {
        redBlackTreeNode<T> deleteRedBlackTreeNode = externalRedBlackTreeNode;
        redBlackTreeNode<T> childRedBlackTreeNode, newRedBlackTreeNode;
        while (redBlackTreeNode != externalRedBlackTreeNode) {
            if (redBlackTreeNode.data.compareTo(deleteValue) == 0) {
                deleteRedBlackTreeNode = redBlackTreeNode;
            }

            if (redBlackTreeNode.data.compareTo(deleteValue) <= 0) {
                redBlackTreeNode = redBlackTreeNode.rightChild;
            } else {
                redBlackTreeNode = redBlackTreeNode.leftChild;
            }
        }

        if (deleteRedBlackTreeNode == externalRedBlackTreeNode) {
            return null;
        }

        newRedBlackTreeNode = deleteRedBlackTreeNode;
        colour newNodeColour = newRedBlackTreeNode.colour;
        if (deleteRedBlackTreeNode.leftChild == externalRedBlackTreeNode) {
            childRedBlackTreeNode = deleteRedBlackTreeNode.rightChild;
            replaceNode(deleteRedBlackTreeNode, deleteRedBlackTreeNode.rightChild);
        } else if (deleteRedBlackTreeNode.rightChild == externalRedBlackTreeNode) {
            childRedBlackTreeNode = deleteRedBlackTreeNode.leftChild;
            replaceNode(deleteRedBlackTreeNode, deleteRedBlackTreeNode.leftChild);
        } else {
            newRedBlackTreeNode = minimumOfSubTree(deleteRedBlackTreeNode.rightChild);
            newNodeColour = newRedBlackTreeNode.colour;
            childRedBlackTreeNode = newRedBlackTreeNode.rightChild;
            if (newRedBlackTreeNode.parent == deleteRedBlackTreeNode) {
                childRedBlackTreeNode.parent = newRedBlackTreeNode;
            } else {
                replaceNode(newRedBlackTreeNode, newRedBlackTreeNode.rightChild);
                newRedBlackTreeNode.rightChild = deleteRedBlackTreeNode.rightChild;
                newRedBlackTreeNode.rightChild.parent = newRedBlackTreeNode;
            }

            replaceNode(deleteRedBlackTreeNode, newRedBlackTreeNode);
            newRedBlackTreeNode.leftChild = deleteRedBlackTreeNode.leftChild;
            newRedBlackTreeNode.leftChild.parent = newRedBlackTreeNode;
            newRedBlackTreeNode.colour = deleteRedBlackTreeNode.colour;
        }
        if (newNodeColour == colour.Black) {
            refactorDelete(childRedBlackTreeNode);
        }

        return deleteRedBlackTreeNode.data;
    }

    /**
     * Method to delete node from red-black tree with pointer
     *
     * @param deleteRedBlackTreeNode node to be deleted from red-black tree
     */
    public void deleteNode(redBlackTreeNode<T> deleteRedBlackTreeNode) {
        redBlackTreeNode<T> childRedBlackTreeNode, newRedBlackTreeNode;

        if (deleteRedBlackTreeNode == externalRedBlackTreeNode) {
            return;
        }

        newRedBlackTreeNode = deleteRedBlackTreeNode;
        colour yOriginalColour = newRedBlackTreeNode.colour;
        if (deleteRedBlackTreeNode.leftChild == externalRedBlackTreeNode) {
            childRedBlackTreeNode = deleteRedBlackTreeNode.rightChild;
            replaceNode(deleteRedBlackTreeNode, deleteRedBlackTreeNode.rightChild);
        } else if (deleteRedBlackTreeNode.rightChild == externalRedBlackTreeNode) {
            childRedBlackTreeNode = deleteRedBlackTreeNode.leftChild;
            replaceNode(deleteRedBlackTreeNode, deleteRedBlackTreeNode.leftChild);
        } else {
            newRedBlackTreeNode = minimumOfSubTree(deleteRedBlackTreeNode.rightChild);
            yOriginalColour = newRedBlackTreeNode.colour;
            childRedBlackTreeNode = newRedBlackTreeNode.rightChild;
            if (newRedBlackTreeNode.parent == deleteRedBlackTreeNode) {
                childRedBlackTreeNode.parent = newRedBlackTreeNode;
            } else {
                replaceNode(newRedBlackTreeNode, newRedBlackTreeNode.rightChild);
                newRedBlackTreeNode.rightChild = deleteRedBlackTreeNode.rightChild;
                newRedBlackTreeNode.rightChild.parent = newRedBlackTreeNode;
            }

            replaceNode(deleteRedBlackTreeNode, newRedBlackTreeNode);
            newRedBlackTreeNode.leftChild = deleteRedBlackTreeNode.leftChild;
            newRedBlackTreeNode.leftChild.parent = newRedBlackTreeNode;
            newRedBlackTreeNode.colour = deleteRedBlackTreeNode.colour;
        }
        if (yOriginalColour == colour.Black) {
            refactorDelete(childRedBlackTreeNode);
        }
    }

    /**
     * Method to fix red-black tree after inserting new node
     *
     * @param newRedBlackTreeNode node to be inserted in red-black tree
     */
    private void refactorInsert(redBlackTreeNode<T> newRedBlackTreeNode) {
        redBlackTreeNode<T> uncleRedBlackTreeNode;
        while (newRedBlackTreeNode.parent.colour == colour.Red) {
            if (newRedBlackTreeNode.parent == newRedBlackTreeNode.parent.parent.rightChild) {
                uncleRedBlackTreeNode = newRedBlackTreeNode.parent.parent.leftChild;
                if (uncleRedBlackTreeNode.colour == colour.Red) {
                    uncleRedBlackTreeNode.colour = colour.Black;
                    newRedBlackTreeNode.parent.colour = colour.Black;
                    newRedBlackTreeNode.parent.parent.colour = colour.Red;
                    newRedBlackTreeNode = newRedBlackTreeNode.parent.parent;
                } else {
                    if (newRedBlackTreeNode == newRedBlackTreeNode.parent.leftChild) {
                        newRedBlackTreeNode = newRedBlackTreeNode.parent;
                        rotateToRight(newRedBlackTreeNode);
                    }
                    newRedBlackTreeNode.parent.colour = colour.Black;
                    newRedBlackTreeNode.parent.parent.colour = colour.Red;
                    rotateToLeft(newRedBlackTreeNode.parent.parent);
                }
            } else {
                uncleRedBlackTreeNode = newRedBlackTreeNode.parent.parent.rightChild;

                if (uncleRedBlackTreeNode.colour == colour.Red) {
                    uncleRedBlackTreeNode.colour = colour.Black;
                    newRedBlackTreeNode.parent.colour = colour.Black;
                    newRedBlackTreeNode.parent.parent.colour = colour.Red;
                    newRedBlackTreeNode = newRedBlackTreeNode.parent.parent;
                } else {
                    if (newRedBlackTreeNode == newRedBlackTreeNode.parent.rightChild) {
                        newRedBlackTreeNode = newRedBlackTreeNode.parent;
                        rotateToLeft(newRedBlackTreeNode);
                    }
                    newRedBlackTreeNode.parent.colour = colour.Black;
                    newRedBlackTreeNode.parent.parent.colour = colour.Red;
                    rotateToRight(newRedBlackTreeNode.parent.parent);
                }
            }
            if (newRedBlackTreeNode == root) {
                break;
            }
        }
        root.colour = colour.Black;
    }

    /**
     * Method to find particular red-black node data
     *
     * @param value value to find in red-black tree
     * @return data of the found node
     */
    public T find(Object value) {
        return findHelper(this.root, value);
    }

    /**
     * Method to get minimum from a particular subtree
     *
     * @param redBlackTreeNode root of tree/subtree to find minimum from
     * @return minimum node from tree/subtree
     */
    private redBlackTreeNode<T> minimumOfSubTree(redBlackTreeNode<T> redBlackTreeNode) {
        while (redBlackTreeNode.leftChild != externalRedBlackTreeNode) {
            redBlackTreeNode = redBlackTreeNode.leftChild;
        }
        return redBlackTreeNode;
    }

    /**
     * Method to fix red-black tree by rotating left
     *
     * @param redBlackTreeNode node to be rotated left in red-black tree
     */
    private void rotateToLeft(redBlackTreeNode<T> redBlackTreeNode) {
        redBlackTreeNode<T> rightChild = redBlackTreeNode.rightChild;
        redBlackTreeNode.rightChild = rightChild.leftChild;
        if (rightChild.leftChild != externalRedBlackTreeNode) {
            rightChild.leftChild.parent = redBlackTreeNode;
        }
        rightChild.parent = redBlackTreeNode.parent;
        if (redBlackTreeNode.parent == null) {
            this.root = rightChild;
        } else if (redBlackTreeNode == redBlackTreeNode.parent.leftChild) {
            redBlackTreeNode.parent.leftChild = rightChild;
        } else {
            redBlackTreeNode.parent.rightChild = rightChild;
        }
        rightChild.leftChild = redBlackTreeNode;
        redBlackTreeNode.parent = rightChild;
    }

    /**
     * Method to fix red-black tree by rotating right
     *
     * @param redBlackTreeNode node to be rotated right in red-black tree
     */
    private void rotateToRight(redBlackTreeNode<T> redBlackTreeNode) {
        redBlackTreeNode<T> leftChild = redBlackTreeNode.leftChild;
        redBlackTreeNode.leftChild = leftChild.rightChild;
        if (leftChild.rightChild != externalRedBlackTreeNode) {
            leftChild.rightChild.parent = redBlackTreeNode;
        }
        leftChild.parent = redBlackTreeNode.parent;
        if (redBlackTreeNode.parent == null) {
            this.root = leftChild;
        } else if (redBlackTreeNode == redBlackTreeNode.parent.rightChild) {
            redBlackTreeNode.parent.rightChild = leftChild;
        } else {
            redBlackTreeNode.parent.leftChild = leftChild;
        }
        leftChild.rightChild = redBlackTreeNode;
        redBlackTreeNode.parent = leftChild;
    }

    /**
     * Method to insert data into red-black tree
     *
     * @param data data to be inserted in red-black tree
     * @return pointer to the node inserted in red-black tree
     */
    public redBlackTreeNode<T> insert(T data) {
        redBlackTreeNode<T> parent = null;
        redBlackTreeNode<T> temp = this.root;

        while (temp != externalRedBlackTreeNode) {
            parent = temp;
            if (data.compareTo(temp.data) < 0) {
                temp = temp.leftChild;
            } else {
                temp = temp.rightChild;
            }
        }

        redBlackTreeNode<T> newRedBlackTreeNode = new redBlackTreeNode<>(data, parent, externalRedBlackTreeNode, externalRedBlackTreeNode, colour.Red);

        if (parent == null) {
            root = newRedBlackTreeNode;
        } else if (newRedBlackTreeNode.data.compareTo(parent.data) < 0) {
            parent.leftChild = newRedBlackTreeNode;
        } else if (newRedBlackTreeNode.data.compareTo(parent.data) > 0) {
            parent.rightChild = newRedBlackTreeNode;
        } else {
            return null;
        }

        if (newRedBlackTreeNode.parent == null) {
            newRedBlackTreeNode.colour = colour.Black;
            return newRedBlackTreeNode;
        }

        if (newRedBlackTreeNode.parent.parent == null) {
            return newRedBlackTreeNode;
        }

        refactorInsert(newRedBlackTreeNode);

        return newRedBlackTreeNode;
    }

    /**
     * Method to delete data from red-black tree
     *
     * @param data data to be deleted from red-black tree
     * @return deleted data from red-black tree
     */
    public T deleteData(Object data) {
        return deleteDataHelper(this.root, data);
    }
}