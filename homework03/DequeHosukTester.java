import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * tester class using Junit
 * @author Hosuk Choi
 * @version 1.0
 */
public class DequeHosukTester {
    private static final int TIMEOUT = 200;
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void arrayAddFirst() {
        assertEquals(array.size(), 0);
        array.addFirst("0");
        assertEquals(array.getFirst(), "0");
        assertEquals(array.size(), 1);
        array.addFirst("9");
        array.addFirst("8");
        array.addFirst("7");
        array.addFirst("6");
        array.addFirst("5");
        array.addFirst("4");
        array.addFirst("3");
        array.addFirst("2");
        array.addFirst("1");
        array.addFirst("New");
        array.addFirst("checking first index");
        assertEquals(array.getFirst(), "checking first index");
        assertEquals(array.size(), 12);
        assertEquals(array.removeFirst(), "checking first index");
        assertEquals(array.removeFirst(), "New");
        assertEquals(array.removeFirst(), "1");
        assertEquals(array.removeFirst(), "2");
        assertEquals(array.removeFirst(), "3");
        assertEquals(array.removeFirst(), "4");
        assertEquals(array.removeFirst(), "5");
        assertEquals(array.removeFirst(), "6");
        assertEquals(array.removeFirst(), "7");
        assertEquals(array.removeFirst(), "8");
        assertEquals(array.removeFirst(), "9");
        assertEquals(array.removeFirst(), "0");
        assertEquals(array.size(), 0);
        array.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void arrayAddLast() {
        assertEquals(array.size(), 0);
        array.addLast("0");
        assertEquals(array.getFirst(), "0");
        assertEquals(array.size(), 1);
        array.addLast("9");
        array.addLast("8");
        array.addLast("7");
        array.addLast("6");
        array.addLast("5");
        array.addLast("4");
        array.addLast("3");
        array.addLast("2");
        array.addLast("1");
        array.addLast("New");
        array.addLast("checking last index");
        assertEquals(array.getLast(), "checking last index");
        assertEquals(array.size(), 12);
        assertEquals(array.removeLast(), "checking last index");
        assertEquals(array.removeLast(), "New");
        assertEquals(array.removeLast(), "1");
        assertEquals(array.removeLast(), "2");
        assertEquals(array.removeLast(), "3");
        assertEquals(array.removeLast(), "4");
        assertEquals(array.removeLast(), "5");
        assertEquals(array.removeLast(), "6");
        assertEquals(array.removeLast(), "7");
        assertEquals(array.removeLast(), "8");
        assertEquals(array.removeLast(), "9");
        assertEquals(array.removeLast(), "0");
        assertEquals(array.size(), 0);
        array.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirst() {
        array.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLast() {
        array.removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuch() {
        array.getFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuch1() {
        array.addFirst("Colossians 3:23");
        assertEquals(array.getFirst(), "Colossians 3:23");
        assertEquals(array.getLast(), "Colossians 3:23");
        assertEquals(array.size(), 1);
        array.removeFirst();
        array.getLast();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLinkAddFirst() {
        assertEquals(linked.size(), 0);
        linked.addFirst("first!");
        assertEquals(linked.getFirst(), "first!");
        assertEquals(linked.removeFirst(), "first!");
        linked.addFirst("1");
        linked.addFirst("2");
        linked.addFirst("3");
        assertEquals(linked.getHead().getData(), "3");
        assertEquals(linked.getTail().getData(), "1");
        assertEquals(linked.size(), 3);
        assertEquals(linked.removeFirst(), "3");
        assertEquals(linked.removeFirst(), "2");
        assertEquals(linked.removeFirst(), "1");
        assertEquals(linked.size(), 0);
        linked.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLinkAddLast() {
        assertEquals(linked.size(), 0);
        linked.addLast("first!");
        assertEquals(linked.getLast(), "first!");
        assertEquals(linked.removeLast(), "first!");
        linked.addLast("3");
        linked.addLast("2");
        linked.addLast("1");
        assertEquals(linked.getHead().getData(), "3");
        assertEquals(linked.getTail().getData(), "1");
        assertEquals(linked.size(), 3);
        assertEquals(linked.removeLast(), "1");
        assertEquals(linked.removeLast(), "2");
        assertEquals(linked.removeLast(), "3");
        assertEquals(linked.size(), 0);
        linked.addFirst(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testNSE2() {
        linked.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNSE3() {
        linked.removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetFirst() {
        linked.addLast("John 16:22");
        linked.addFirst("1 John 4:8");
        assertEquals(linked.getFirst(), "1 John 4:8");
        linked.addFirst("Romans 8:1");
        assertEquals(linked.getLast(), "John 16:22");
        assertEquals(linked.getFirst(), "Romans 8:1");
        assertEquals(linked.removeLast(), "John 16:22");
        assertEquals(linked.removeFirst(), "Romans 8:1");
        assertEquals(linked.removeFirst(), "1 John 4:8");
        linked.getFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetLast() {
        linked.addLast("Luke 12:23");
        linked.addFirst("2 Corinthians 4:16");
        assertEquals(linked.getLast(), "Luke 12:23");
        linked.addLast("1 Corinthians 2:9");
        assertEquals(linked.getLast(), "1 Corinthians 2:9");
        assertEquals(linked.getFirst(), "2 Corinthians 4:16");
        assertEquals(linked.removeLast(), "1 Corinthians 2:9");
        assertEquals(linked.removeLast(), "Luke 12:23");
        assertEquals(linked.removeFirst(), "2 Corinthians 4:16");
        linked.getLast();
    }
}
