package de.lathspell.test.model;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Named
@Scope(scopeName = SCOPE_PROTOTYPE)
public class FooSpringPrototype extends Foo {

}
