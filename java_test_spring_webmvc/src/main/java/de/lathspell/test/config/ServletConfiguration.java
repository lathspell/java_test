package de.lathspell.test.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Create ApplicationContext an register DispatcherServlet (replacement for web.xml).
 *
 * The Servlet Container scans for all classes that implement
 * javax.servlet.ServletContainerInitializer and will thus find
 * SpringServletContainerInitializer.
 * That class is annotated by the Servlet @HandlesTypes annotation
 * with WebApplicationInitializer as argument so that the Servlet
 * Container will pass any implementations it can find, e.g. this here.
 * 
 * @since Servlet 3.0 first offered this SPI based configuration
 * @see javax.servlet.ServletContainerInitializer
 * @see org.springframework.web.SpringServletContainerInitializer
 */
public class ServletConfiguration implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // This creates an ApplicationConfig as replacement for WEB-INF/$name-servlet.xml
        // The contents of the old $name-servlet.xml are now in the class WebConfiguration.
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebMvcConfiguration.class);
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
