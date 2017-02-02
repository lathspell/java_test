package de.lathspell.test.jsf.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(SCOPE_REQUEST)
@Slf4j
public class AdderWebRequestScopedBean extends AbstractAdder {

}
