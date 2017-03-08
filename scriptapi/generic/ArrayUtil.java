package scripts.api.scriptapi.generic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * The ArrayUtil class contains helper methods for arrays.
 * <p>
 * @author Nolan
 */
public class ArrayUtil {

    /**
     * Checks to see if the specified integer array contains the specified value.
     * <p>
     * @param value The value.
     * @param array The array.
     * @return True if the array contains the value, false otherwise.
     */
    public static boolean contains(int value, int... array) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the specified short array contains the specified value.
     * <p>
     * @param value The key.
     * @param array The array.
     * @return True if the array contains the key, false otherwise.
     */
    public static boolean contains(short value, short... array) {
        for (short a : array) {
            if (a == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the specified object array contains the specified object.
     * <p>
     * @param <T>    The type of array.
     * @param object The object.
     * @param array  The array.
     * @return True if the array contains the object, false otherwise.
     */
    public static <T> boolean contains(T object, T... array) {
        return Arrays.asList(array).contains(object);
    }

    /**
     * Concatenates the two specified arrays.
     * <p>
     * @param array1 The first array.
     * @param array2 The second array.
     * @return The concatenated array.
     */
    public static int[] concat(int[] array1, int[] array2) {
        int length1 = array1.length;
        int length2 = array2.length;

        int[] newArray = new int[length1 + length2];
        System.arraycopy(array1, 0, newArray, 0, length1);
        System.arraycopy(array2, 0, newArray, length1, length2);

        return newArray;
    }

    /**
     * Concatenates the two specified arrays.
     * <p>
     * @param <T>    The type of the arrays.
     * @param array1 The first array.
     * @param array2 The second array.
     * @return The concatenated array.
     */
    public static <T> T[] concat(T[] array1, T[] array2) {
        int length1 = array1.length;
        int length2 = array2.length;

        T[] newArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), length1 + length2);
        System.arraycopy(array1, 0, newArray, 0, length1);
        System.arraycopy(array2, 0, newArray, length1, length2);

        return newArray;
    }

    /**
     * Filters any duplicates in the specified array and returns a new array with no duplicates.
     * <p>
     * Note that this method <i>does not</i> alter the original array in any way.
     * <p>
     * @param <T>   The object type of the array.
     * @param array The array to filter.
     * @return An array with the duplicates filtered out.
     */
    public static <T extends Object> T[] filterDuplicates(T[] array) {
        if (array.length < 1) {
            return array;
        }
        HashSet<T> set = new LinkedHashSet<>(Arrays.asList(array));
        return set.toArray((T[]) Array.newInstance(array.getClass(), set.size()));
    }

    /**
     * Converts the specified list of strings into an array.
     *
     * If the list is null, this method returns an empty array of strings.
     * <p>
     * @param list The list to convert.
     * @return An array representing the list.
     */
    public static String[] toArrayString(List<String> list) {
        if (list == null) {
            return new String[0];
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Converts the specified list of ints into an array.
     *
     * If the list is null, this method returns an empty array of ints.
     * <p>
     * @param list The list to convert.
     * @return An array representing the list.
     */
    public static int[] toArrayInt(List<Integer> list) {
        if (list == null) {
            return new int[0];
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    /**
     * Converts the specified list of doubles into an array.
     *
     * If the list is null, this method returns an empty array of doubles.
     * <p>
     * @param list The list to convert.
     * @return An array representing the list.
     */
    public static double[] toArrayDouble(List<Double> list) {
        if (list == null) {
            return new double[0];
        }
        double[] arr = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    /**
     * Converts the specified list of longs into an array.
     *
     * If the list is null, this method returns an empty array of longs.
     * <p>
     * @param list The list to convert.
     * @return An array representing the list.
     */
    public static long[] toArrayLong(List<Long> list) {
        if (list == null) {
            return new long[0];
        }
        long[] arr = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;

    }

    /**
     * Converts the specified list of booleans into an array.
     *
     * If the list is null, this method returns an empty array of booleans.
     * <p>
     * @param list The list to convert.
     * @return An array representing the list.
     */
    public static boolean[] toArrayBoolean(List<Boolean> list) {
        if (list == null) {
            return new boolean[0];
        }
        boolean[] arr = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;

    }

    /**
     * Checks whether or not the specified values can be retrieved from the specified array.
     * <p>
     * @param array The array to check.
     * @param i     The x value to be retrieved from the 2D array.
     * @param j     The y value to be retrieved from the 2D array.
     * @return True if the specified values can be retrieved from the specified array, false otherwise.
     */
    public static boolean isValidArray(Object[][] array, int i, int j) {
        return i >= 0 && j >= 0 && i < array.length && j < array[i].length;
    }

    /**
     * Returns whether the specified String is contained within any String that is contained within the specified ArrayList of Strings.
     *
     * @param string The String that is being checked.
     * @param array  The ArrayList that is being checked.
     * @return Whether the specified String is contained within any String that is contained within the specified ArrayList of Strings.
     */
    public static boolean containsPartOf(String string, String... array) {
        if (string == null || array == null) {
            return false;
        }
        for (String s : array) {
            if (s != null) {
                if (string.toLowerCase().contains(s.toLowerCase()) || s.toLowerCase().contains(string.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any of the String elements contained in the specified ArrayList of Strings contains any of the Strings in the specified varargs of
     * Strings, false otherwise.
     * <p>
     * @param array       The ArrayList being tested.
     * @param stringArray The ArrayList of Strings to be searched for.
     * @return True if any of the String elements contained in the specified ArrayList of Strings contains any of the Strings in the specified varargs of
     *         Strings, false otherwise.
     */
    public static boolean containsPartOf(ArrayList<String> array, String... stringArray) {
        for (String tempS : array) {
            for (String s : stringArray) {
                if (tempS.toLowerCase().contains(s.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the array represented by the specified varargs.
     * 
     * @param <T>   The type of array.
     * @param array The array.
     * @return The array represented by the specified varargs.
     */
    public static <T extends Object> T[] getArray(T... array) {
        return array;
    }

    /**
     * Checks if the specified name is contained in the specified array of Enums.
     * <p>
     * @param value The value that is being tested.
     * @param arr   The array that is being searched through.
     * @return true if the specified name is contained in the specified array of Enums.
     */
    public static boolean enumArrayContains(String value, Enum[] arr) {
        for (Enum num : arr) {
            if (value.equals(num.name())) {
                return true;
            }
        }
        return false;
    }
}
