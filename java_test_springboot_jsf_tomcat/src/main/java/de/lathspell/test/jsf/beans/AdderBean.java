package de.lathspell.test.jsf.beans;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(value = SCOPE_REQUEST)
@Slf4j
public class AdderBean {

    @Getter
    @Setter
    @Min(0)
    @Max(100)
    private int number = 0;

    public void addOne() {
        number++;
    }

}
