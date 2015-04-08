package de.lathspell.java_test_dropwizard;



import javax.servlet.ServletException;

import io.dropwizard.Application;
import io.dropwizard.servlets.assets.AssetServlet;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import de.lathspell.java_test_dropwizard.health.TemplateHealthCheck;
import de.lathspell.java_test_dropwizard.resources.HelloWorldResource;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
 //       bootstrap.addBundle(new AssetsBundle("/assets", "/assets", "index.html"));
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) throws ServletException, Exception {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        // environment.jersey().register(resource);

        /**
         * <pre>
         * <servlet>
         *   <servlet-name>Faces Servlet</servlet-name>
         *   <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
         *   <load-on-startup>1</load-on-startup>
         * </servlet>
         * <servlet-mapping>
         *   <servlet-name>Faces Servlet</servlet-name>
         *   <url-pattern>*.xhtml</url-pattern>
         * </servlet-mapping>
         * </pre>
         */
//        environment.servlets().addServlet("Faces Servlet", javax.faces.webapp.FacesServlet.class).addMapping("*.xhtml");
        environment.getApplicationContext().getServletHandler().addServletWithMapping(javax.faces.webapp.FacesServlet.class, "*.xhtml").start();

        environment.getApplicationContext().addServlet(AssetServlet.class, "/assets/*");

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

    }

}
