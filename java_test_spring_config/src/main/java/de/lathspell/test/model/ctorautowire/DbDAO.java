package de.lathspell.test.model.ctorautowire;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DbDAO {

    @Getter
    private DbProperties props;

    public DbDAO() {
        log.info("ctor");
        // this one is not used by Spring
    }

    @Autowired
    public DbDAO(@Qualifier("customers") DbProperties props) {
        log.info("ctor with arg");
        this.props = props;
    }

}
