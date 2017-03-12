package de.lathspell.test.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // This creates an ApplicationConfig as replacement for WEB-INF/$name-servlet.xml
        // The contents of the old $name-servlet.xml are now in the class WebConfiguration.
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebConfiguration.class);
        ctx.setServletContext(servletContext);

        // This replaces the web.xml:
        //
        //       <servlet>
        //           <servlet-name>java_test_spring_webmvc</servlet-name>
        //           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        //           <load-on-startup>1</load-on-startup>
        //       </servlet>
        //   
        //        <servlet-mapping>
        //            <servlet-name>java_test_spring_webmvc</servlet-name>
        //            <url-pattern>/hello/*</url-pattern>
        //        </servlet-mapping>        
        Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }

}
