import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * This is a set of tests for DoublyLinkedList which can reach 100% branch and
 * line coverage on my implementation of the assignment.
 *
 * @author William Goodall
 */
public class DoublyLinkedListFullCoverageTest {

    private static final int TIMEOUT = 200;

    // The list under test
    private DoublyLinkedList<Integer> list;

    // The expected size of the list after each test
    // set to -1 here, and in reset(), so tests fail if we forget to set size.
    private int size = -1;

    @Before
    public void reset() {
        list = new DoublyLinkedList<>();
        size = -1;
    }

    /** 
     * Checks the invariants of the given list, failing the test if they're
     * invalid.
     *
     * Run this check @After each test, verifying that the tested operation did
     * not break any invariants.
     *
     * Also, this assumes each test leaves its list in sorted form. This checks
     * that items are added in the right order.
     *
     * If one of the list operations leaves the data in an invalid state, we can
     * catch it right after it happens, rather than later when another
     * unrelated method fails. 
     *
     */
    @After
    public void assertInvariants() {
        assertEquals("list is not the correct size", size, list.size());
        if (list.size() == 0) {
            // Case 1: Is the list empty?

            // Head and tail must both be null.
            assertNull(list.getHead());
            assertNull(list.getTail());

        } else if (list.size() == 1) {
            // Case 2: Is it one item long?
            
            // Head and tail must point to the same thing.
            assertSame(
                "one-item list must have head, tail point to same item",
                list.getHead(),
                list.getTail()
            );

            // Item must be zero
            assertEquals("expected 0", 0, list.getHead().getData().intValue());
    
            // Item cannot have next/previous. It's the only one.
            DoublyLinkedListNode<Integer> item = list.getHead();
            assertNull("unexpected link in one-item list", item.getNext());
            assertNull("unexpected link in one-item list", item.getPrevious());

        } else {
            // Case 3: Is it >1 items long?
            
            // Head cannot have previous, tail cannot have next.
            assertNull(
                "head item cannot have previous link",
                list.getHead().getPrevious()
            );
            assertNull(
                "head item cannot have previous link",
                list.getTail().getNext()
            );

            // Check all items that aren't the head or the tail,
            // making sure their references are well-formed.
            DoublyLinkedListNode<Integer> cur = list.getHead();
            assertEquals(
                "should start with 0",
                0,
                cur.getData().intValue()
            ); 
            for (int i = 0; i < list.size() - 1; i++) {
                DoublyLinkedListNode<Integer> next = cur.getNext();
                assertNotNull("unexpected null reference inside list", next);
                assertSame(
                    "previous and next links must be reciprocal",
                    next.getPrevious(),
                    cur
                );
                assertEquals(
                    "expected items to equal their indices",
                    i,
                    cur.getData().intValue()
                );
                cur = next;
            }
            assertSame(cur, list.getTail());
        }
    }

    @Test
    public void addAtIndexEmpty() {
        size = 1;
        list.addAtIndex(0, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addAtIndexInvalid1() {
        size = 0;
        list.addAtIndex(100, 0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void addAtIndexInvalid2() {
        size = 0;
        list.addAtIndex(-1, 0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void addAtIndexInvalid3() {
        size = 3;
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(2);
        list.addAtIndex(4, -100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAtIndexNull() {
        size = 0;
        list.addAtIndex(0, null);
    }

    @Test()
    public void addAtIndexFront1() {
        size = 4;
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addAtIndex(0, 0);
    }

    @Test
    public void addAtIndexFront2() {
        size = 1;
        list.addAtIndex(0, 0);
    }

    @Test
    public void addAtIndexMiddle() {
        size = 3;
        list.addToBack(0);
        list.addToBack(2);
        list.addAtIndex(1, 1);
    }

    @Test
    public void addAtIndexBack() {
        size = 2;
        list.addToBack(0);
        list.addAtIndex(1, 1);
    }

    @Test
    public void addToFrontEmpty() {
        size = 1;
        list.addToFront(0);
    }

    @Test
    public void addToFront() {
        size = 2;
        list.addToBack(1);
        list.addToFront(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addToFrontNull() {
        size = 0;
        list.addToFront(null);
    }

    @Test
    public void addToBackEmpty() {
        size = 1;
        list.addToBack(0);
    }

    @Test
    public void addToBack() {
        size = 2;
        list.addToFront(0);
        list.addToBack(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addToBackNull() {
        size = 0;
        list.addToBack(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtIndexEmpty() {
        size = 0;
        list.removeAtIndex(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtIndexNegative() {
        size = 1;
        list.addToBack(0);
        list.removeAtIndex(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAtIndexOutOfBounds() {
        size = 2;
        list.addToBack(0);
        list.addToBack(1);
        list.removeAtIndex(2);
    }

    @Test
    public void removeAtIndexHead() {
        size = 2;
        list.addToBack(4242);
        list.addToBack(0);
        list.addToBack(1);
        assertEquals((int) list.removeAtIndex(0), 4242);
    }

    @Test
    public void removeAtIndexTail() {
        size = 2;
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(4242);
        assertEquals((int) list.removeAtIndex(2), 4242);

    }

    @Test
    public void removeAtIndexMiddle() {
        size = 2;
        list.addToBack(0);
        list.addToBack(4242);
        list.addToBack(1);
        assertEquals(4242, (int) list.removeAtIndex(1));
    }

    @Test
    public void removeAtIndexSingleton() {
        size = 0;
        list.addToBack(0);
        assertEquals((int) list.removeAtIndex(0), 0);
    }


    @Test(expected = NoSuchElementException.class)
    public void removeFromFrontEmpty() {
        size = 0;
        list.removeFromFront();
    }

    @Test()
    public void removeFromFrontSingleton() {
        size = 0;
        list.addToBack(0);
        assertEquals(0, (int) list.removeFromFront());
    }

    @Test
    public void removeFromFront() {
        size = 2;
        list.addToBack(4242);
        list.addToBack(0);
        list.addToBack(1);
        assertEquals(4242, (int) list.removeFromFront());
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFromBackEmpty() {
        size = 0;
        list.removeFromBack();
    }

    @Test()
    public void removeFromBackSingleton() {
        size = 0;
        list.addToBack(0);
        assertEquals(0, (int) list.removeFromBack());
    }

    @Test
    public void removeFromBack() {
        size = 2;
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(4242);
        assertEquals(4242, (int) list.removeFromBack());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getEmpty() {
        size = 0;
        list.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getNegativeIndex() {
        size = 2;
        list.addToBack(0);
        list.addToBack(1);
        list.get(-1);
    }

    @Test
    public void getSingleton() {
        size = 1;
        list.addToBack(0);
        assertEquals(0, (int) list.get(0));
    }

    @Test
    public void getAll() {
        size = 5;
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        assertEquals(0, (int) list.get(0));
        assertEquals(1, (int) list.get(1));
        assertEquals(2, (int) list.get(2));
        assertEquals(3, (int) list.get(3));
        assertEquals(4, (int) list.get(4));
    }

    @Test
    public void get10k() {
        size = 10000;
        for (int i = 0; i < size; i++) {
            list.addToBack(i);
        }
        for (int i = 0; i < size; i++) {
            assertEquals(i, (int) list.get(i));
        }
    }

    @Test
    public void isEmptyEmpty() {
        size = 0;
        assertTrue(list.isEmpty());
    }

    @Test
    public void isEmptyFalse() {
        size = 1;
        list.addToBack(0);
        assertFalse(list.isEmpty());
    }

    @Test
    public void clearEmpty() {
        size = 0;
        list.clear();
    }

    @Test
    public void clearSingleton() {
        size = 0;
        list.addToBack(0);
        list.clear();
    }

    @Test
    public void clearMany() {
        size = 0;
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        list.addToBack(4);
        list.clear();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastOccurrenceEmpty() {
        size = 0;
        list.removeLastOccurrence(4242);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeLastOccurrenceNull() {
        size = 1;
        list.addToBack(0);
        list.removeLastOccurrence(null);
    }

    @Test
    public void removeLastOccurrence() {
        size = 3;
        list.addToBack(0);
        list.addToBack(1);
        list.addToBack(1);
        list.addToBack(2);
        assertEquals(1, (int) list.removeLastOccurrence(1));
    }

    @Test
    public void removeLastOccurrenceCorrectReturn() {
        size = 2;

        list.addToBack(0);

        Integer firstOne = 1;
        list.addToBack(firstOne);

        Integer secondOne = 1;
        list.addToBack(secondOne);

        list.addToBack(2);

        assertSame(secondOne, list.removeLastOccurrence(1));
        assertSame(firstOne, list.removeLastOccurrence(1));
    }

    @Test
    public void toArrayEmpty() {
        size = 0;
        assertArrayEquals(list.toArray(), new Integer[0]);
    }

    @Test
    public void toArraySingleton() {
        size = 1;
        list.addToBack(0);

        assertArrayEquals(list.toArray(), new Integer[]{0});
    }

    @Test
    public void toArrayMany() {
        size = 4;

        Integer[] expected = new Integer[]{0, 1, 2, 3};
        for (Integer x : expected) {
            list.addToBack(x);
        }

        // also check the references are the same
        Object[] got = list.toArray();
        assertEquals("array lengths should match", expected.length, got.length);
        for (int i = 0; i < expected.length; i++) {
            assertSame(expected[i], got[i]);
        }
    }
}
