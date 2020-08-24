import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Zhen Jiang
 * @version 1.0
 * @userid zjiang330
 * @GTID 903402987
 *
 * Collaborators: none
 *
 * Resources: none
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("the index is less than 0");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("the index is greater "
                                                + "than size");
        } else if (data == null) {
            throw new IllegalArgumentException("the data is null "
                                    + "(does not refer to any value).");
        }

        int indexToHead = index - 0;
        int indexToTail = (size - 1) - index;

        if (size == 0) {
            head = new DoublyLinkedListNode<>(data);
            tail = head;
            size++;
        } else if (index == 0) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
        } else if (index == size) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            if (indexToHead <= indexToTail) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                DoublyLinkedListNode<T> previousCurr = curr.getPrevious();
                newNode.setPrevious(previousCurr);
                newNode.setNext(curr);
                previousCurr.setNext(newNode);
                curr.setPrevious(newNode);
                size++;
            } else {
                DoublyLinkedListNode<T> curr = tail;
                DoublyLinkedListNode<T> previousCurr = tail.getPrevious();
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                    previousCurr = curr.getPrevious();
                }
                newNode.setPrevious(previousCurr);
                newNode.setNext(curr);
                previousCurr.setNext(newNode);
                curr.setPrevious(newNode);
                size++;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null "
                                + "(does not refer to any value).");
        }
        if (size == 0) {
            head = new DoublyLinkedListNode<T>(data);
            tail = head;
            size++;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null "
                                + "(does not refer to any value).");
        }
        if (size == 0) {
            tail = new DoublyLinkedListNode<T>(data);
            head = tail;
            size++;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index is less than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index is greater than "
                                                    + "and equal to size");
        }
        int indexToHead = index - 0;
        int indexToTail = (size - 1) - index;
        if (size == 1) {
            T removed = head.getData();
            head = null;
            tail = null;
            size--;
            return removed;
        } else if (index == 0) {
            T removed = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return removed;
        } else if (index == size - 1) {
            T removed = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return removed;
        } else {
            if (indexToHead <= indexToTail) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                curr.getPrevious().setNext(curr.getNext());
                curr.getNext().setPrevious(curr.getPrevious());
                size--;
                return curr.getData();
            } else {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
                curr.getPrevious().setNext(curr.getNext());
                curr.getNext().setPrevious(curr.getPrevious());
                size--;
                return curr.getData();
            }
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        }
        if (size == 1) {
            T removed = head.getData();
            head = null;
            tail = null;
            size--;
            return removed;
        }
        DoublyLinkedListNode<T> removed = head;
        head = head.getNext();
        head.setPrevious(null);
        size--;
        return removed.getData();
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
            throw new NoSuchElementException("The list is empty");
        }
        if (size == 1) {
            T removed = tail.getData();
            head = null;
            tail = null;
            size--;
            return removed;
        }
        DoublyLinkedListNode<T> removed = tail;
        tail = tail.getPrevious();
        tail.setNext(null);
        size--;
        return removed.getData();
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index is less than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index is greater"
                    + " than and equal to size");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        }


        int indexToHead = index;
        int indexToTail = (size - 1) - index;
        if (indexToHead <= indexToTail) {
            DoublyLinkedListNode<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            return temp.getData();
        } else {
            DoublyLinkedListNode<T> temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.getPrevious();
            }
            return temp.getData();
        }

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
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data is null "
                                    + "(does not refer to any value)");
        }

        if (size == 0) {
            throw new NoSuchElementException("The data is not found");
        } else if (data.equals(tail.getData())) {
            T removed = tail.getData();
            if (size == 1) {
                head = null;
                tail = null;
                size--;
                return removed;
            }
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return removed;
        } else {
            DoublyLinkedListNode<T> curr = tail;
            int i = size - 1;
            while (curr != null && (!(curr.getData().equals(data)))) {
                curr = curr.getPrevious();
                i--;
            }
            if (curr == null) {
                throw new NoSuchElementException("The data is not found "
                                                + "in the linked list.");
            }
            T removed = curr.getData();
            if (i == 0) {
                head = head.getNext();
                head.setPrevious(null);
                size--;
                return removed;
            } else {
                curr.getPrevious().setNext(curr.getNext());
                curr.getNext().setPrevious(curr.getPrevious());
                size--;
                return removed;
            }
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] array1 = new Object[size];
        DoublyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            array1[i] = curr.getData();
            curr = curr.getNext();
        }
        return array1;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
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
        // DO NOT MODIFY!
        return size;
    }
}
