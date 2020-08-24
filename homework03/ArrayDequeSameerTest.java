import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * This is a fairly comprehensive set of tests for the ArrayDeque.
 * It is horribly written and scary to read.
 * Use at your own risk.
 *
 * Some testing conventions:
 * Values >= 0 are auto-generated values that are equal to the un-modded index of where they are initially located
 * "-1" is an inserted value that we want to track the location of.
 * @author Sameer Suri
 * @version 1.0
 */
@SuppressWarnings({"CheckStyle", "ConfusingArgumentToVarargsMethod", "SameParameterValue"})
public class ArrayDequeSameerTest {
    private ArrayDeque<Integer> deque;

    @Before
    public void setup() {
        deque = new ArrayDeque<>();
    }

    // Misc
    @Test
    public void testInitialization() {
        assertSame(0, getFront());
        assertSame(0, deque.size());
        assertSame(11, arrLen());
    }

    // addFront()
    @Test
    public void testAddFrontResize() {
        generateValues(0, 11);
        int oldSize = deque.size();
        int oldArrLen = arrLen();
        deque.addFirst(-1);
        int newSize = deque.size();
        int newArrLen = arrLen();

        assertSame(oldSize + 1, newSize);
        assertSame(oldArrLen * 2, newArrLen);

        for (int i = 0 ; i < 11; i++) {
            deque.addFirst(-1);
        }
        int newerSize = deque.size();
        int newerArrLen = arrLen();

        assertSame(newSize + 11, newerSize);
        assertSame(newArrLen * 2, newerArrLen);
    }

    @Test
    public void testAddFrontEmpty() {
        deque.addFirst(-1);
        assertSame(mod(-1, arrLen()), getFront());
        assertSame(1, deque.size());
        verifyValues(nulls(10), -1);
    }

    @Test
    public void testAddFrontFull() {
        generateValues(0, 11);
        deque.addFirst(-1);
        assertSame(0, getFront());
        assertSame(12, deque.size());
        verifyValues(range(-1, 10), nulls(10));
    }

    @Test
    public void testAddFrontFullOffset() {
        generateValues(1, 11);
        assertSame(1, getFront());
        assertSame(11, deque.size());
        deque.addFirst(-1);
        assertSame(0, getFront());
        assertSame(12, deque.size());
        verifyValues(-1, range(1, 11), nulls(10));
    }

    @Test
    public void testAddFrontNotFull() {
        generateValues(0, 10);
        deque.addFirst(-1);
        assertSame(mod(-1, arrLen()), getFront());
        assertSame(11, deque.size());
        verifyValues(range(0, 9), -1);
    }

    @Test
    public void testAddFrontNotFullOffset() {
        generateValues(2, 9);
        deque.addFirst(-1);
        assertSame(1, getFront());
        assertSame(10, deque.size());
        verifyValues(null, -1, range(2, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFrontNullElement() {
        deque.addFirst(null);
    }

    // addLast()
    @Test
    public void testAddLastResize() {
        generateValues(0, 11);
        int oldSize = deque.size();
        int oldArrLen = arrLen();
        deque.addLast(-1);
        int newSize = deque.size();
        int newArrLen = arrLen();

        assertSame(oldSize + 1, newSize);
        assertSame(oldArrLen * 2, newArrLen);

        for (int i = 0 ; i < 11; i++) {
            deque.addLast(-1);
        }
        int newerSize = deque.size();
        int newerArrLen = arrLen();

        assertSame(newSize + 11, newerSize);
        assertSame(newArrLen * 2, newerArrLen);
    }

    @Test
    public void testAddLastEmpty() {
        deque.addLast(1);
        assertSame(0, getFront());
        assertSame(1, deque.size());
        verifyValues(1, nulls(10));
    }

    @Test
    public void testAddLastFull() {
        generateValues(0, 11);
        deque.addLast(-1);
        assertSame(0, getFront());
        assertSame(12, deque.size());
        verifyValues(range(0, 10), -1, nulls(10));
    }

    @Test
    public void testAddLastFullOffset() {
        generateValues(1, 11);
        deque.addLast(-1);
        assertSame(0, getFront());
        assertSame(12, deque.size());
        verifyValues(range(1, 11), -1, nulls(10));
    }

    @Test
    public void testAddLastNotFull() {
        generateValues(0, 10);
        deque.addLast(-1);
        assertSame(0, getFront());
        assertSame(11, deque.size());
        verifyValues(range(0, 9), -1);
    }

    @Test
    public void testAddLastNotFullOffset() {
        generateValues(1, 10);
        deque.addLast(-1);
        assertSame(1, getFront());
        assertSame(11, deque.size());
        verifyValues(-1, range(1, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLastNullElement() {
        deque.addLast(null);
    }

    // removeFirst()
    @Test
    public void testRemoveFirst() {
        generateValues(0, 11);
        int val = deque.removeFirst();
        assertSame(1, getFront());
        assertSame(10, deque.size());
        assertSame(0, val);
        verifyValues(null, range(1, 10));
    }

    @Test
    public void testRemoveFirstOffset() {
        generateValues(10, 11);
        int val = deque.removeFirst();
        assertSame(0, getFront());
        assertSame(10, deque.size());
        assertSame(10, val);
        verifyValues(range(11, 20), null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstEmpty() {
        deque.removeFirst();
    }

    // removeLast()
    @Test
    public void testRemoveLast() {
        generateValues(0, 11);
        int val = deque.removeLast();
        assertSame(0, getFront());
        assertSame(10, deque.size());
        assertSame(10, val);
        verifyValues(range(0, 9), null);
    }

    @Test
    public void testRemoveLastOffset() {
        generateValues(10, 11);
        int val = deque.removeLast();
        assertSame(10, getFront());
        assertSame(10, deque.size());
        assertSame(20, val);
        verifyValues(range(11, 19), null, 10);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastEmpty() {
        deque.removeLast();
    }

    // getFirst()
    @Test
    public void testGetFirst() {
        generateValues(0, 11);
        int val = deque.getFirst();
        assertSame(0, getFront());
        assertSame(11, deque.size());
        assertSame(0, val);
        verifyValues(range(0, 10));
    }

    @Test
    public void testGetFirstOffset() {
        generateValues(10, 11);
        int val = deque.getFirst();
        assertSame(10, getFront());
        assertSame(11, deque.size());
        assertSame(10, val);
        verifyValues(range(11, 20), 10);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetFirstEmpty() {
        deque.getFirst();
    }

    // getLast()
    @Test
    public void testGetLast() {
        generateValues(0, 11);
        int val = deque.getLast();
        assertSame(0, getFront());
        assertSame(11, deque.size());
        assertSame(10, val);
        verifyValues(range(0, 10));
    }

    @Test
    public void testGetLastOffset() {
        generateValues(10, 11);
        int val = deque.getLast();
        assertSame(10, getFront());
        assertSame(11, deque.size());
        assertSame(20, val);
        verifyValues(range(11, 20), 10);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetLastEmpty() {
        deque.getLast();
    }

    // HELPER METHODS
    private int getFront() {
        try {
            Field f = ArrayDeque.class.getDeclaredField("front");
            f.setAccessible(true);
            return (Integer) f.get(deque);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int arrLen() {
        // noinspection RedundantCast
        return ((Object[]) deque.getBackingArray()).length;
    }

    private void resize(int size) {
        try {
            // noinspection RedundantCast
            int len = ((Object[]) deque.getBackingArray()).length;
            while (len < size) {
                len *= 2;
            }
            Field f = ArrayDeque.class.getDeclaredField("backingArray");
            f.setAccessible(true);
            f.set(deque, new Object[len]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setFront(int front) {
        try {
            Field f = ArrayDeque.class.getDeclaredField("front");
            f.setAccessible(true);
            f.set(deque, front);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setSize(int size) {
        try {
            Field f = ArrayDeque.class.getDeclaredField("size");
            f.setAccessible(true);
            f.set(deque, size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generateValues(int front, int size) {
        // Do some fun direct array writes
        // This allows us to set up our tests without actually assuming that your public API methods work
        resize(size);

        Object[] backingArray = deque.getBackingArray();
        for (int i = front; i < front + size; i++) {
            int target = mod(i, backingArray.length);
            backingArray[target] = i;
        }

        setFront(front);
        setSize(size);
    }

    private void verifyValues(Object... val) {
        List<Integer> flattened = new java.util.ArrayList<>();
        for (Object o : val) {
            if (o == null) {
                flattened.add(null);
            }
            else if (o instanceof Integer) {
                flattened.add((Integer) o);
            }
            else if (o instanceof int[]) {
                for (int i : ((int[]) o)) {
                    flattened.add(i);
                }
            }
            else if (o instanceof Integer[]) {
                flattened.addAll(Arrays.asList((Integer[]) o));
            }
            else {
                throw new IllegalArgumentException("verifyValues only works with integer types, provided was: " + o.getClass().getSimpleName());
            }
        }

        Object[] backingArray = deque.getBackingArray();
        assertEquals("Size of backing array does not match number of elements passed to verification method", flattened.size(), backingArray.length);

        for (int i = 0; i < flattened.size(); i++) {
            assertEquals(flattened.get(i), backingArray[i]);
        }
    }

    private Integer[] nulls(int num) {
        return new Integer[num];
    }

    private Integer[] range(int startInclusive, int endInclusive) {
        return IntStream.range(startInclusive, endInclusive + 1).boxed().toArray(Integer[]::new);
    }
    private static int mod(int index, int modulo) {
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive");
        }
        int newIndex = index % modulo;
        return newIndex >= 0 ? newIndex : newIndex + modulo;
    }
}
