package de.lathspell.test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.BagOfHolding;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/collections.xml")
public class CollectionsTest {

    // Inject using Spring annotations; Qualifier as there are two different beans of type "Person" in the XML.
    @Autowired
    private BagOfHolding bag;

    @Test
    public void testOld() {
        assertThat(bag.getMyList(), hasItems("red", "green"));
        assertThat(bag.getMySet(), hasItems("red", "green"));

        Map<String, String> expectingMap = new HashMap<>();
        expectingMap.put("red", "r");
        expectingMap.put("green", "g");
        assertThat(bag.getMyMap(), is(expectingMap));
    }
}
