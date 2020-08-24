import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DequeImanuelTest {
    private static final int TIMEOUT = 200;
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    /*********************
     * Array deque tests *
     *********************/

    @Test(timeout = TIMEOUT)
    public void testArrayDequeUsedAsQueue() {
        array.addFirst("0a"); // _, _, _, _, _, _, _, _, _, _, 0a
        array.addFirst("1a"); // _, _, _, _, _, _, _, _, _, 1a, 0a
        array.addFirst("2a"); // _, _, _, _, _, _, _, _, 2a, 1a, 0a

        assertEquals("0a", array.removeLast());
        assertEquals("1a", array.removeLast());
        assertEquals("2a", array.removeLast());
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeUsedAsStack() {
        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _
        array.addLast("2a"); // 0a, 1a, 2a, _, _, _, _, _, _, _, _

        assertEquals("2a", array.removeLast());
        assertEquals("1a", array.removeLast());
        assertEquals("0a", array.removeLast());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayDequeAddFirstNullData() {
        array.addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testArrayDequeAddLastNullData() {
        array.addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeRemoveFirstEmptyDeque() {
        assertEquals(0, array.size());
        array.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeRemoveLastEmptyDeque() {
        assertEquals(0, array.size());
        array.removeLast();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeGetFirstEmptyDeque() {
        assertEquals(0, array.size());
        array.getFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayDequeGetLastEmptyDeque() {
        assertEquals(0, array.size());
        array.getLast();
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeGrowsWhenFull() {
        assertEquals(11, ((Object[]) array.getBackingArray()).length);

        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _
        array.addLast("2a"); // 0a, 1a, 2a,  _, _, _, _, _, _, _, _
        array.addLast("3a"); // 0a, 1a, 2a, 3a, _, _, _, _, _, _, _
        array.addLast("4a"); // 0a, 1a, 2a, 3a, 4a, _, _, _, _, _, _
        array.addLast("5a"); // 0a, 1a, 2a, 3a, 4a, 5a, _, _, _, _, _
        array.addLast("6a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, _, _, _, _
        array.addLast("7a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, _, _, _
        array.addLast("8a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, _, _
        array.addLast("9a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, _
        array.addLast("10a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a

        assertEquals(11, array.size());
        assertEquals(11, ((Object[]) array.getBackingArray()).length);
        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        expected[6] = "6a";
        expected[7] = "7a";
        expected[8] = "8a";
        expected[9] = "9a";
        expected[10] = "10a";
        assertArrayEquals(expected, array.getBackingArray());

        array.addLast("11a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, 11a, _, _, _, _, _, _, _, _, _, _

        expected = Arrays.copyOf(expected, 22);
        expected[11] = "11a";

        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(12, array.size());
        assertEquals(22, ((Object[]) array.getBackingArray()).length);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeUnwrapsWhenGrownAddLast() {
        assertEquals(11, ((Object[]) array.getBackingArray()).length);

        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _
        array.addLast("2a"); // 0a, 1a, 2a,  _, _, _, _, _, _, _, _
        array.addLast("3a"); // 0a, 1a, 2a, 3a, _, _, _, _, _, _, _
        array.addLast("4a"); // 0a, 1a, 2a, 3a, 4a, _, _, _, _, _, _
        array.addLast("5a"); // 0a, 1a, 2a, 3a, 4a, 5a, _, _, _, _, _
        array.addLast("6a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, _, _, _, _
        array.addLast("7a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, _, _, _
        array.addLast("8a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, _, _
        array.addLast("9a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, _
        array.addLast("10a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a

        array.removeFirst(); // _, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        array.removeFirst(); // _, _, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        array.removeFirst(); // _, _, _, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a

        array.addLast("11a"); // 11a, _, _, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        array.addLast("12a"); // 11a, 12a, _, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        array.addLast("13a"); // 11a, 12a, 13a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a

        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "11a";
        expected[1] = "12a";
        expected[2] = "13a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        expected[6] = "6a";
        expected[7] = "7a";
        expected[8] = "8a";
        expected[9] = "9a";
        expected[10] = "10a";
        assertArrayEquals(expected, array.getBackingArray());

        array.addLast("14a"); // 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, 11a, 12a, 13a, 14a, _, _, _, _, _, _, _, _, _, _


        expected = new String[ArrayDeque.INITIAL_CAPACITY * 2];
        expected[0] = "3a";
        expected[1] = "4a";
        expected[2] = "5a";
        expected[3] = "6a";
        expected[4] = "7a";
        expected[5] = "8a";
        expected[6] = "9a";
        expected[7] = "10a";
        expected[8] = "11a";
        expected[9] = "12a";
        expected[10] = "13a";
        expected[11] = "14a";

        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(22, ((Object[]) array.getBackingArray()).length);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeUnwrapsWhenGrownAddFirst() {
        assertEquals(11, ((Object[]) array.getBackingArray()).length);

        array.addLast("0a"); // 0a, _, _, _, _, _, _, _, _, _, _
        array.addLast("1a"); // 0a, 1a, _, _, _, _, _, _, _, _, _
        array.addLast("2a"); // 0a, 1a, 2a,  _, _, _, _, _, _, _, _
        array.addLast("3a"); // 0a, 1a, 2a, 3a, _, _, _, _, _, _, _
        array.addLast("4a"); // 0a, 1a, 2a, 3a, 4a, _, _, _, _, _, _
        array.addLast("5a"); // 0a, 1a, 2a, 3a, 4a, 5a, _, _, _, _, _
        array.addLast("6a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, _, _, _, _
        array.addLast("7a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, _, _, _
        array.addLast("8a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, _, _
        array.addLast("9a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, _
        array.addLast("10a"); // 0a, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a

        array.removeFirst(); // _, 1a, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        array.removeFirst(); // _, _, 2a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        array.removeFirst(); // _, _, _, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a

        array.addLast("11a"); // 11a, _, _, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        array.addLast("12a"); // 11a, 12a, _, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a
        array.addLast("13a"); // 11a, 12a, 13a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a

        String[] expected = new String[ArrayDeque.INITIAL_CAPACITY];
        expected[0] = "11a";
        expected[1] = "12a";
        expected[2] = "13a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        expected[6] = "6a";
        expected[7] = "7a";
        expected[8] = "8a";
        expected[9] = "9a";
        expected[10] = "10a";
        assertArrayEquals(expected, array.getBackingArray());

        array.addFirst("14a"); // 14a, 3a, 4a, 5a, 6a, 7a, 8a, 9a, 10a, 11a, 12a, 13a, _, _, _, _, _, _, _, _, _, _

        expected = new String[ArrayDeque.INITIAL_CAPACITY * 2];
        expected[0] = "14a";
        expected[1] = "3a";
        expected[2] = "4a";
        expected[3] = "5a";
        expected[4] = "6a";
        expected[5] = "7a";
        expected[6] = "8a";
        expected[7] = "9a";
        expected[8] = "10a";
        expected[9] = "11a";
        expected[10] = "12a";
        expected[11] = "13a";

        assertArrayEquals(expected, array.getBackingArray());
        assertEquals(22, ((Object[]) array.getBackingArray()).length);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayDequeSizeUpdatesCorrectly() {
        array.addFirst("0a");
        array.addFirst("1a");
        array.addFirst("2a");

        assertEquals(3, array.size());

        array.removeFirst();
        array.removeFirst();
        array.removeFirst();

        assertEquals(0, array.size());
    }

    /***************************
     * Linked list deque tests *
     ***************************/

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedDequeAddFirstNullData() {
        linked.addFirst(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedDequeAddLastNullData() {
        linked.addLast(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeRemoveFirstEmptyDeque() {
        assertEquals(0, linked.size());
        linked.removeFirst();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeRemoveLastEmptyDeque() {
        assertEquals(0, linked.size());
        linked.removeLast();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedDequeGetFirstEmptyDeque() {
        assertEquals(0, linked.size());
        linked.getFirst();
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeHeadAndTailProperlySet() {
        linked.addFirst("0a");

        assertEquals("0a", linked.getHead().getData());
        assertEquals("0a", linked.getTail().getData());

        linked.addFirst("1a");

        assertEquals("1a", linked.getHead().getData());
        assertEquals("0a", linked.getTail().getData());

        linked.removeLast();

        assertEquals("1a", linked.getHead().getData());
        assertEquals("1a", linked.getTail().getData());

        linked.removeLast();

        assertEquals(null, linked.getHead());
        assertEquals(null, linked.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedDequeSizeUpdatesCorrectly() {
        linked.addFirst("0a");
        linked.addFirst("1a");
        linked.addFirst("2a");

        assertEquals(3, linked.size());

        linked.removeFirst();
        linked.removeFirst();
        linked.removeFirst();

        assertEquals(0, linked.size());
    }
}