import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Some edge cases that I didnt see posted
 *
 * @author Hosuk Choi
 * @version 1.0
 */
public class DoublyLinkedListHosukTest {

    private static final int TIMEOUT = 200;
    private DoublyLinkedList<String> list;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }


    @Test(expected = NoSuchElementException.class)
    public void testRemoveAndAddAtBack() {
        list.addToFront("first");
        list.addToBack("second");
        list.addToBack("third");
        list.addToBack("fourth");
        list.addToBack("fifth");

        assertEquals(list.removeAtIndex(3), "fourth");
        assertEquals(list.removeAtIndex(3), "fifth");
        assertEquals(list.size(), 3);
        list.addAtIndex(3, "fourth");
        assertEquals(list.get(3), "fourth");
        list.addAtIndex(4, "lol");
        assertEquals(list.get(4), "lol");
        assertEquals(list.size(), 5);
        assertEquals(list.removeFromFront(), "first");
        assertEquals(list.removeFromFront(), "second");
        assertEquals(list.removeFromFront(), "third");
        assertEquals(list.removeFromFront(), "fourth");
        assertEquals(list.removeFromFront(), "lol");
        assertEquals(list.removeFromFront(), "first");
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveAndAddAtFront() {
        list.addToFront("fifth");
        list.addToFront("fourth");
        list.addToFront("third");
        list.addToFront("second");
        list.addToFront("first");

        assertEquals(list.removeAtIndex(3), "fourth");
        assertEquals(list.removeAtIndex(3), "fifth");
        assertEquals(list.size(), 3);
        list.addAtIndex(3, "fourth");
        assertEquals(list.get(3), "fourth");
        list.addAtIndex(4, "lol");
        assertEquals(list.get(4), "lol");
        assertEquals(list.size(), 5);
        assertEquals(list.removeFromBack(), "lol");
        assertEquals(list.removeFromBack(), "fourth");
        assertEquals(list.removeFromBack(), "third");
        assertEquals(list.removeFromBack(), "second");
        assertEquals(list.removeFromBack(), "first");
        assertEquals(list.removeFromBack(), "first");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsException() {
        list.addToFront("First");
        assertEquals(list.get(0), "First");
        assertEquals(list.get(1), "This should fail");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionNegative() {
        list.addToBack("First");
        assertEquals(list.get(0), "First");
        assertEquals(list.isEmpty(), false);
        list.removeFromBack();
        assertEquals(list.isEmpty(), true);
        list.addToBack("yoyoyo");
        list.clear();
        assertEquals(list.isEmpty(), true);
        assertEquals(list.get(-1), "This should fail");
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() {
        list.removeLastOccurrence("yo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegality() {
        list.addAtIndex(0, "Index insert!");
        list.removeLastOccurrence(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndex1() {
        list.addAtIndex(1, "Index insert!");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndex2() {
        list.removeAtIndex(0);
    }

    @Test(timeout = TIMEOUT) 
    public void testEmptyArray() {
        String[] obj = new String[0];
        assertArrayEquals(list.toArray(), obj);
    }

    @Test(timeout = TIMEOUT)
    public void testLastOccurrence() {
        list.addToFront("hello");
        list.addToFront("hello");
        list.addToFront("hi");
        list.addToFront("yo");
        list.addToFront("hello");
        assertEquals(list.get(0), "hello");
        assertEquals(list.get(3), "hello");
        assertEquals(list.removeLastOccurrence("hello"), "hello");
        assertEquals(list.get(0), "hello");
        assertEquals(list.get(1), "yo");
        assertEquals(list.get(2), "hi");
        assertEquals(list.get(3), "hello");
    }
}