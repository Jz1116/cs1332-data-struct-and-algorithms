import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * Your implementation of an AVL.
 *
 * @author Zhen Jiang
 * @version 1.0
 * @userid zjiang330
 * @GTID 903402987
 *
 * Collaborators: n/a
 *
 * Resources: n/a
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }

        for (T datum : data) {
            if (data.contains(null)) {
                throw new IllegalArgumentException("The element in data"
                                                    + "is null");
            }
            add(datum);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }

        root = addHelper(root, data);
    }

    /**
     *
     * @param node refers to the current node
     * @param data refers to the data that needs to be added
     * @return the root node (point reinforcement)
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        } else {
            if (node.getData().equals(data)) {
                return node;
            } else if (data.compareTo(node.getData()) > 0) {
                node.setRight(addHelper(node.getRight(), data));
            } else {
                node.setLeft(addHelper(node.getLeft(), data));
            }
            return updateAndBalance(node);
        }
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }

        AVLNode<T> dummyNode = new AVLNode<T>(null);
        root = removeHelper(root, data, dummyNode);
        return dummyNode.getData();

    }

    /**
     *
     * @param node refers to the current node
     * @param data refers to the node that needs to be removed
     * @param dummyNode a placeholder for the removed data in the AVL
     * @return the root node (point reinforcement)
     */
    private AVLNode<T> removeHelper(AVLNode<T> node, T data,
                                    AVLNode<T> dummyNode) {
        if (node == null) {
            throw new NoSuchElementException("The data is not found.");
        } else {
            if (data.compareTo(node.getData()) > 0) {
                node.setRight(removeHelper(node.getRight(), data, dummyNode));
                return updateAndBalance(node);
            } else if (data.compareTo(node.getData()) < 0) {
                node.setLeft(removeHelper(node.getLeft(), data, dummyNode));
                return updateAndBalance(node);
            } else {
                dummyNode.setData(node.getData());
                size--;
                if (node.getLeft() == null && node.getRight() == null) {
                    node = null;
                    return node;
                } else if (node.getLeft() == null) {
                    return updateAndBalance(node.getRight());
                } else if (node.getRight() == null) {
                    return updateAndBalance(node.getLeft());
                } else {
                    AVLNode<T> temp = new AVLNode<T>(null);
                    node.setRight(getSuccessor(node.getRight(), temp));
                    node.setData(temp.getData());
                    return updateAndBalance(node);
                }
            }
        }
    }

    /**
     *
     * @param node refers to the current node;
     * @param temp a placeholder for the successor in the AVL
     * @return the current node (point reinforcement)
     */
    private AVLNode<T> getSuccessor(AVLNode<T> node, AVLNode<T> temp) {
        if (node.getLeft() == null) {
            temp.setData(node.getData());
            if (node.getRight() == null) {
                return null;
            } else {
                return updateAndBalance(node.getRight());
            }
        } else {
            node.setLeft(getSuccessor(node.getLeft(), temp));
            return updateAndBalance(node);
        }
    }

    /**
     *
     * @param a refers to the node that has the balance factor of -2
     * @return the node after the left rotation
     */
    private AVLNode<T> leftRotate(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        update(a);
        update(b);
        return b;
    }

    /**
     *
     * @param a refers to the node that has the balance factor of 2
     * @return the node after the right rotation
     */
    private AVLNode<T> rightRotate(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft();
        a.setLeft(b.getRight());
        b.setRight(a);
        update(a);
        update(b);
        return b;
    }

    /**
     *
     * @param a refers to the node that has the balance factor of 2
     * @return the node after the left right rotation
     */
    private AVLNode<T> leftRightRotate(AVLNode<T> a) {
        a.setLeft(leftRotate(a.getLeft()));
        return rightRotate(a);
    }

    /**
     *
     * @param a refers to the node that has the balance factor of -2
     * @return the node after the right left rotation
     */
    private AVLNode<T> rightLeftRotate(AVLNode<T> a) {
        a.setRight(rightRotate(a.getRight()));
        return leftRotate(a);
    }

    /**
     *
     * @param node refers to the current node
     */
    private void update(AVLNode<T> node) {
        int left = 0;
        int right = 0;
        if (node.getLeft() == null) {
            left = -1;
        } else {
            left = node.getLeft().getHeight();
        }

        if (node.getRight() == null) {
            right = -1;
        } else {
            right = node.getRight().getHeight();
        }

        int height = Math.max(left, right) + 1;
        node.setHeight(height);
        node.setBalanceFactor(left - right);
    }

    /**
     *
     * @param node refers to the current node
     * @return the node after balancing
     */
    private AVLNode<T> updateAndBalance(AVLNode<T> node) {
        update(node);
        int balanceFactor = node.getBalanceFactor();
        if (balanceFactor >= -1 && balanceFactor <= 1) {
            return node;
        } else if (balanceFactor == 2) {
            if (node.getLeft().getBalanceFactor() == 1
                || node.getLeft().getBalanceFactor() == 0) {
                return rightRotate(node);
            } else {
                return leftRightRotate(node);
            }
        } else {
            if (node.getRight().getBalanceFactor() == -1
                || node.getRight().getBalanceFactor() == 0) {
                return leftRotate(node);
            } else {
                return rightLeftRotate(node);
            }
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        return getHelper(data, root);
    }


    /**
     *
     * @param data refers to the data that we need
     * @param node refers to the current node
     * @return the data in the AVL
     */
    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("The data is "
                                            + "not in the tree");
        } else {
            if (node.getData().compareTo(data) == 0) {
                return node.getData();
            } else if (data.compareTo(node.getData()) > 0) {
                T value = getHelper(data, node.getRight());
                return value;
            } else {
                T value = getHelper(data, node.getLeft());
                return value;
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }

        return containsHelper(data, root);
    }

    /**
     *
     * @param data refers to the data we need
     * @param node refers to the current node
     * @return true or false (whether data is in the AVL)
     */
    private boolean containsHelper(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        } else {
            if (data.compareTo(node.getData()) == 0) {
                return true;
            } else if (data.compareTo(node.getData()) > 0) {
                boolean value = containsHelper(data, node.getRight());
                return value;
            } else {
                boolean value = containsHelper(data, node.getLeft());
                return value;
            }
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }
    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        return predecessorHelper(root, data);
    }

    /**
     *
     * @param node refers to the current node
     * @param data refers to the data (given)
     * @return the predecessor of the data
     */
    private T predecessorHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("The data is not in the tree.");
        } else if (data.compareTo(node.getData()) > 0) {
            T value = predecessorHelper(node.getRight(), data);
            if (value == null) {
                return node.getData();
            } else {
                return value;
            }
        } else if (data.compareTo(node.getData()) < 0) {
            T value = predecessorHelper(node.getLeft(), data);
            return value;
        } else {
            if (node.getLeft() != null) {
                return getPredecessor(node.getLeft());
            } else {
                return null;
            }
        }
    }

    /**
     *
     * @param node refers to the current node
     * @return the predecessor
     */
    private T getPredecessor(AVLNode<T> node) {
        if (node.getRight() == null) {
            return node.getData();
        } else {
            T value = getPredecessor(node.getRight());
            return value;
        }
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                50
     *              /    \
     *            25      75
     *           /  \     / \
     *          12   37  70  80
     *         /  \    \      \
     *        10  15    40    85
     *           /
     *          13
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("The number of elements to "
                                                + "return is less than 0");
        }
        if (k > size) {
            throw new IllegalArgumentException("The number of elements to "
                                            + "return is greater than size");
        }
        List<T> kList = new ArrayList<>();
        if (k == 0) {
            return kList;
        }
        inOrderTraversal(root, kList, k);
        return kList;
    }

    /**
     *
     * @param node refers to the current
     * @param kList refers to the list to add smallest elements
     * @param k refers to the number of smallest elements to return
     */
    private void inOrderTraversal(AVLNode<T> node,
                                  List<T> kList, int k) {
        if (node != null) {
            inOrderTraversal(node.getLeft(), kList, k);
            if (kList.size() < k) {
                kList.add(node.getData());
            }
            if (kList.size() == k) {
                return;
            }
            inOrderTraversal(node.getRight(), kList, k);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
