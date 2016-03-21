package de.lathspell.test;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.sun.faces.config.FacesInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ConfigureJsf {

    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        log.info("#42# entering");

        ServletRegistrationBean servletRegistrationBean = new JsfServletRegistrationBean();

        return servletRegistrationBean;
    }

    public class JsfServletRegistrationBean extends ServletRegistrationBean {

        public JsfServletRegistrationBean() {
            super();
            log.info("ctor");
        }

        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            log.info("entering onStartup");
            Set<Class<?>> clazz = new HashSet<>();
            clazz.add(ConfigureJsf.class);

            FacesInitializer facesInitializer = new FacesInitializer();
            facesInitializer.onStartup(clazz, servletContext);

            servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
            servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
        }
    }
}
