package de.lathspell.java_test_se8.streams;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.partitioningBy;

import lombok.AllArgsConstructor;
import lombok.Data;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PartitionByTest {

    @Data
    @AllArgsConstructor
    private class Child {

        private String name;
        private int age;

        @Override
        public String toString() {
            return name;
        }
    }

    @Test
    public void testPartitionBy() {
        List<Child> children = Arrays.asList(
                new Child("Anna", 4),
                new Child("Bert", 5),
                new Child("Charlie", 7),
                new Child("Doro", 3),
                new Child("Emil", 5),
                new Child("Franz", 2)
        );

        // Partitions the list of children into two groups (use "false" for those who did not match the condition)
        List<Child> childrenOver4 = children.stream().collect(partitioningBy(i -> i.age > 4)).get(true);

        assertEquals("[Bert, Charlie, Emil]", childrenOver4.toString());
    }
}
