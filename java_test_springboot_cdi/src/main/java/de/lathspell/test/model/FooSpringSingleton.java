package de.lathspell.test.model;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Named
@Scope(scopeName = SCOPE_SINGLETON)
public class FooSpringSingleton extends Foo {

}
