import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Zhen Jiang
 * @version 1.0
 * @userid zjiang330
 * @GTID 903402987
 *
 * Collaborators: n/a
 *
 * Resources: n/a
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is null");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("The length of pattern is 0");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null");
        }

        List<Integer> result = new ArrayList<>();

        int m = pattern.length();
        int n = text.length();

        if (m > n) {
            return result;
        }

        int[] failureTable = buildFailureTable(pattern, comparator);
        int i = 0;
        int j = 0;

        while (i <= n - m) {
            while (j < m && comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0) {
                j += 1;
            }
            if (j == 0) {
                i += 1;
            } else {
                if (j == m) {
                    result.add(i);
                }
                int shift = failureTable[j - 1];
                i = i + j - shift;
                j = shift;
            }
        }
        return result;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null");
        }

        int m = pattern.length();
        int[] failureTable = new int[m];

        int i = 0;
        int j = 1;

        if (m == 0) {
            return failureTable;
        }

        failureTable[0] = 0;
        while (j < m) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                failureTable[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    failureTable[j] = i;
                    j++;
                } else {
                    i = failureTable[i - 1];
                }
            }
        }

        return failureTable;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is null");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("The length of pattern is 0");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null");
        }

        List<Integer> result = new ArrayList<>();

        int m = pattern.length();
        int n = text.length();
        Map<Character, Integer> lastTable = buildLastTable(pattern);

        if (m > n) {
            return result;
        }

        int i = 0;
        while (i <= (n - m)) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0) {
                j--;
            }
            if (j == -1) {
                result.add(i);
                i += 1;
            } else {
                int shift = lastTable.getOrDefault(text.charAt(i + j), -1);
                if (shift < j) {
                    i = i + j - shift;
                } else {
                    i += 1;
                }
            }
        }

        return result;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is null");
        }

        Map<Character, Integer> lastTable = new HashMap<>();
        int m = pattern.length();

        for (int i = 0; i < m; i++) {
            lastTable.put(pattern.charAt(i), i);
        }

        return lastTable;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to find BASE^(m - 1).
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 113 hash
     * = b * 113 ^ 3 + u * 113 ^ 2 + n * 113 ^ 1 + n * 113 ^ 0 = 98 * 113 ^ 3 +
     * 117 * 113 ^ 2 + 110 * 113 ^ 1 + 110 * 113 ^ 0 = 142910419
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y =
     * (142910419 - 98 * 113 ^ 3) * 113 + 121 = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * Do NOT use Math.pow() for this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or of
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is null");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("The length of pattern is 0");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is null");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null");
        }

        List<Integer> result = new ArrayList<>();

        int m = pattern.length();
        int n = text.length();

        if (m > n) {
            return result;
        }

        int patternHash = 0;
        int textHash = 0;
        int power = 1;
        int maxPower = 0;
        for (int i = m - 1; i >= 0; i--) {
            patternHash += pattern.charAt(i) * power;
            textHash += text.charAt(i) * power;
            if (i == 0) {
                maxPower = power;
            }
            power *= BASE;
        }

        int i = 0;
        while (i <= n - m) {
            if (patternHash == textHash) {
                int j = 0;
                while (j < m && comparator.compare(text.charAt(i + j),
                        pattern.charAt(j)) == 0) {
                    j += 1;
                }
                if (j == m) {
                    result.add(i);
                }
            }

            int oldCharHash = text.charAt(i) * maxPower;
            i += 1;
            if (i <= n - m) {
                int newCharHash = text.charAt(i + m - 1);
                textHash = (textHash - oldCharHash) * BASE + newCharHash;
            }
        }

        return result;
    }
}