package de.lathspell.test.lang.functional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FunctionalTest {

    @Test
    public void testLambda() {
        // The anonymous function on the right side is called Lambda Expression
        Comparator<String> ssUmlautCmp = (a,b) -> a.replace("ß", "ss").compareTo(b.replace("ß", "ss"));
        
        List<String> list = Arrays.asList("Fuss", "Fuß", "Fuss2");
        
        list.sort(Comparator.naturalOrder());
        assertEquals(Arrays.asList("Fuss", "Fuss2", "Fuß"), list); // ASCII 'ß' comes after 's'
        
        list.sort(ssUmlautCmp);
        assertEquals(Arrays.asList("Fuss", "Fuß", "Fuss2"), list); // "ss" and "ß" are considered equal
    }
    
    @Test
    public void testArrays() {
        List<Integer> oldList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // List
        List<Integer> newList = oldList.stream().filter(x -> (x % 2) == 0).collect(Collectors.toList());
        assertEquals(Arrays.asList(2, 4, 6, 8, 10), newList);

        // Object Array
        Integer[] newIntegerArray = oldList.stream().filter(x -> (x % 2) == 0).toArray(Integer[]::new);
        assertArrayEquals(new Integer[]{2, 4, 6, 8, 10}, newIntegerArray);

        // Primitive Array
        int[] newIntArray = oldList.stream().filter(x -> (x % 2) == 0).mapToInt(x -> x).toArray();
        assertArrayEquals(new int[]{2, 4, 6, 8, 10}, newIntArray);
    }

    @Test
    public void createStream() {
        // object stream from literal list
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);
        assertEquals(5, integerStream.count());

        // object stream from object array
        Integer[] integerArray = new Integer[]{1, 2, 3, 4, 5};

        Stream<Integer> integerStream2a = Stream.of(integerArray);
        assertEquals(5, integerStream2a.count());

        Stream<Integer> integerStream2b = Arrays.stream(integerArray);
        assertEquals(5, integerStream2b.count());

        // primitive stream from primitive array
        int[] intArray = new int[]{1, 2, 3, 4, 5};
        IntStream intStream = Arrays.stream(intArray);
        assertEquals(5, intStream.count());
    }

    @Test
    public void testMap() {
        List<Integer> oldList = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> newList = oldList.stream().map(x -> x * 2).collect(Collectors.toList());
        assertEquals(Arrays.asList(2, 4, 6, 8, 10), newList);
    }
}
