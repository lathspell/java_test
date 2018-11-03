package test2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MySecuredService {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void onlyForAdmins() {
        log.info("onlyForAdmins called");
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void onlyForAdmins2() {
        log.info("onlyForAdmins2 called");
    }

    @Secured({"ROLE_ADMIN"})
    public void onlyForAdmins3() {
        log.info("onlyForAdmins3 called");
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"}) // or
    public void freeForAll() {
        log.info("freeForAll called");
    }

    /* JSR-250: @RolesAllowed("ADMIN")
    public void onlyForAdmins3() {
        log.info("onlyForAdmins3 called");
    }
    */

    @PreAuthorize("hasAuthority('VIEW_STATS')")
    public void viewStats() {
        log.info("viewStats called");
    }

    /* Caveat: @Secured does only work if the checked string starts with "ROLE_"!
    @Secured({"VIEW_STATS"})
    public void viewStats2() {
        log.info("viewStats2 called");
    }
     */
}
