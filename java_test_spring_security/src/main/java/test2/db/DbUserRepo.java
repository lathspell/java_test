package test2.db;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.stereotype.Repository;

@Repository
public class DbUserRepo {

    private final Set<DbUserEntry> users = new HashSet<DbUserEntry>() {
        {
            add(new DbUserEntry("admin", "secret", "admin@example.com", new TreeSet(Arrays.asList("RW", "STATS"))));
            add(new DbUserEntry("tim", "t1mmy", "tim@example.com", new TreeSet(Arrays.asList("RO", "STATS"))));
        }
    };

    public Optional<DbUserEntry> findByName(String username) {
        return users.stream().filter(e -> e.getName().equals(username)).findFirst();
    }
}
