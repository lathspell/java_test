package test1.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Slf4j
public class MyAuthenticationManager implements AuthenticationManager {

    Map<String, String> credentials = new HashMap<String, String>() {
        {
            put("admin", "secret");
            put("guest", "welcome");
        }
    };

    public static final GrantedAuthority AUTH_RW = new SimpleGrantedAuthority("RW");
    public static final GrantedAuthority AUTH_RO = new SimpleGrantedAuthority("RO");
    public static final GrantedAuthority AUTH_STATS = new SimpleGrantedAuthority("STATS");

    Map<String, List<GrantedAuthority>> roles = new HashMap<String, List<GrantedAuthority>>() {
        {
            put("admin", Arrays.asList(AUTH_STATS, AUTH_RW));
            put("guest", Arrays.asList(AUTH_RO));
        }
    };

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String user = auth.getName();
        String pass = (String) auth.getCredentials();
        log.info("user={} pass={}", user, pass);

        if (!credentials.keySet().contains(user)) {
            throw new BadCredentialsException("User not existing!");
        }
        if (!credentials.get(user).equals(pass)) {
            throw new BadCredentialsException("Wrong password!");
        }

        List<GrantedAuthority> grantedRoles = roles.getOrDefault(user, Collections.emptyList());
        Collections.sort(grantedRoles, (o1, o2) -> {
            return o1.getAuthority().compareTo(o2.getAuthority());
        });
        log.info("sorted roles={}", grantedRoles);

        return new UsernamePasswordAuthenticationToken(user, pass, grantedRoles);
    }

}
