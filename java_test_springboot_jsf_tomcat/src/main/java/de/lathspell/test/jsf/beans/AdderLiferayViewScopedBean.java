package de.lathspell.test.jsf.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("liferayView")
@Slf4j
public class AdderLiferayViewScopedBean extends AbstractAdder {

}
