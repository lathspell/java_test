package de.lathspell.test.model;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Testing init-method vs InitializingBean vs. @PostConstruct.
 *
 * Using InitializingBean is not recommended as it couples to bean to Spring.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class InititializedPerson2 implements InitializingBean, DisposableBean {

    private String firstName;
    private String lastName;
    private LocalDate birthday;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * For Spring InitializingBean Interface; comes second.
     */
    @Override
    public void afterPropertiesSet() {
        log.info("Entering afterPropertiesSet");
    }

    /**
     * For Spring "init-method"; comes third.
     */
    private void initMethod() {
        log.info("Entering initMethod");
        lastName = lastName.toUpperCase();
    }

    /**
     * For Common Annotations; comes first;
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Entering postConstruct");
    }

    /**
     * For Spring DisposableBean; comes second.
     */
    @Override
    public void destroy() {
        log.info("Entering destroy");
    }

    /**
     * For Common Annotations; comes first.
     */
    @PreDestroy
    public void preDestroy() {
        log.info("Entering preDestroy");
    }

    /**
     * For Spring "destroy-method"; comes third.
     */
    public void destroyMethod() {
        log.info("Entering destroyMethod");
    }

}
