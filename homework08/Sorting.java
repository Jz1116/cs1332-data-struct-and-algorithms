import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null");
        }

        int length = arr.length;
        for (int i  = 0; i < length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < length; j++) {
                if (comparator.compare(arr[minIndex], arr[j]) > 0) {
                    minIndex = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }

    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null");
        }

        int length = arr.length;

        for (int i = 1; i < length; i++) {
            int j = i;
            while (j > 0
                    && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null");
        }
        boolean swapsMade = true;
        int startInd = 0;
        int endInd = arr.length - 1;

        while (startInd < endInd && swapsMade) {
            swapsMade = false;
            int tempEnd = endInd - 1;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    tempEnd = i;
                }
            }
            endInd = tempEnd;
            if (swapsMade) {
                swapsMade = false;
                int tempStart = startInd + 1;
                for (int j = endInd; j > startInd; j--) {
                    if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                        T temp2 = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp2;
                        swapsMade = true;
                        tempStart = j;
                    }
                }
                startInd = tempStart;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null");
        }
        if (arr.length == 0) {
            return;
        }
        if (arr.length == 1) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] leftArray = (T[]) new Object[midIndex];
        T[] rightArray = (T[]) new Object[length - midIndex];
        for (int i = 0, j = 0; i < length; i++) {
            if (i < midIndex) {
                leftArray[i] = arr[i];
            } else {
                rightArray[j] = arr[i];
                j++;
            }
        }
        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);

        int i = 0;
        int j = 0;
        while (i < leftArray.length && j < rightArray.length) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                arr[i + j] = leftArray[i++];
            } else {
                arr[i + j] = rightArray[j++];
            }
        }
        while (i < leftArray.length) {
            arr[i + j] = leftArray[i++];
        }

        while (j < rightArray.length) {
            arr[i + j] = rightArray[j++];
        }

    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("the comparator is null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("The rand is null");
        }

        int end = arr.length - 1;
        int start = 0;

        quickSortHelper(arr, comparator, rand, start, end);
    }

    /**
     *
     * @param arr refers to the array we want to sort
     * @param comparator refers to the way we compare values
     * @param rand refers to random
     * @param start refers to start index of subarray
     * @param end refers to the end index of subarray
     * @param <T> refers to the element type
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator,
                                           Random rand, int start, int end) {
        if (end - start < 1) {
            return;
        }

        int pivotIdx = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIdx];
        arr[pivotIdx] = arr[start];
        arr[start] = pivotVal;
        int i = start + 1;
        int j = end;
        while (j - i >= 0) {
            while (j - i >= 0 && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (j - i >= 0 && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            if (j - i >= 0) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }

        }
        T temp1 = arr[start];
        arr[start] = arr[j];
        arr[j] = temp1;
        quickSortHelper(arr, comparator, rand, start, j - 1);
        quickSortHelper(arr, comparator, rand, j + 1, end);

    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }
        if (arr.length == 0) {
            return;
        }
        int maxNum = arr[0];
        for (int i = 0; i < arr.length; i++) {
            int num = 0;
            if (arr[i] == Integer.MIN_VALUE) {
                num = Integer.MAX_VALUE;
            } else {
                num = Math.abs(arr[i]);
            }
            if (num > maxNum) {
                maxNum = num;
            }
        }

        int k = 1;
        while (maxNum >= 10) {
            k++;
            maxNum = maxNum / 10;
        }

        int divNumber = 1;
        ArrayList<Integer>[] buckets = new ArrayList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < arr.length; j++) {
                int bucket = (arr[j] / divNumber) % 10 + 9;
                buckets[bucket].add(arr[j]);
            }
            divNumber *= 10;

            int index = 0;
            for (int bucket = 0; bucket < buckets.length; bucket++) {
                while (buckets[bucket].size() != 0) {
                    arr[index] = buckets[bucket].remove(0);
                    index++;
                }
            }
        }
    }
}
