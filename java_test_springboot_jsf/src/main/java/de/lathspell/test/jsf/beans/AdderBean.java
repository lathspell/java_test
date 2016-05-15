package de.lathspell.test.jsf.beans;

import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Named
@Scope(value = SCOPE_REQUEST)
@Data
@Slf4j
public class AdderBean {

    @Min(0)
    @Max(100)
    private int number = 0;

    public void addOne() {
        number++;
    }

}
