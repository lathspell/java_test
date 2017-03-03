package de.lathspell.test.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class BagOfHolding {

    private List<String> myList;

    private Set<String> mySet;

    private Map<String, String> myMap;
}
