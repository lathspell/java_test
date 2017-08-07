package de.lathspell.java_test_se8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;

import lombok.AllArgsConstructor;
import lombok.Data;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import static de.lathspell.java_test_se8.streams.GroupByTest.Color.*;

public class GroupByTest {

    enum Color {
        RED,
        GREEN,
        BLUE;
    }

    @Data
    @AllArgsConstructor
    private class Child {

        private String name;
        private Color favColor;

        @Override
        public String toString() {
            return name;
        }

    }

    private final List<Child> children = Arrays.asList(
            new Child("Rosaly", RED),
            new Child("Graham", GREEN),
            new Child("Bob", BLUE),
            new Child("Grag", GREEN),
            new Child("Gail", GREEN),
            new Child("Berta", BLUE)
    );

    @Test
    public void testGroupBy() {
        Map<Color, List<Child>> childrenByColor = children.stream().collect(groupingBy(Child::getFavColor));
        assertEquals("{BLUE=[Bob, Berta], RED=[Rosaly], GREEN=[Graham, Grag, Gail]}", childrenByColor.toString());
    }

    @Test
    public void testGroupByWithArg() {
        Map<Color, Double> namelenByColor = children.stream().collect(
                // group by favourite color
                groupingBy(Child::getFavColor,
                        // but then don't use the list of Objects but calculate an average of their names lengths and use that as map value
                        averagingInt(c -> c.getName().length())
                )
        );
        assertEquals("{BLUE=4.0, RED=6.0, GREEN=4.666666666666667}", namelenByColor.toString());
    }

}
