package de.lathspell.java_test_ee6_security.unused;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;

@Singleton
public class UserService {

    private static final List<User> USERS = new ArrayList<User>() {
        {
            add(new User("admin_user", "q", "admin_role"));
            add(new User("guest_user", "q", "guest_role"));
            add(new User("dept_a_user", "q", "dept_a_role"));
            add(new User("dept_b_user", "q", "dept_b_role"));
        }
    };

    public User findAndCheck(String username, String password) throws Exception {
        User user = null;
        for (User iuser : USERS) {
            if (iuser.getUsername().equals(username)) {
                user = iuser;
            }
        }
        if (user == null) {
            throw new Exception("User not existing!");
        }
        if (!user.getPassword().equals(password)) {
            throw new Exception("Wrong password!");
        }
        return user;
    }
}
