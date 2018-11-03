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
            add(new DbUserEntry("admin", "{noop}secret", "admin@example.com", new TreeSet(Arrays.asList("ROLE_ADMIN", "VIEW_STATS"))));
            add(new DbUserEntry("tim", "{noop}t1mmy", "tim@example.com", new TreeSet(Arrays.asList("ROLE_USER", "VIEW_STATS"))));
        }
    };

    public Optional<DbUserEntry> findByName(String username) {
        return users.stream().filter(e -> e.getName().equals(username)).findFirst();
    }
}
