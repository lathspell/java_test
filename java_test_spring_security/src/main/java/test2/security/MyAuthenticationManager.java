package test2.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = (String) auth.getCredentials();
        log.info("Authenticate user={} with pass={}", username, password);

        UserDetails user = userDetailsService.loadUserByUsername(username);
        log.info("Found user: " + user);
        
        if (!user.getPassword().equals(password)) {
            throw new BadCredentialsException("User not existing or bad password!");
        }
        log.info("Authentication successfull");
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        token.eraseCredentials();
        return token;
    }

}
