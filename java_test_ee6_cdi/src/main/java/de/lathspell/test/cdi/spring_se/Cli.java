package de.lathspell.test.cdi.spring_se;

import javax.naming.NamingException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Cli {

    public static void main(String[] args) throws NamingException {
        String env = "devel";
        if (args.length > 0 && args[0].startsWith("--env=")) {
            env = args[0].replaceFirst("--env=", "");
        }

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles(env);
        ctx.scan(Cli.class.getPackage().getName());
        ctx.refresh();

        App app = ctx.getBean(App.class);
        app.start();
    }
}
