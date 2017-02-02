package de.lathspell.test.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.extern.slf4j.Slf4j;

/**
 * JSF managed bean with the JSF ViewScope.
 *
 * As Spring has no native ViewScope, we use the JSF internal ("hk"-based)
 * dependency injection mechanism.
 *
 *
 */
@ViewScoped
@ManagedBean(name = "adderJsfViewScopedBean")
@Slf4j
public class AdderJsfViewScopedBean extends AbstractAdder {

}
