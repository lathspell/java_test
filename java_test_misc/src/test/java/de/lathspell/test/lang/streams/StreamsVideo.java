package de.lathspell.test.lang.streams;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Counting the number of entries in a 2-level map using Streams.
 *
 * Inspired by:
 * <pre>
 * JDK 8: Lessons Learnt With Lambdas and Streams
 * Recorded at SpringOne Platform 2016.
 * Speaker: Simon Ritter, Azul
 * http://www.slideshare.net/SpringCentral/jdk8-lessons-learnt-with-lambdas-and-streams
 * https://www.youtube.com/watch?v=wZKmA6XodNE
 * </pre>
 */
public class StreamsVideo {

    /**
     * Map of "class names" with "method names" belonging to it.
     */
    private final Map<String, Set<String>> methods;

    /**
     * Text with multiple lines.
     */
    private final String text = "aaa\nbbbbbbbbb\ncc\ndddddddddddddddd\neeeee\n";

    /**
     * BufferedReader of "text".
     */
    private final BufferedReader textReader = new BufferedReader(new StringReader(text));

    public StreamsVideo() {
        methods = new HashMap<>();
        methods.put("Foo", new HashSet<>());
        methods.put("Bar", new HashSet<>());
        methods.put("Baz", new HashSet<>());
        methods.get("Foo").add("a1");
        methods.get("Foo").add("a2");
        methods.get("Bar").add("b1");
        methods.get("Bar").add("b2");
        methods.get("Baz").add("c1");
        methods.get("Baz").add("c2");
    }

    /**
     * Example 1: Count the overall number of methods.
     *
     * Bad: not really functional as it modifies state.
     */
    @Test
    public void ex1a() {
        LongAdder sourceCount = new LongAdder(); // allocates an instance to each thread
        methods.keySet().stream()
                .forEach(c -> sourceCount.add(methods.get(c).size()));
        assertEquals(6, sourceCount.intValue());
    }

    /**
     * Bad: Unnecessary mapping.
     */
    @Test
    public void ex1_my1() {
        int count = methods.keySet().stream()
                .map(c -> methods.get(c).size())
                .collect(Collectors.summingInt(s -> s));
        assertEquals(6, count);
    }

    /**
     * Bad: Not as readable as mapToInt() with sum().
     */
    @Test
    public void ex1_my2() {
        int count = methods.keySet().stream()
                .collect(Collectors.summingInt(k -> methods.get(k).size()));
        assertEquals(6, count);
    }

    /**
     * Good.
     */
    @Test
    public void ex1b() {
        int count = methods.keySet().stream()
                .mapToInt(k -> methods.get(k).size())
                .sum();
        assertEquals(6, count);
    }

    /**
     * Example 2: Count the number of methods and separately the number ending
     * with "2".
     */
    @Test
    public void ex2_my1() {
        LongAdder v2Count = new LongAdder();
        int count = methods.entrySet().stream()
                .collect(Collectors.summingInt(
                        entry -> {
                            v2Count.add(entry.getValue().stream().filter(v -> v.endsWith("2")).collect(Collectors.counting()));
                            return entry.getValue().size();
                        }));
        assertEquals(6, count);
        assertEquals(3, v2Count.intValue());
    }

    /**
     * Bad: Two state variables.
     */
    @Test
    public void ex2a() {
        LongAdder count = new LongAdder();
        LongAdder v2Count = new LongAdder();
        methods.entrySet()
                .forEach(entry -> {
                    entry.getValue().stream().forEach(v -> {
                        count.increment();
                        if (v.endsWith("2")) {
                            v2Count.increment();
                        }
                    });
                });

        assertEquals(6, count.intValue());
        assertEquals(3, v2Count.intValue());
    }

    /**
     * Bad: One state variables.
     */
    @Test
    public void ex2b() {
        LongAdder v2Count = new LongAdder();
        int count = methods.entrySet().stream()
                .mapToInt(entry -> {
                    entry.getValue().stream().forEach(v -> {
                        if (v.endsWith("2")) {
                            v2Count.increment();
                        }
                    });
                    return entry.getValue().size();
                })
                .sum();

        assertEquals(6, count);
        assertEquals(3, v2Count.intValue());
    }

    /**
     * Bad: v2Count is a side effect so not functional.
     */
    @Test
    public void ex2c() {
        LongAdder v2Count = new LongAdder();
        int count = methods.entrySet().stream()
                .peek(entry -> entry.getValue().stream().forEach(v -> {
                    if (v.endsWith("2")) {
                        v2Count.increment();
                    }
                }))
                .mapToInt(entry -> entry.getValue().size())
                .sum();

        assertEquals(6, count);
        assertEquals(3, v2Count.intValue());
    }

    /**
     * Example 3: Find the length of the longest line in a file.
     */
    @Test
    public void ex3cb1() {
        int result = textReader.lines()
                .mapToInt(line -> line.length())
                .max()
                .getAsInt();
        assertEquals(16, result);
    }

    @Test
    public void ex3a() {
        int result = textReader.lines()
                .mapToInt(String::length)
                .max()
                .getAsInt();
        assertEquals(16, result);
    }

    /**
     * Example 4: Find the longest line in a file.
     */
    @Test
    public void ex4cb1() {
        String result = textReader.lines()
                .sorted((a, b) -> Integer.compare(b.length(), a.length()))
                .findFirst()
                .get();
        assertEquals("dddddddddddddddd", result);
    }

    /**
     * Bad: reads lines sequentially and then sorts them.
     */
    @Test
    public void ex4a() {
        String result = textReader.lines()
                .sorted((a, b) -> b.length() - a.length())
                .findFirst()
                .get();
        assertEquals("dddddddddddddddd", result);
    }

    /**
     * Traditional Java approach.
     *
     * Bad: Can't be parallelized by the compiler and uses state.
     */
    @Test
    @SneakyThrows
    public void ex4b() {
        String result = "";
        while (textReader.ready()) {
            String s = textReader.readLine();
            if (s == null) {
                break;
            }
            if (s.length() > result.length()) {
                result = s;
            }
        }
        assertEquals("dddddddddddddddd", result);
    }

    /**
     * Bad: Large data sets result in Stack Overflow.
     */
    @Test
    public void ex4c() {
        String result = ext4cRecursion("", textReader);
        assertEquals("dddddddddddddddd", result);
    }

    @SneakyThrows
    private String ext4cRecursion(String longest, BufferedReader br) {
        String s = br.readLine();
        if (s == null) {
            return longest;
        }
        if (s.length() > longest.length()) {
            longest = s;
        }

        return ext4cRecursion(longest, br);
    }

    /**
     * Using filter/map/reduce or rather only reduce.
     *
     * Good: In effect, "a" maintains the state. Is thread safe.
     */
    @Test
    public void ex4d() {
        String result = textReader.lines()
                .reduce((a, b) -> a.length() > b.length() ? a : b)
                .get();
        assertEquals("dddddddddddddddd", result);
    }

    /**
     * Best: Easier than ex4d.
     */
    @Test
    public void ex4e() {
        String result = textReader.lines()
                .max(Comparator.comparingInt(String::length))
                .get();
        assertEquals("dddddddddddddddd", result);
    }
}
