package de.lathspell.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {
    private String name = "anonymous";
}
