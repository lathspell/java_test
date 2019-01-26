package de.lathspell.java_test_se8.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;

import lombok.AllArgsConstructor;
import lombok.Data;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
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

    @Ignore("FIXME: Order")
    @Test
    public void testGroupBy() {
        Map<Color, List<Child>> childrenByColor = children.stream().collect(groupingBy(Child::getFavColor));
        assertEquals("{RED=[Rosaly], GREEN=[Graham, Grag, Gail], BLUE=[Bob, Berta]}", childrenByColor.toString());
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
        Map<Color, Double> expected = new HashMap<>();
        expected.put(RED, 6.0);
        expected.put(GREEN, 4.666666666666667);
        expected.put(BLUE, 4.0);
        assertThat(namelenByColor).isEqualTo(expected);
    }

}
