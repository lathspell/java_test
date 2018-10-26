package de.lathspell.test2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class DebugLog {

    private List<Object> allInputs = new ArrayList<>();

    public List<Object> getAllInputs() {
        return allInputs;
    }
}
