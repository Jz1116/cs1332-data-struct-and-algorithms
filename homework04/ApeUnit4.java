import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ApeUnit4 {
    private BST<Integer> bst = new BST<Integer>();
    private BST<Integer> bst2 = new BST<Integer>();
    private BST<Integer> bst4 = new BST<Integer>();

    //please occasionally put print statements so people can tell what's going wrong when they fail tests
    //also pleeeeeeeaaaase comment what each segment is testing. make cases explicit.
    @Before
    public void runBefore() {
        bst = new BST<Integer>();
        bst2 = new BST<Integer>();
    }

    @Test
    public void APETestAdd() {
        bst.add(10);
        System.out.println(bst.size());
        bst.add(5);
        System.out.println(bst.size());
        bst.add(12);
        System.out.println(bst.size());
        bst.add(15);
        assertEquals("Make sure you're iterating your size variable.", 4, bst.size());

        bst.add(5);
        assertEquals("You should not be adding in any duplicate nodes.", 4, bst.size());

        bst.add(11);
        assertEquals("The integer 10 should be the root node.", (Integer) 10, bst.getRoot().getData());
        System.out.println(bst.getRoot());
        System.out.println(bst.getRoot().getLeft());
        System.out.println(bst.getRoot().getRight());
        assertEquals("The left child of the root node should be 5.", (Integer) 5, bst.getRoot().getLeft().getData());
        assertEquals("The right child of the root node should be 12.", (Integer) 12, bst.getRoot().getRight().getData());
        assertEquals("The node containing integer 11 is misplaced.", (Integer) 11, bst.getRoot().getRight().getLeft().getData());
        assertEquals("The node containing integer 15 is misplaced.", (Integer) 15, bst.getRoot().getRight().getRight().getData());
    }

    @Test(expected = IllegalArgumentException.class)
    public void APETestAddException() {
        bst.add(null);
    }


    @Test
    public void remove() {
        //tree 1
        /* this test is extremely excessive but redundancy cant hurt in tests
        because of recursion, just one case tests all similar cases
        ex: the 5-3-4 case also tests 16-14-15, 14-12-13, and 24-22-24
        ex: 7-10-11 also tests 2-1-5 and 22-21-24 and more
        as long as you used recursion, we can be very sure this test is comprehensive
                         10
                      7/    \11
                    6/ \8      \12
                   2/    \9      \16
                  1/\5         14/  \18
                 0/3/         13/\15   \19 */
        bst.add(10);
        bst.add(7);
        bst.add(11);
        bst.add(6);
        bst.add(8);
        bst.add(12);
        bst.add(2);
        bst.add(9);
        bst.add(16);
        bst.add(1);
        bst.add(5);
        bst.add(14);
        bst.add(18);
        bst.add(0);
        bst.add(3);
        bst.add(13);
        bst.add(15);
        bst.add(19);

        System.out.println(bst.size());
        assertEquals(18, bst.size());
        //remove a leaf
        assertEquals(19, (long) bst.remove(19));
        assertEquals(17, bst.size());
        assertEquals(18,
                (long) bst.getRoot().getRight().getRight().getRight().getRight().getData());
        assertNull(bst.getRoot().getRight().getRight().getRight().getRight().getRight());

        //remove a single child node
        assertEquals(1, (long) bst.remove(1));
        assertEquals(16, bst.size());
        assertNull(bst.getRoot().getLeft().getLeft().getLeft().getLeft().getLeft());
        assertEquals(0, (long) bst.getRoot().getLeft().getLeft().getLeft().getLeft().getData());

        //remove 16 (two children remove case)
        assertEquals(16, (long) bst.remove(16));
        assertEquals(15, bst.size());
        assertNull(bst.getRoot().getRight().getRight().getRight().getLeft().getRight());
        assertEquals(15, (long) bst.getRoot().getRight().getRight().getRight().getData());
        assertEquals(18, (long) bst.getRoot().getRight().getRight().getRight().getRight().getData());

        //remove 7 (two children and a one child remove for pred)
        assertEquals(6, (long) bst.remove(6));
        assertEquals(14, bst.size());
        assertEquals(7, (long) bst.remove(7));
        assertEquals(13, bst.size());
        assertNull(bst.getRoot().getLeft().getLeft().getRight().getLeft());
        assertEquals(3, (long) bst.getRoot().getLeft().getLeft().getRight().getData());

        //remove root (two children case)
        assertEquals(10, (long) bst.remove(10));
        assertEquals(12, bst.size());
        assertNull(bst.getRoot().getLeft().getRight().getRight());
        assertEquals(9, (long) bst.getRoot().getData());
        assertEquals(11, (long) bst.getRoot().getRight().getData());

        //remove root(one child case)
        bst.clear();
        bst.add(1);
        bst.add(2);
        assertEquals(1, (long) bst.remove(1));
        assertNull(bst.getRoot().getRight());
        assertEquals(1, bst.size());
        assertEquals(2, (long) bst.getRoot().getData());

        //remove root (no children case)
        assertEquals(2, (long) bst.remove(2));
        assertEquals(0, (long) bst.size());
    }

    @Test (expected = NoSuchElementException.class)
    public void removeEmpty() {
        bst.remove(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void removeNull() {
        bst.add(1);
        bst.remove(null);
    }


    @Test
    public void get() {
        bst.add(1);
        bst.add(2);
        bst.add(0);

        assertEquals(1, (long) bst.get(1));
        //dont be using remove for your get
        assertNotNull(bst.getRoot());
        assertEquals(2, (long) bst.get(2));
        assertEquals(3, bst.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void getillegal() {
        bst.get(null);
    }

    @Test
    public void contains() {
        assertFalse(bst.contains(1));
        bst.add(1);
        assertEquals((Integer) 1, bst.getRoot().getData());
        assertTrue(bst.contains(1));
        bst.add(1);
        assertEquals((Integer) 1, bst.getRoot().getData());
        //it's quite easy to mess up add or remove for the case of root, that's what I'm testing.
        assertTrue(bst.contains(1));
        bst.remove(1);
        System.out.println(bst.getRoot());
        assertFalse(bst.contains(1));
        //tested duplicate case

        bst.add(2);
        assertTrue(bst.contains(2));
        //added 2, test for it
        assertTrue(bst.contains(2));
        assertFalse(bst.contains(1));
        //removed 1, 2 should be root, assert case

        bst.add(5);
        bst.add(7);
        bst.add(6);
        /*
               2
                 5
                   7
                 6
        */
        assertTrue(bst.contains(2));
        assertTrue(bst.contains(5));
        assertTrue(bst.contains(7));
        assertTrue(bst.contains(6));
        //make sure they're all there
        bst.add(1);
        bst.add(-3);
        bst.add(-2);
        bst.add(0);
        assertTrue(bst.contains(1));
        assertTrue(bst.contains(-2));
        assertTrue(bst.contains(-3));
        assertTrue(bst.contains(0));
        /*
               2
            1     5
         -3   0      7
           -2      6
        */
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsNull() {
        //make sure you implemented the null throwing correctly.
        bst.contains(null);
    }

    @Test
    public void preorder() {
        //tree 1
        /* this test is extremely excessive but redundancy cant hurt in tests
        because of recursion, just one case tests all similar cases
        ex: the 5-3-4 case also tests 16-14-15, 14-12-13, and 24-22-24
        ex: 7-10-11 also tests 2-1-5 and 22-21-24 and more
        as long as you used recursion, we can be very sure this test is comprehensive
                         10
                      7/    \11
                    6/ \8      \12
                   2/    \9      \16
                  1/\5         14/  \18
                 0/3/         13/\15   \19
               -5/   \4                  \25
             -6/\-2                   22/    \27
           -10/-3/\-1                21/\24 26/\30
         -11/ \-9                               \40
               \-7                            39/\41
             -8/                             37/  \42
                                               \38
         */
        int[] expected = {10, 7, 6, 2, 1, 0, -5, -6, -10, -11, -9, -7, -8, -2, -3, -1, 5, 3, 4, 8, 9,
                11, 12, 16, 14, 13, 15, 18, 19, 25, 22, 21, 24, 27, 26, 30, 40, 39, 37, 38, 41, 42};
        System.out.println("len = " + expected.length);
        bst.add(10);
        bst.add(7);
        bst.add(11);
        bst.add(6);
        bst.add(8);
        bst.add(12);
        bst.add(2);
        bst.add(9);
        bst.add(16);
        bst.add(1);
        bst.add(5);
        bst.add(14);
        bst.add(18);
        bst.add(0);
        bst.add(3);
        bst.add(13);
        bst.add(15);
        bst.add(19);
        bst.add(-5);
        bst.add(4);
        bst.add(25);
        bst.add(-6);
        bst.add(-2);
        bst.add(22);
        bst.add(27);
        bst.add(-10);
        bst.add(-3);
        bst.add(-1);
        bst.add(21);
        bst.add(24);
        bst.add(26);
        bst.add(30);
        bst.add(-11);
        bst.add(-9);
        bst.add(40);
        bst.add(-7);
        bst.add(39);
        bst.add(41);
        bst.add(-8);
        bst.add(37);
        bst.add(42);
        bst.add(38);
        List<Integer> preordered = bst.preorder();
        System.out.println(Arrays.toString(expected));
        System.out.println(preordered);
        int i = 0;
        Boolean stilltrue = true;
        for (int x: expected) {
            System.out.println(i);
            if (preordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //tree 2
        /*
                5
              4/
             3/
            2/
         */
        bst.clear();
        bst.add(5);
        bst.add(4);
        bst.add(3);
        bst.add(2);
        preordered = bst.preorder();
        int[] expected2 = {5, 4, 3, 2};
        i = 0;
        for (int x: expected2) {
            if (preordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //tree 3
        /*
               0
                \1
                  \2
                    \3
         */
        bst.clear();
        bst.add(0);
        bst.add(1);
        bst.add(2);
        bst.add(3);
        preordered = bst.preorder();
        int[] expected3 = {0, 1, 2, 3};
        i = 0;
        for (int x: expected3) {
            if (preordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //tree 4
        /*
               0
         */
        i = 0;
        bst.clear();
        bst.add(0);
        preordered = bst.preorder();
        int[] expected4 = {0};
        for(int x: expected4) {
            if(preordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //empty tree
        bst.clear();
        assertEquals(bst.preorder(), Collections.emptyList());
    }

    @Test
    public void inorder() {
        //tree 1
        /* this test is extremely excessive but redundancy cant hurt in tests
        because of recursion, just one case tests all similar cases
        ex: the 5-3-4 case also tests 16-14-15, 14-12-13, and 24-22-24
        ex: 7-10-11 also tests 2-1-5 and 22-21-24 and more
        as long as you used recursion, we can be very sure this test is comprehensive
                         10
                      7/    \11
                    6/ \8      \12
                   2/    \9      \16
                  1/\5         14/  \18
                 0/3/         13/\15   \19
               -5/   \4                  \25
             -6/\-2                   22/    \27
           -10/-3/\-1                21/\24 26/\30
         -11/ \-9                               \40
               \-7                            39/\41
             -8/                             37/  \42
                                               \38
         */
        int[] expected = {-11, -10, -9, -8, -7, -6, -5, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                12, 13, 14, 15, 16, 18, 19, 21, 22, 24, 25, 26, 27, 30, 37, 38, 39, 40, 41, 42};
        bst.add(10);
        bst.add(7);
        bst.add(11);
        bst.add(6);
        bst.add(8);
        bst.add(12);
        bst.add(2);
        bst.add(9);
        bst.add(16);
        bst.add(1);
        bst.add(5);
        bst.add(14);
        bst.add(18);
        bst.add(0);
        bst.add(3);
        bst.add(13);
        bst.add(15);
        bst.add(19);
        bst.add(-5);
        bst.add(4);
        bst.add(25);
        bst.add(-6);
        bst.add(-2);
        bst.add(22);
        bst.add(27);
        bst.add(-10);
        bst.add(-3);
        bst.add(-1);
        bst.add(21);
        bst.add(24);
        bst.add(26);
        bst.add(30);
        bst.add(-11);
        bst.add(-9);
        bst.add(40);
        bst.add(-7);
        bst.add(39);
        bst.add(41);
        bst.add(-8);
        bst.add(37);
        bst.add(42);
        bst.add(38);
        List<Integer> inordered = bst.inorder();
        System.out.println("expected inorder :" +  Arrays.toString(expected));
        System.out.println("your inorder     :" + inordered);
        int i = 0;
        Boolean stilltrue = true;
        for (int x: expected) {
            if (inordered.get(i) != x) {
                stilltrue = false;
            };
            i++;
        }
        assertTrue(stilltrue);

        //tree 2
        /*
                5
              4/
             3/
            2/
         */
        bst.clear();
        bst.add(5);
        bst.add(4);
        bst.add(3);
        bst.add(2);
        inordered = bst.inorder();
        int[] expected2 = {2, 3, 4, 5};
        System.out.println("expected inorder :" +  Arrays.toString(expected2));
        System.out.println("your inorder     :" + inordered);
        i = 0;
        for (int x: expected2) {
            if (inordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //tree 3
        /*
               0
                \1
                  \2
                    \3
         */
        bst.clear();
        bst.add(0);
        bst.add(1);
        bst.add(2);
        bst.add(3);
        inordered = bst.inorder();
        int[] expected3 = {0, 1, 2, 3};
        System.out.println("expected inorder :" +  Arrays.toString(expected3));
        System.out.println("your inorder     :" + inordered);
        i = 0;
        for (int x: expected3) {
            if (inordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //tree 4
        /*
               0
         */
        i = 0;
        bst.clear();
        bst.add(0);
        inordered = bst.inorder();
        int[] expected4 = {0};
        System.out.println("expected inorder :" +  Arrays.toString(expected4));
        System.out.println("your inorder     :" + inordered);
        for(int x: expected4) {
            if(inordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //empty tree
        bst.clear();
        assertEquals(bst.inorder(), Collections.emptyList());
    }

    @Test
    public void postorder() {
        //tree 1
        /* this test is extremely excessive but redundancy cant hurt in tests
        because of recursion, just one case tests all similar cases
        ex: the 5-3-4 case also tests 16-14-15, 14-12-13, and 24-22-24
        ex: 7-10-11 also tests 2-1-5 and 22-21-24 and more
        as long as you used recursion, we can be very sure this test is comprehensive
                         10
                      7/    \11
                    6/ \8      \12
                   2/    \9      \16
                  1/\5         14/  \18
                 0/3/         13/\15   \19
               -5/   \4                  \25
             -6/\-2                   22/    \27
           -10/-3/\-1                21/\24 26/\30
         -11/ \-9                               \40
               \-7                            39/\41
             -8/                             37/  \42
                                               \38
         */
        int[] expected = {-11, -8, -7, -9, -10, -6, -3, -1, -2, -5, 0, 1, 4, 3, 5, 2, 6, 9, 8, 7, 13, 15, 14, 21,
                24, 22, 26, 38, 37, 39, 42, 41, 40, 30, 27, 25, 19, 18, 16, 12, 11, 10};
        bst.add(10);
        bst.add(7);
        bst.add(11);
        bst.add(6);
        bst.add(8);
        bst.add(12);
        bst.add(2);
        bst.add(9);
        bst.add(16);
        bst.add(1);
        bst.add(5);
        bst.add(14);
        bst.add(18);
        bst.add(0);
        bst.add(3);
        bst.add(13);
        bst.add(15);
        bst.add(19);
        bst.add(-5);
        bst.add(4);
        bst.add(25);
        bst.add(-6);
        bst.add(-2);
        bst.add(22);
        bst.add(27);
        bst.add(-10);
        bst.add(-3);
        bst.add(-1);
        bst.add(21);
        bst.add(24);
        bst.add(26);
        bst.add(30);
        bst.add(-11);
        bst.add(-9);
        bst.add(40);
        bst.add(-7);
        bst.add(39);
        bst.add(41);
        bst.add(-8);
        bst.add(37);
        bst.add(42);
        bst.add(38);
        List<Integer> postordered = bst.postorder();
        System.out.println("expected postorder :" +  Arrays.toString(expected));
        System.out.println("your postorder     :" + postordered);
        int i = 0;
        Boolean stilltrue = true;
        for (int x: expected) {
            if (postordered.get(i) != x) {
                stilltrue = false;
            };
            i++;
        }
        assertTrue(stilltrue);

        //tree 2
        /*
                5
              4/
             3/
            2/
         */
        bst.clear();
        bst.add(5);
        bst.add(4);
        bst.add(3);
        bst.add(2);
        postordered = bst.postorder();
        int[] expected2 = {2, 3, 4, 5};
        System.out.println("expected postorder :" +  Arrays.toString(expected2));
        System.out.println("your postorder     :" + postordered);
        i = 0;
        for (int x: expected2) {
            if (postordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //tree 3
        /*
               0
                \1
                  \2
                    \3
         */
        bst.clear();
        bst.add(0);
        bst.add(1);
        bst.add(2);
        bst.add(3);
        postordered = bst.postorder();
        int[] expected3 = {3, 2, 1, 0};
        System.out.println("expected postorder :" +  Arrays.toString(expected3));
        System.out.println("your postorder     :" + postordered);
        i = 0;
        for (int x: expected3) {
            if (postordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //tree 4
        /*
               0
         */
        i = 0;
        bst.clear();
        bst.add(0);
        postordered = bst.postorder();
        int[] expected4 = {0};
        System.out.println("expected postorder :" +  Arrays.toString(expected4));
        System.out.println("your postorder     :"+ postordered);
        for(int x: expected4) {
            if(postordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //empty tree
        bst.clear();
        assertEquals(bst.postorder(), Collections.emptyList());
    }

    @Test
    public void levelorder() {
        //tree 1
        /* this test is extremely excessive but redundancy cant hurt in tests
        because of recursion, just one case tests all similar cases
        ex: the 5-3-4 case also tests 16-14-15, 14-12-13, and 24-22-24
        ex: 7-10-11 also tests 2-1-5 and 22-21-24 and more
        as long as you used recursion, we can be very sure this test is comprehensive
                         10
                      7/    \11
                    6/ \8      \12
                   2/    \9      \16
                  1/\5         14/  \18
                 0/3/         13/\15   \19
               -5/   \4                  \25
             -6/\-2                   22/    \27
           -10/-3/\-1                21/\24 26/\30
         -11/ \-9                               \40
               \-7                            39/\41
             -8/                             37/  \42
                                               \38
         */
        int[] expected = {10, 7, 11, 6, 8, 12, 2, 9, 16, 1, 5, 14, 18, 0, 3, 13, 15, 19, -5, 4, 25, -6, -2, 22, 27,
                -10, -3, -1, 21, 24, 26, 30, -11, -9, 40, -7, 39, 41, -8, 37, 42, 38};
        bst.add(10);
        bst.add(7);
        bst.add(11);
        bst.add(6);
        bst.add(8);
        bst.add(12);
        bst.add(2);
        bst.add(9);
        bst.add(16);
        bst.add(1);
        bst.add(5);
        bst.add(14);
        bst.add(18);
        bst.add(0);
        bst.add(3);
        bst.add(13);
        bst.add(15);
        bst.add(19);
        bst.add(-5);
        bst.add(4);
        bst.add(25);
        bst.add(-6);
        bst.add(-2);
        bst.add(22);
        bst.add(27);
        bst.add(-10);
        bst.add(-3);
        bst.add(-1);
        bst.add(21);
        bst.add(24);
        bst.add(26);
        bst.add(30);
        bst.add(-11);
        bst.add(-9);
        bst.add(40);
        bst.add(-7);
        bst.add(39);
        bst.add(41);
        bst.add(-8);
        bst.add(37);
        bst.add(42);
        bst.add(38);

        List<Integer> levelordered = bst.levelorder();
        System.out.println("expected levelorder :" +  Arrays.toString(expected));
        System.out.println("your levelorder     :" + levelordered);
        int i = 0;
        Boolean stilltrue = true;
        for (int x: expected) {
            if (levelordered.get(i) != x) {
                stilltrue = false;
            };
            i++;
        }
        assertTrue(stilltrue);

        //tree 2
        /*
                5
              4/
             3/
            2/
         */
        bst.clear();
        bst.add(5);
        bst.add(4);
        bst.add(3);
        bst.add(2);
        levelordered = bst.levelorder();
        int[] expected2 = {5, 4, 3, 2};
        System.out.println("expected levelorder :" +  Arrays.toString(expected2));
        System.out.println("your levelorder     :" + levelordered);
        i = 0;
        for (int x: expected2) {
            if (levelordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //tree 3
        /*
               0
                \1
                  \2
                    \3
         */
        bst.clear();
        bst.add(0);
        bst.add(1);
        bst.add(2);
        bst.add(3);
        levelordered = bst.levelorder();
        int[] expected3 = {0, 1, 2, 3};
        System.out.println("expected levelorder :" +  Arrays.toString(expected3));
        System.out.println("your levelorder     :" + levelordered);
        i = 0;
        for (int x: expected3) {
            if (levelordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //tree 4
        /*
               0
         */
        i = 0;
        bst.clear();
        bst.add(0);
        levelordered = bst.levelorder();
        int[] expected4 = {0};
        System.out.println("expected levelorder :" +  Arrays.toString(expected4));
        System.out.println("your levelorder     :" + levelordered);
        for(int x: expected4) {
            if(levelordered.get(i) != x) {
                stilltrue = false;
            }
            i++;
        }
        assertTrue(stilltrue);

        //empty tree
        bst.clear();
        assertEquals(bst.levelorder(), Collections.emptyList());
    }

    @Test
    public void height() {
        bst4 = new BST<Integer>();
        bst4.add(5);
        bst4.add(6);
        bst4.add(10);
        bst4.add(2);
        bst4.add(1);
        bst4.add(75);
        bst4.add(4);
        bst4.add(55);
        bst4.add(58);
        bst4.add(13);
        bst4.add(34);
        assertEquals("Empty tree should have height of -1", -1, bst.height());
        assertEquals("BST height not properly updated.", 6, bst4.height());
    }

    @Test
    public void clear() {
        //tree 1
        /* this test is extremely excessive but redundancy cant hurt in tests
        because of recursion, just one case tests all similar cases
        ex: the 5-3-4 case also tests 16-14-15, 14-12-13, and 24-22-24
        ex: 7-10-11 also tests 2-1-5 and 22-21-24 and more
        as long as you used recursion, we can be very sure this test is comprehensive
                         10
                      7/    \11
                    6/ \8      \12
                   2/    \9      \16
                  1/\5         14/  \18
                 0/3/         13/\15   \19
               -5/   \4                  \25
             -6/\-2                   22/    \27
           -10/-3/\-1                21/\24 26/\30
         -11/ \-9                               \40
               \-7                            39/\41
             -8/                             37/  \42
                                               \38
         */
        bst.add(10);
        bst.add(7);
        bst.add(11);
        bst.add(6);
        bst.add(8);
        bst.add(12);
        bst.add(2);
        bst.add(9);
        bst.add(16);
        bst.add(1);
        bst.add(5);
        bst.add(14);
        bst.add(18);
        bst.add(0);
        bst.add(3);
        bst.add(13);
        bst.add(15);
        bst.add(19);
        bst.add(-5);
        bst.add(4);
        bst.add(25);
        bst.add(-6);
        bst.add(-2);
        bst.add(22);
        bst.add(27);
        bst.add(-10);
        bst.add(-3);
        bst.add(-1);
        bst.add(21);
        bst.add(24);
        bst.add(26);
        bst.add(30);
        bst.add(-11);
        bst.add(-9);
        bst.add(40);
        bst.add(-7);
        bst.add(39);
        bst.add(41);
        bst.add(-8);
        bst.add(37);
        bst.add(42);
        bst.add(38);

        bst.clear();
        assertNull(bst.getRoot());
        assertEquals(0, bst.size());
    }

    @Test
    public void findPathBetween() {
        //tree 1
        /* this test is extremely excessive but redundancy cant hurt in tests
        because of recursion, just one case tests all similar cases
        ex: the 5-3-4 case also tests 16-14-15, 14-12-13, and 24-22-24
        ex: 7-10-11 also tests 2-1-5 and 22-21-24 and more
        as long as you used recursion, we can be very sure this test is comprehensive
                         10
                      7/    \11
                    6/ \8      \12
                   2/    \9      \16
                  1/\5         14/  \18
                 0/3/         13/\15   \19
               -5/   \4                  \25
             -6/\-2                   22/    \27
           -10/-3/\-1                21/\24 26/\30
         -11/ \-9                               \40
               \-7                            39/\41
             -8/                             37/  \42
                                               \38
         */
        Integer[] expected2to10arr = {2, 6, 7, 10};
        Integer[] expected10to2arr = {10, 7, 6, 2};
        Integer[] expected10to16arr = {10, 11, 12, 16};
        Integer[] expected16to10arr = {16, 12, 11, 10};
        Integer[] expected38to42arr = {38, 37, 39, 40, 41, 42};
        Integer[] expected42to38arr = {42, 41, 40, 39, 37, 38};
        bst.add(10);
        bst.add(7);
        bst.add(11);
        bst.add(6);
        bst.add(8);
        bst.add(12);
        bst.add(2);
        bst.add(9);
        bst.add(16);
        bst.add(1);
        bst.add(5);
        bst.add(14);
        bst.add(18);
        bst.add(0);
        bst.add(3);
        bst.add(13);
        bst.add(15);
        bst.add(19);
        bst.add(-5);
        bst.add(4);
        bst.add(25);
        bst.add(-6);
        bst.add(-2);
        bst.add(22);
        bst.add(27);
        bst.add(-10);
        bst.add(-3);
        bst.add(-1);
        bst.add(21);
        bst.add(24);
        bst.add(26);
        bst.add(30);
        bst.add(-11);
        bst.add(-9);
        bst.add(40);
        bst.add(-7);
        bst.add(39);
        bst.add(41);
        bst.add(-8);
        bst.add(37);
        bst.add(42);
        bst.add(38);

        //try this to print them:
        //Arrays.toString(expected2to10arr); or
        //Arrays.toString(bst.findPathBetween(2, 10).toArray());
        Object[] a2to10 = bst.findPathBetween(2, 10).toArray();
        assertArrayEquals(a2to10, expected2to10arr);
        assertArrayEquals(expected10to2arr, bst.findPathBetween(10, 2).toArray());
        assertArrayEquals(expected10to16arr, bst.findPathBetween(10, 16).toArray());
        assertArrayEquals(expected16to10arr, bst.findPathBetween(16, 10).toArray());
        assertArrayEquals(expected38to42arr, bst.findPathBetween(38, 42).toArray());
        assertArrayEquals(expected42to38arr, bst.findPathBetween(42, 38).toArray());
    }
}
