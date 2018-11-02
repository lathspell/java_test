package test2.security;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUser extends User {

    @Getter
    private final String email;
    
    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String email) {
        super(username, password, authorities);
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString() + "; email: " + email;
    }
}
