package test2.security;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import test2.db.DbUserEntry;
import test2.db.DbUserRepo;

@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private DbUserRepo dbUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching for {}", username);
        DbUserEntry u = dbUserRepo.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new MyUser(u.getName(), u.getPassword(), u.getPerms().stream().map(it -> new SimpleGrantedAuthority(it)).collect(Collectors.toList()), u.getEmail());
    }

}
