import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
 *
 * @author Zhen Jiang
 * @version 1.0
 *
 * Collaborators: n/a
 *
 * Resources: n/a
 * @userid zjiang330
 * @GTID 903402987
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        Object[] temp = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) temp;
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        int capacity = backingArray.length;
        if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than 0");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        }
        if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }

        if (index == size) {
            if (size < capacity) {
                backingArray[size] = data;
                size++;
            } else if (size == capacity) {
                capacity *= 2;
                Object[] temp2 = new Object[capacity];
                T[] array2 = (T[]) temp2;
                for (int i = 0; i < size; i++) {
                    array2[i] = backingArray[i];
                }
                array2[index] = data;
                size++;
                backingArray = array2;
            }
        } else {
            if (size < capacity) {
                for (int i = size; i > index; i--) {
                    backingArray[i] = backingArray[i - 1];
                }
                backingArray[index] = data;
                size++;
            } else if (size == capacity) {
                capacity *= 2;
                Object[] temp2 = new Object[capacity];
                T[] array2 = (T[]) temp2;
                for (int i = 0; i < size + 1; i++) {
                    if (i < index) {
                        array2[i] = backingArray[i];
                    } else if (i == index) {
                        array2[i] = data;
                    } else {
                        array2[i] = backingArray[i - 1];
                    }
                }
                backingArray = array2;
                size++;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        int capacity = backingArray.length;
        if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }

        if (size < capacity) {
            for (int i = size; i > 0; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[0] = data;
            size++;
        } else if (size == capacity) {
            capacity *= 2;
            Object[] temp2 = new Object[capacity];
            T[] array2 = (T[]) temp2;
            for (int i = 0; i < size + 1; i++) {
                if (i == 0) {
                    array2[i] = data;
                } else {
                    array2[i] = backingArray[i - 1];
                }
            }
            backingArray = array2;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        int capacity = backingArray.length;
        if (data == null) {
            throw new IllegalArgumentException("the data is null");
        }

        if (size < capacity) {
            backingArray[size] = data;
            size++;
        } else if (size == capacity) {
            capacity *= 2;
            Object[] temp2 = new Object[capacity];
            T[] array2 = (T[]) temp2;
            for (int i = 0; i < size; i++) {
                array2[i] = backingArray[i];
            }
            array2[size] = data;
            size++;
            backingArray = array2;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than and"
                    + " equal to size");
        }

        if (index == size - 1) {
            T removeValue = backingArray[index];
            backingArray[index] = null;
            size--;
            return removeValue;
        } else {
            T removeValue = backingArray[index];
            for (int i = 0; i < size - 1; i++) {
                if (i < index) {
                    backingArray[i] = backingArray[i];
                } else {
                    backingArray[i] = backingArray[i + 1];
                }
            }
            backingArray[size - 1] = null;
            size--;
            return removeValue;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("the list is empty");
        }
        T removeValue = backingArray[0];
        for (int i = 0; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;
        return removeValue;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("the list is empty.");
        }

        T removeValue = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return removeValue;

    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than "
                    + "and equal to size");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        Object[] array1 = new Object[INITIAL_CAPACITY];
        backingArray = (T[]) array1;
        size = 0;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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
