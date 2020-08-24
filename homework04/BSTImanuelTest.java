import org.junit.*;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class BSTImanuelTest {
    private static final int TIMEOUT = 200;
    private BST<Integer> bst;

    @Before
    public void setup() {
        bst = new BST<>();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructorNullCollection() {
        bst = new BST<>(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructorDataInCollectionIsNull() {
        List<Integer> collection = new ArrayList<>();

        collection.add(1);
        collection.add(2);
        collection.add(null);
        collection.add(4);

        bst = new BST<>(collection);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNullData() {
        bst.add(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullData() {
        bst.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveDataNotInTree() {
        /*
                      2
                     / \
                    1   3
                         \
                          4
                           \
                            5
        */

        bst.add(2);
        bst.add(1);
        bst.add(3);
        bst.add(4);
        bst.add(5);

        bst.remove(6);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNodeWithTwoChildren() {
        Integer temp = 2;

        /*
                      2             1
                     / \             \
                    1   3             3
                         \     ->      \
                          4             4
                           \             \
                            5             5
        */

        bst.add(temp);
        bst.add(1);
        bst.add(3);
        bst.add(4);
        bst.add(5);

        assertSame(temp, bst.remove(2));
        assertEquals(4, bst.size());
        assertEquals((Integer) 1, bst.getRoot().getData());
        assertEquals((Integer) 3, bst.getRoot().getRight().getData());
        assertEquals((Integer) 4, bst.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 5, bst.getRoot().getRight().getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullData() {
        bst.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetDataNotInTree() {
        /*
                       4
                    /     \
                   1       7
                    \     /
                     2   5
                      \   \
                       3   6
        */

        bst.add(4);
        bst.add(1);
        bst.add(2);
        bst.add(3);
        bst.add(7);
        bst.add(5);
        bst.add(6);

        bst.get(8);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNullData() {
        bst.contains(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testFindPathBetweenNullData() {
        /*
                      1
                     / \
                    0   2
        */

        bst.add(1);
        bst.add(0);
        bst.add(2);

        bst.findPathBetween(null, 0);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testFindPathBetweenDataNotInTree() {
        /*
                      1
                     / \
                    0   2
        */

        bst.add(1);
        bst.add(0);
        bst.add(2);

        bst.findPathBetween(0, 3);
    }

    @Test(timeout = TIMEOUT)
    public void testPathBetweenMoreComplexTree() {
        /*
                          50
                      /        \
                    25         75
                  /    \
                 12    37
                /  \    \
              10   15   40
                  /
                13
         */

        bst.add(50);
        bst.add(25);
        bst.add(75);
        bst.add(12);
        bst.add(37);
        bst.add(10);
        bst.add(15);
        bst.add(40);
        bst.add(13);

        List<Integer> expected = new ArrayList<>();
        expected.add(13);
        expected.add(15);
        expected.add(12);
        expected.add(25);
        expected.add(37);
        expected.add(40);
        assertEquals(expected, bst.findPathBetween(13, 40));

        expected.clear();
        expected.add(10);
        expected.add(12);
        expected.add(15);
        expected.add(13);
        assertEquals(expected, bst.findPathBetween(10, 13));

        expected.clear();
        expected.add(13);
        expected.add(15);
        expected.add(12);
        expected.add(25);
        expected.add(50);
        expected.add(75);
        assertEquals(expected, bst.findPathBetween(13, 75));

        expected.clear();
        expected.add(75);
        expected.add(50);
        expected.add(25);
        expected.add(37);
        expected.add(40);
        assertEquals(expected, bst.findPathBetween(75, 40));

        expected.clear();
        expected.add(13);
        expected.add(15);
        expected.add(12);
        expected.add(25);
        expected.add(50);
        assertEquals(expected, bst.findPathBetween(13, 50));
    }
}