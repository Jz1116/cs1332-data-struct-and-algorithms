import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * This is a basic set of unit tests for ArrayDeque and LinkedDeque.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs, Alexander Liu
 * @version 1.0
 */
public class DequeStudentTest {

    private static final int TIMEOUT = 200;
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, array.size());
        assertArrayEquals(new Object[ArrayDeque.INITIAL_CAPACITY],
            array.getBackingArray());
        assertEquals(0, linked.size());
        assertNull(linked.getHead());
        assertNull(linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeNoWrapAround() {
        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _
        array.addLast("2a"); // 0a, 1a, 2a,  _, _, _, _, _, _, _, _
        array.addLast("3a"); // 0a, 1a, 2a, 3a, _, _, _, _, _, _, _
        array.addLast("4a"); // 0a, 1a, 2a, 3a, 4a, _, _, _, _, _, _

        assertEquals(5, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("4a", array.getLast());

        // _, 1a, 2a, 3a, 4a, _, _, _, _, _, _
        assertEquals("0a", array.removeFirst());
        // _, _, 2a, 3a, 4a, _, _, _, _, _, _
        assertEquals("1a", array.removeFirst());
        // _, _, 2a, 3a, _, _, _, _, _, _, _
        assertEquals("4a", array.removeLast());
        // _, _, 2a, _, _, _, _, _, _, _, _
        assertEquals("3a", array.removeLast());

        assertEquals(1, array.size());
        expected[0] = null;
        expected[1] = null;
        expected[3] = null;
        expected[4] = null;
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("2a", array.getFirst());
        assertEquals("2a", array.getLast());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeWrapAround() {
        array.addFirst("4a"); // _, _, _, _, _, _, _, _, _, _, 4a
        array.addFirst("3a"); // _, _, _, _, _, _, _, _, _, 3a, 4a
        array.addFirst("2a"); // _, _, _, _, _, _, _, _, 2a, 3a, 4a
        array.addFirst("1a"); // _, _, _, _, _, _, _, 1a, 2a, 3a, 4a
        array.addFirst("0a"); // _, _, _, _, _, _, 0a, 1a, 2a, 3a, 4a

        assertEquals(5, array.size());
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[10] = "4a";
        expected[9] = "3a";
        expected[8] = "2a";
        expected[7] = "1a";
        expected[6] = "0a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("4a", array.getLast());

        // _, _, _, _, _, _, 0a, 1a, 2a, 3a, _
        assertEquals("4a", array.removeLast());

        assertEquals(4, array.size());
        expected[10] = null;
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("3a", array.getLast());

        array.addLast("5a"); // _, _, _, _, _, _, 0a, 1a, 2a, 3a, 5a
        array.addLast("6a"); // 6a, _, _, _, _, _, 0a, 1a, 2a, 3a, 5a

        assertEquals(6, array.size());
        expected[10] = "5a";
        expected[0] = "6a";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("6a", array.getLast());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayDequeAddFirstNull() {
        array.addFirst(null);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeAddFirstFull() {
        array.addFirst("4a"); // _, _, _, _, _, _, _, _, _, _, (4a)
        array.addFirst("3a"); // _, _, _, _, _, _, _, _, _, (3a), 4a
        array.addFirst("2a"); // _, _, _, _, _, _, _, _, (2a), 3a, 4a
        array.addFirst("1a"); // _, _, _, _, _, _, _, (1a), 2a, 3a, 4a
        array.addFirst("0a"); // _, _, _, _, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("5a"); // 5a, _, _, _, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("6a"); // 5a, 6a, _, _, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("7a"); // 5a, 6a, 7a, _, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("8a"); // 5a, 6a, 7a, 8a, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("9a"); // 5a, 6a, 7a, 8a, 9a, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("10a"); // 5a, 6a, 7a, 8a, 9a, 10a, (0a), 1a, 2a, 3a, 4a
        array.addFirst("newFront"); // (newFront), 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, _, _, _, _, _, _, _, _, _, _

        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY * 2];
        expected[11] = "10a";
        expected[10] = "9a";
        expected[9] = "8a";
        expected[8] = "7a";
        expected[7] = "6a";
        expected[6] = "5a";
        expected[5] = "4a";
        expected[4] = "3a";
        expected[3] = "2a";
        expected[2] = "1a";
        expected[1] = "0a";
        expected[0] = "newFront";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("newFront", array.getFirst());
        assertEquals("10a", array.getLast());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayDequeAddLastNull() {
        array.addLast(null);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeAddLastFull() {
        array.addFirst("4a"); // _, _, _, _, _, _, _, _, _, _, (4a)
        array.addFirst("3a"); // _, _, _, _, _, _, _, _, _, (3a), 4a
        array.addFirst("2a"); // _, _, _, _, _, _, _, _, (2a), 3a, 4a
        array.addFirst("1a"); // _, _, _, _, _, _, _, (1a), 2a, 3a, 4a
        array.addFirst("0a"); // _, _, _, _, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("5a"); // 5a, _, _, _, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("6a"); // 5a, 6a, _, _, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("7a"); // 5a, 6a, 7a, _, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("8a"); // 5a, 6a, 7a, 8a, _, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("9a"); // 5a, 6a, 7a, 8a, 9a, _, (0a), 1a, 2a, 3a, 4a
        array.addLast("10a"); // 5a, 6a, 7a, 8a, 9a, 10a, (0a), 1a, 2a, 3a, 4a
        array.addLast("newEnd"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, newEnd, _, _, _, _, _, _, _, _, _

        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY * 2];
        expected[10] = "10a";
        expected[9] = "9a";
        expected[8] = "8a";
        expected[7] = "7a";
        expected[6] = "6a";
        expected[5] = "5a";
        expected[4] = "4a";
        expected[3] = "3a";
        expected[2] = "2a";
        expected[1] = "1a";
        expected[0] = "0a";
        expected[11] = "newEnd";
        assertArrayEquals(expected, array.getBackingArray());
        assertEquals("0a", array.getFirst());
        assertEquals("newEnd", array.getLast());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeRemoveFirstNull() {
        array.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeRemoveLastNull() {
        array.removeLast();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeGetFirstNull() {
        array.getFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeGetLastNull() {
        array.getLast();
    }

    /*
    *
    * This section ends testing for ArrayDeque
    * And starts testing for LinkedDeque
    *
     */
    //

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeAdd() {
        linked.addFirst("1a"); // 1a
        linked.addFirst("0a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals(4, linked.size());

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("0a", cur.getData());

        LinkedNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("1a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotEquals(null, cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("2a", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("3a", cur.getData());
        assertEquals(linked.getTail(), cur);
        assertNull(cur.getNext());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeRemove() {
        linked.addFirst("1a"); // 1a
        linked.addFirst("0a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals(4, linked.size());

        assertEquals("0a", linked.removeFirst()); // 1a, 2a, 3a
        assertEquals("3a", linked.removeLast()); // 1a, 2a

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("1a", cur.getData());

        LinkedNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("2a", cur.getData());
        assertEquals(linked.getTail(), cur);

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedDequeAddFirstNull() {
        linked.addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedDequeAddLastNull() {
        linked.addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeRemoveFirstNull() {
        linked.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeRemoveLastNull() {
        linked.removeLast();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeGet() {
        linked.addLast("0a"); // 0a
        linked.addLast("1a"); // 0a, 1a
        linked.addLast("2a"); // 0a, 1a, 2a
        linked.addLast("3a"); // 0a, 1a, 2a, 3a

        assertEquals("0a", linked.getFirst());
        assertEquals("3a", linked.getLast());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeGetFirstNull() {
        linked.getFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeGetLastNull() {
        linked.getLast();
    }

    // Because I can
    @Test(timeout = TIMEOUT)
    public void testLinkedNodeString() {
        LinkedNode<String> newNode = new LinkedNode<>("this is a test");
        System.out.println(newNode.toString());
    }
}
