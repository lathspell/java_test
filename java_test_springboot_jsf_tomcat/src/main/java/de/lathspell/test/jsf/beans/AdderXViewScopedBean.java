package de.lathspell.test.jsf.beans;

import com.github.javaplugs.jsf.SpringScopeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@SpringScopeView
@Component
@Slf4j
public class AdderXViewScopedBean extends AbstractAdder {

}
