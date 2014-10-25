package de.lathspell.java_test_ee7_jsf.blog.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlogTitleValidator implements ConstraintValidator<BlogTitle, String> {

    private static final Logger log = LoggerFactory.getLogger(BlogTitleValidator.class);

    @Override
    public void initialize(BlogTitle a) {
        // nothing
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        log.info("Validating blog title: " + t);

        cvc.disableDefaultConstraintViolation();

        if (t == null) {
            return true;
        }

        if (t.length() < 3) {
            // EL-References like "{BlogTitleTooLong}" are possible!
            cvc.buildConstraintViolationWithTemplate("Title too short!").addConstraintViolation();
            return false;
        }

        if (t.length() > 20) {
            cvc.buildConstraintViolationWithTemplate("Title too long!").addConstraintViolation();
            return false;
        }

        return true;
    }

}
