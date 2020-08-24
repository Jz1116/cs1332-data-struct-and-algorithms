import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DoublyLinkedListImanuelTest {
    private static final int TIMEOUT = 200;
    private DoublyLinkedList<String> list;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontFromEmpty() {
        list.addToFront("0a");

        DoublyLinkedListNode<String> cur = list.getHead();

        assertNotNull(cur);
        assertEquals("0a", cur.getData());

        cur = cur.getNext();

        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBackFromEmpty() {
        list.addToBack("0a");

        DoublyLinkedListNode<String> cur = list.getHead();

        assertNotNull(cur);
        assertEquals("0a", cur.getData());

        cur = cur.getNext();

        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testAddNodesThenRemoveAll() {
        list.addAtIndex(0,"0a"); // 0a
        list.addAtIndex(1,"1a"); // 0a, 1a
        list.addAtIndex(2,"2a"); // 0a, 1a, 2a

        list.removeAtIndex(2); // 0a, 1a
        list.removeAtIndex(1); // 0a
        list.removeAtIndex(0); // empty

        DoublyLinkedListNode<String> head = list.getHead();
        DoublyLinkedListNode<String> tail = list.getTail();

        assertNull(head);
        assertNull(tail);
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexBelowBounds() {
        list.addAtIndex(-1, "0a");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexAboveBounds() {
        list.addAtIndex(3, "0a");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddAtIndexNullData() {
       list.addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToFrontNullData() {
        list.addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToBackNullData() {
        list.addToBack(null);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexBelowBounds() {
        list.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexAboveBounds() {
        list.removeAtIndex(3);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromFrontEmptyList() {
        list.removeFromFront();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromBackEmptyList() {
        list.removeFromBack();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexCloserToEndOfList() {
        String temp = "4a";
        list.addAtIndex(0, "0a"); // 0a
        list.addAtIndex(1, "1a"); // 0a, 1a
        list.addAtIndex(2, "2a"); // 0a, 1a, 2a
        list.addAtIndex(3, "3a"); // 0a, 1a, 2a, 3a
        list.addAtIndex(4, temp); // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a"); // 0a, 1a, 2a, 3a, 4a, 5a
        list.addAtIndex(6, "6a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a

        assertSame(temp, list.removeAtIndex(4));
        assertEquals(6, list.size());

        DoublyLinkedListNode<String> cur = list.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("0a", cur.getData());

        DoublyLinkedListNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("1a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("2a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("3a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("5a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("6a", cur.getData());
        assertSame(list.getTail(), cur);

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testToArrayEmptyList() {
        String[] emptyArray = new String[0];
        assertEquals(emptyArray, list.toArray());
    }
}