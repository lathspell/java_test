package de.lathspell.test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class InitJsf implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        log.info("onStartup");
        sc.setInitParameter("com.sun.faces.forceLoadConfiguration", "true"); // Or: "Could not find backup for factory javax.faces.context.FacesContextFactory."
        sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
        sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
    }
}
