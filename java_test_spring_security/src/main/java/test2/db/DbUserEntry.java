package test2.db;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DbUserEntry {

    private String name;
    private String password;
    private String email;
    private Set<String> perms;
}
