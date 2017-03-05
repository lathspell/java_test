package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.scopedproxy.Manager;
import de.lathspell.test.model.scopedproxy.ManagerConfiguration;

/**
 * AOP Scoped Proxy.
 *
 * The bean "Manager" contains the autowired property "settings" of type "UserSettings".
 * It is created by ManagerConfiguration.userSettingsFactory().
 * 
 * As Manager is of scope Singleton, "settings" should remain the same once instanciated.
 *  * 
 * Using the AOP Proxy-Settings type "INTERFACES", a proxy object is called which
 * triggers a new call to the userSettingsFactory() every time one of the interface
 * methods setSettings() and getSettings() is called.
 * 
 * This proxy behaviour could be used to return e.g. a HTTP Session specific object
 * every time it is accessed without having to write HTTP Session specific logic in
 * the Manager object.
 * 
 * It can quickly become quite confusing though :-)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Manager.class, ManagerConfiguration.class})
@Slf4j
public class ScopedProxyTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testScopedProxy() {
        Manager m = context.getBean(Manager.class);

        String oldObj = m.getSettings().toString();
        assertNotEquals(oldObj, m.getSettings().toString());
        assertNotEquals(oldObj, m.getSettings().toString());
        assertNotEquals(oldObj, m.getSettings().toString());
    }
}
