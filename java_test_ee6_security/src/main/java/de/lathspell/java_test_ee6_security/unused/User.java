package de.lathspell.java_test_ee6_security.unused;

import java.io.Serializable;

/**
 * This class represents a single user for custom authentication.
 *
 * Has to implement serializable as it gets tossed around and stored
 * between requests by JSF.
 *
 */
public class User implements Serializable {

    private String username;
    private String password;
    private String group;

    public User() {
    }

    public User(String username, String password, String group) {
        this.username = username;
        this.password = password;
        this.group = group;
    }

    @Override
    public String toString() {
        return "User(" + username + ", " + group + ")";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
