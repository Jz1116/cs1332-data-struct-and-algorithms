import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * This is a basic set of unit tests for ArrayList.
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
 * @author CS 1332 TAs
 * @version 1.0
 */
public class ArrayListStudentTest {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
            list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndex() {
        list.addAtIndex(0, "2a"); // 2a
        list.addAtIndex(0, "1a"); // 1a, 2a
        list.addAtIndex(2, "4a"); // 1a, 2a, 4a
        list.addAtIndex(2, "3a"); // 1a, 2a, 3a, 4a
        list.addAtIndex(0, "0a"); // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "6a"); // 0a, 1a, 2a, 3a, 4a, 5a
        list.addAtIndex(5,"5a");
        list.addAtIndex(7, "7a");
        list.addAtIndex(8,"9a");
        assertEquals(9, list.size());
        list.addAtIndex(8,"8a");


        //assertEquals(10, list.size());
        Object[] expected = new Object[2 * ArrayList.INITIAL_CAPACITY];
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
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        list.addToFront("9a"); // 4a
        list.addToFront("8a"); // 3a, 4a
        list.addToFront("7a"); // 2a, 3a, 4a
        list.addToFront("6a"); // 1a, 2a, 3a, 4a
        list.addToFront("5a"); // 0a, 1a, 2a, 3a, 4a
        list.addToFront("4a"); // 4a
        list.addToFront("3a"); // 3a, 4a
        list.addToFront("2a"); // 2a, 3a, 4a
        list.addToFront("1a"); // 1a, 2a, 3a, 4a
        list.addToFront("0a"); // 0a, 1a, 2a, 3a, 4a

        assertEquals(10, list.size());
        Object[] expected = new Object[2 * ArrayList.INITIAL_CAPACITY];
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
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        list.addToBack("0a"); // 0a
        list.addToBack("1a"); // 0a, 1a
        list.addToBack("2a"); // 0a, 1a, 2a
        list.addToBack("3a"); // 0a, 1a, 2a, 3a
        list.addToBack("4a"); // 0a, 1a, 2a, 3a, 4a
        list.addToBack("5a"); // 0a
        list.addToBack("6a"); // 0a, 1a
        list.addToBack("7a"); // 0a, 1a, 2a
        list.addToBack("8a"); // 0a, 1a, 2a, 3a
        list.addToBack("9a"); // 0a, 1a, 2a, 3a, 4a

        assertEquals(10, list.size());
        Object[] expected = new Object[2 * ArrayList.INITIAL_CAPACITY];
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
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndex() {
        String temp = "2a"; // For equality checking.

        list.addAtIndex(0, "0a"); // 0a
        list.addAtIndex(1, "1a"); // 0a, 1a
        list.addAtIndex(2, temp); // 0a, 1a, 2a
        list.addAtIndex(3, "3a"); // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a"); // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a"); // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeAtIndex(2)); // 0a, 1a, 3a, 4a, 5a
        assertSame("5a", list.removeAtIndex(4)); // 0a, 1a, 3a, 4a
        assertSame("0a", list.removeAtIndex(0)); // 1a, 3a, 4a
        assertEquals(3, list.size());
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "3a";
        expected[2] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFront() {
        String temp = "0a"; // For equality checking.

        list.addAtIndex(0, temp); // 0a
        list.addAtIndex(1, "1a"); // 0a, 1a
        list.addAtIndex(2, "2a"); // 0a, 1a, 2a
        list.addAtIndex(3, "3a"); // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a"); // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, "5a"); // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromFront()); // 1a, 2a, 3a, 4a, 5a
        assertEquals(5, list.size());
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "1a";
        expected[1] = "2a";
        expected[2] = "3a";
        expected[3] = "4a";
        expected[4] = "5a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        String temp = "5a"; // For equality checking.

        list.addAtIndex(0, "0a"); // 0a
        list.addAtIndex(1, "1a"); // 0a, 1a
        list.addAtIndex(2, "2a"); // 0a, 1a, 2a
        list.addAtIndex(3, "3a"); // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a"); // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(5, temp); // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        assertSame(temp, list.removeFromBack()); // 0a, 1a, 2a, 3a, 4a
        assertEquals(5, list.size());
        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        list.addAtIndex(0, "0a"); // 0a
        list.addAtIndex(1, "1a"); // 0a, 1a
        list.addAtIndex(2, "2a"); // 0a, 1a, 2a
        list.addAtIndex(3, "3a"); // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a"); // 0a, 1a, 2a, 3a, 4a

        assertEquals("0a", list.get(0));
        assertEquals("1a", list.get(1));
        assertEquals("2a", list.get(2));
        assertEquals("3a", list.get(3));
        assertEquals("4a", list.get(4));
    }

    @Test(timeout = TIMEOUT)
    public void testIsEmptyAndClear() {
        // Should be empty at initialization
        assertTrue(list.isEmpty());

        // Should not be empty after adding elements
        list.addAtIndex(0, "0a"); // 0a
        list.addAtIndex(1, "1a"); // 0a, 1a
        list.addAtIndex(2, "2a"); // 0a, 1a, 2a
        list.addAtIndex(3, "3a"); // 0a, 1a, 2a, 3a
        list.addAtIndex(4, "4a"); // 0a, 1a, 2a, 3a, 4a
        assertFalse(list.isEmpty());

        // Clearing the list should empty the array and reset size
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertArrayEquals(new Object[ArrayList.INITIAL_CAPACITY],
            list.getBackingArray());
    }


    @Test(expected = IllegalArgumentException.class)
    public void exceptionalTestCase() {
        list.addAtIndex(0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionalTestCase9() {
        list.addToFront(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionalTestCase10() {
        list.addToBack(null);
    }


    @Test(expected = NoSuchElementException.class)
    public void exceptionalTestCase2() {
        assertTrue(list.isEmpty());
        list.removeFromFront();
    }

    @Test(expected = NoSuchElementException.class)
    public void exceptionalTestCase8() {
        assertTrue(list.isEmpty());
        list.removeFromBack();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void exceptionalTestCase1() {
        assertTrue(list.isEmpty());
        list.addAtIndex(-1,"1a");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void exceptionalTestCase3() {
        assertTrue(list.isEmpty());
        list.addAtIndex(1, "1a");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void exceptionalTestCase4() {
        assertTrue(list.isEmpty());
        list.addAtIndex(0, "1a");
        assertEquals(1, list.size());
        list.removeAtIndex(2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void exceptionalTestCase5() {
        assertTrue(list.isEmpty());
        list.addAtIndex(0, "1a");
        list.removeAtIndex(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void exceptionalTestCase6() {
        assertTrue(list.isEmpty());
        list.addAtIndex(0, "1a");
        list.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void exceptionalTestCase7() {
        assertTrue(list.isEmpty());
        list.addAtIndex(0, "1a");
        list.get(2);
    }
}