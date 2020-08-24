import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Zhen Jiang
 * @version 1.0
 * @userid zjiang330
 * @GTID 903402987
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }

        int length = data.size();
        length = 2 * length + 1;
        Comparable[] temp = new Comparable[length];
        backingArray = (T[]) temp;
        size = data.size();
        for (int i = 0; i < size; i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("The data is null");
            }
            backingArray[i + 1] = data.get(i);
        }

        for (int i = size / 2; i >= 1; i--) {
            minHeapify(i);
        }
    }

    /**
     *
     * @param i denotes the last index of the internal node that starts heapify
     */
    private void minHeapify(int i) {
        while (2 * i <= size) {
            int j = 2 * i;
            if (j < size
                    && backingArray[j].compareTo(backingArray[j + 1]) > 0) {
                j++;
            }
            if (backingArray[i].compareTo(backingArray[j]) < 0) {
                break;
            }
            exchange(i, j);
            i = j;
        }
    }

    /**
     *
     * @param i refers to the index of an value exchanging with j
     * @param j refers to the index of an value exchanging with i
     */
    private void exchange(int i, int j) {
        T temp = backingArray[i];
        backingArray[i] = backingArray[j];
        backingArray[j] = temp;
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }

        int capacity = backingArray.length;
        if (size == capacity - 1) {
            capacity *= 2;
            T[] array = (T[]) new Comparable[capacity];
            for (int i = 0; i < backingArray.length; i++) {
                array[i] = backingArray[i];
            }
            backingArray = array;
        }
        backingArray[size + 1] = data;
        size++;

        int childIndex = size;
        int parentIndex = childIndex / 2;
        while (parentIndex >= 1) {
            T child = backingArray[childIndex];
            T parent = backingArray[parentIndex];
            if (child.compareTo(parent) < 0) {
                backingArray[childIndex] = parent;
                backingArray[parentIndex] = child;
                childIndex = parentIndex;
                parentIndex = childIndex / 2;
            } else {
                break;
            }
        }


    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        }

        T removeData = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;

        int parentIndex = 1;
        while (2 * parentIndex <= size || (2 * parentIndex + 1) <= size) {
            if (backingArray[2 * parentIndex + 1] == null) {
                T parent = backingArray[parentIndex];
                T child = backingArray[parentIndex * 2];
                if (parent.compareTo(child) > 0) {
                    backingArray[parentIndex] = child;
                    backingArray[parentIndex * 2] = parent;
                    parentIndex *= 2;
                } else {
                    break;
                }
            } else {
                T parent = backingArray[parentIndex];
                T child1 = backingArray[2 * parentIndex];
                T child2 = backingArray[2 * parentIndex + 1];
                if (child1.compareTo(child2) < 0) {
                    if (child1.compareTo(parent) < 0) {
                        backingArray[parentIndex] = child1;
                        backingArray[2 * parentIndex] = parent;
                        parentIndex *= 2;
                    } else {
                        break;
                    }
                } else {
                    if (child2.compareTo(parent) < 0) {
                        backingArray[parentIndex] = child2;
                        backingArray[2 * parentIndex + 1] = parent;
                        parentIndex = 2 * parentIndex + 1;
                    } else {
                        break;
                    }
                }
            }
        }
        return removeData;
    }


    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
