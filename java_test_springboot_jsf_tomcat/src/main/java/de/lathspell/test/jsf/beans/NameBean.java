package de.lathspell.test.jsf.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NameBean {

    @Size(min = 3, max = 50)
    @Getter
    @Setter
    private String name;

    public void submit() {
        log.info("submit");
        FacesContext.getCurrentInstance().addMessage("nameForm:nameInput", new FacesMessage("Hello " + name));
    }
}
