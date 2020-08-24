
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }

        for (T datum : data) {
            if (datum == null) {
                throw new IllegalArgumentException("The element "
                                            +  "of the data is null");
            }
            add(datum);
        }

    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
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
     * @param data refers to the data to add
     * @return the root node (point reinforcement)
     */
    private BSTNode<T> addHelper(BSTNode<T> node, T data) {

        if (node == null) {
            size++;
            return new BSTNode<>(data);
        } else {
            if (data.compareTo(node.getData()) > 0) {
                node.setRight(addHelper(node.getRight(), data));
                return node;
            } else if (node.getData().equals(data)) {
                return node;
            } else {
                node.setLeft(addHelper(node.getLeft(), data));
                return node;
            }
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
     * replace it with its
     * child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("The data is "
                                            + "not in the tree");
        }
        BSTNode<T> temp = new BSTNode<>(null);
        root = removeHelper(root, data, temp);
        return temp.getData();
    }

    /**
     *
     * @param node refers to the current node
     * @param data refers to the data to remove
     * @param dummyNode the placeholder for the data to be removed
     * @return the root node (point reinforcement)
     */
    private BSTNode<T> removeHelper(BSTNode<T> node, T data,
                                    BSTNode<T> dummyNode) {
        if (node == null) {
            throw new NoSuchElementException("The data is in the tree");
        } else {
            if (node.getData().equals(data)) {
                dummyNode.setData(node.getData());
                size--;
                if (node.getLeft() == null && node.getRight() == null) {
                    node = null;
                    return node;
                } else if (node.getRight() == null) {
                    return node.getLeft();
                } else if (node.getLeft() == null) {
                    return node.getRight();
                } else {
                    BSTNode<T> temp2 = new BSTNode<>(null);
                    node.setLeft(getPredecessor(node.getLeft(), temp2));
                    node.setData(temp2.getData());
                    return node;
                }
            } else if (data.compareTo(node.getData()) > 0) {
                node.setRight(removeHelper(node.getRight(), data, dummyNode));
            } else if (data.compareTo(node.getData()) < 0) {
                node.setLeft(removeHelper(node.getLeft(), data, dummyNode));
            }
            return node;
        }
    }

    /**
     *
     * @param curr refers to the current node
     * @param temp placeholder to get the predecessor
     * @return the current node (point reinforcement)
     */
    private BSTNode<T> getPredecessor(BSTNode<T> curr, BSTNode<T> temp) {
        if (curr.getRight() == null) {
            temp.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(getPredecessor(curr.getRight(), temp));
            return curr;
        }
    }


    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
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

        return getHelper(root, data);
    }

    /**
     *
     * @param node refers to the current node
     * @param data refers to the node that I want to get
     * @return the data in the tree equal to the parameter
     */
    private T getHelper(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("The data is not "
                                               + "in the tree");
        } else {
            if (data.equals(node.getData())) {
                return node.getData();
            } else if (data.compareTo(node.getData()) > 0) {
                T value = getHelper(node.getRight(), data);
                return value;
            } else {
                T value = getHelper(node.getLeft(), data);
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
     * Must be O(log n) for a balanced tree and O(n) for worst case.
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

        return containsHelper(root, data);
    }

    /**
     *
     * @param node refers to the current node
     * @param data the data we search for in the tree
     * @return true if data is in the tree, false otherwise
     */
    private boolean containsHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        } else {
            if (data.equals(node.getData())) {
                return true;
            } else if (data.compareTo(node.getData()) > 0) {
                boolean value = containsHelper(node.getRight(), data);
                return value;
            } else {
                boolean value = containsHelper(node.getLeft(), data);
                return value;
            }
        }
    }


    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorderHelper(root, list);
        return list;
    }

    /**
     *
     * @param node refers to the current node
     * @param temp refers to the list to add the preorder traversal
     */
    private void preorderHelper(BSTNode<T> node, List<T> temp) {
        if (node != null) {
            temp.add(node.getData());
            preorderHelper(node.getLeft(), temp);
            preorderHelper(node.getRight(), temp);
        }
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    /**
     *
     * @param node refers to the current node
     * @param temp refers to the list to add inorder traversal
     */
    private void inorderHelper(BSTNode<T> node, List<T> temp) {
        if (node != null) {
            inorderHelper(node.getLeft(), temp);
            temp.add(node.getData());
            inorderHelper(node.getRight(), temp);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;

    }

    /**
     *
     * @param node refers to the current node
     * @param temp refers to the list to add postorder traversal
     */
    private void postorderHelper(BSTNode<T> node, List<T> temp) {
        if (node != null) {
            postorderHelper(node.getLeft(), temp);
            postorderHelper(node.getRight(), temp);
            temp.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>();
        if (size == 0) {
            return list;
        }
        BSTNode<T> node = root;
        queue.add(node);
        list.add(node.getData());

        while (!(queue.isEmpty())) {
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
                list.add(node.getLeft().getData());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
                list.add(node.getRight().getData());
            }
            queue.remove();
            node = queue.peek();
        }

        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     *
     * @param node refers to the current node
     * @return the height of the root
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return Math.max(heightHelper(node.getLeft()),
                    heightHelper(node.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     **
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *                 50
     *             /        \
     *           25         75
     *         /    \
     *        12    37
     *       /  \    \
     *     10   15   40
     *         /
     *       13
     * findPathBetween(13, 40) should return the list [13, 15, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        LinkedList<T> list = new LinkedList<>();
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Either data1 or data2 is null");
        }
        if (!(contains(data1)) || !((contains(data2)))) {
            throw new NoSuchElementException("Either data1 or data2 "
                                                    + "is not in the tree");
        }

        BSTNode<T> ancestor = findAncestor(root, data1, data2);
        list.add(ancestor.getData());
        traversalData1(data1, ancestor, list);
        traversalData2(data2, ancestor, list);

        return list;
    }

    /**
     *
     * @param node refers to the current node
     * @param data1 given information
     * @param data2 given information
     * @return the deepest ancestor of data1 and data2
     */
    private BSTNode<T> findAncestor(BSTNode<T> node, T data1, T data2) {
        if (data1.equals(node) || data2.equals(node)) {
            return node;
        }

        if (data1.compareTo(data2) < 0) {
            if (data1.compareTo(node.getData()) <= 0
                    && data2.compareTo(node.getData()) >= 0) {
                return node;
            } else {
                if (data1.compareTo(node.getData()) > 0
                        && data2.compareTo(node.getData()) > 0) {
                    return findAncestor(node.getRight(), data1, data2);
                } else if (data1.compareTo(node.getData()) < 0
                        && data2.compareTo(node.getData()) < 0) {
                    return findAncestor(node.getLeft(), data1, data2);
                } else {
                    return root;
                }
            }
        } else {
            if (data2.compareTo(node.getData()) <= 0
                    && data1.compareTo(node.getData()) >= 0) {
                return node;
            } else {
                if (data1.compareTo(node.getData()) > 0
                        && data2.compareTo(node.getData()) > 0) {
                    return findAncestor(node.getRight(), data1, data2);
                } else if (data1.compareTo(node.getData()) < 0
                        && data2.compareTo(node.getData()) < 0) {
                    return findAncestor(node.getLeft(), data1, data2);
                } else {
                    return root;
                }
            }
        }
    }

    /**
     *
     * @param data refers to data1
     * @param node refers to the current node
     * @param temp refers to the list to add the path from ancestor to data1
     */
    private void traversalData1(T data, BSTNode<T> node, LinkedList<T> temp) {
        if (data.equals(node.getData())) {
            return;
        } else {
            if (data.compareTo(node.getData()) > 0) {
                node = node.getRight();
                temp.addFirst(node.getData());
                traversalData1(data, node, temp);
            } else {
                node = node.getLeft();
                temp.addFirst(node.getData());
                traversalData1(data, node, temp);
            }
        }
    }

    /**
     *
     * @param data refers to data2
     * @param node refers to the current node
     * @param temp refers to the list to add the path from ancestor to data2
     */
    private void traversalData2(T data, BSTNode<T> node, LinkedList<T> temp) {
        if (data.equals(node.getData())) {
            return;
        } else {
            if (data.compareTo(node.getData()) > 0) {
                node = node.getRight();
                temp.addLast(node.getData());
                traversalData2(data, node, temp);
            } else {
                node = node.getLeft();
                temp.addLast(node.getData());
                traversalData2(data, node, temp);
            }
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
    public BSTNode<T> getRoot() {
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
