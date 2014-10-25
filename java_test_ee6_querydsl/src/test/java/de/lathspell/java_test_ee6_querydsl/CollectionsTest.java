package de.lathspell.java_test_ee6_querydsl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.util.concurrent.UncheckedExecutionException;
import com.mysema.query.collections.CollQuery;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;
import static org.junit.Assert.*;

// needed for access of the Querydsl Collections API
import static com.mysema.query.collections.CollQueryFactory.*;
// needed, if you use the $-invocations
import static com.mysema.query.alias.Alias.*;

public class CollectionsTest {

    private static class Name {

        public String name;

        public Name() {
        }

        public Name(String name) {
            this.name = name;
        }
    }

    /** Does not work for Collection<String> which makes it pretty unusable. */
    @Test(expected = UncheckedExecutionException.class)
    public void testCollections() {
        List<String> authors = new ArrayList<String>() {
            {
                add("Jules Verne");
                add("Mark Twain");
                add("Oscar Wilde");
            }
        };

        String n = alias(String.class, "name");
        List<String> someAuthors = from($(n), authors).where($(n).length().gt(10)).list($(n.toLowerCase()));
        assertEquals("[Jules Verne, Oscar Wilde]", someAuthors.toArray(new String[]{}).toString());
    }
}
