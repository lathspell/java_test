package de.lathspell.test.config;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.ViewResolverComposite;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Spring WebMVC configuration.
 *
 * This class gets loaded by ServletConfiguration and sets up the Web framework
 * by adding resource paths and tell WebMVC where to find the template file for
 * the view names.
 *
 * @see ServletConfiguration
 */
@Configuration
@ComponentScan("de.lathspell.test")
@EnableWebMvc
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/*");
    }

    /**
     * Replacement for the InternalResourceViewResolver bean in WEB-INF/$name-servlet.xml.
     */
    @Bean
    public ViewResolver setupViewResolver() {
        ViewResolverComposite composite = new ViewResolverComposite();
        composite.setViewResolvers(Arrays.asList(
                new FreeMarkerViewResolver("/", ".ftl"),
                thymeleafViewResolver(),
                new InternalResourceViewResolver("/WEB-INF/views/", ".jsp")
        ));
        return composite;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfig() {
        log.info("freeMarkerConfig");
        FreeMarkerConfigurer c = new FreeMarkerConfigurer();
        // NPE: c.getConfiguration().setDefaultEncoding("UTF-8");
        c.setTemplateLoaderPath("/WEB-INF/views/");
        return c;
    }

    /* **************************************************************** */
 /*  THYMELEAF-SPECIFIC ARTIFACTS                                    */
 /*  TemplateResolver <- TemplateEngine <- ViewResolver              */
 /* **************************************************************** */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
        // speed up execution in most scenarios, but might be incompatible
        // with specific cases when expressions in one template are reused
        // across different data types, so this flag is "false" by default
        // for safer backwards compatibility.
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setViewNames(new String[]{"*.html"});
        return viewResolver;
    }

}
