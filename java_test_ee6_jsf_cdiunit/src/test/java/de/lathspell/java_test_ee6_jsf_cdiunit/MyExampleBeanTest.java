package de.lathspell.java_test_ee6_jsf_cdiunit;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(CdiRunner.class)
// Without AdditionalClasses you'll get:
// WELD-001408 Unsatisfied dependencies for type [MyConverter] with qualifiers [@Default] at injection point [[BackedAnnotatedField] @Inject de.lathspell.java_test_ee6_jsf_cdiunit.MyExampleBean.myConverter]
@AdditionalClasses(MyApplicationConfig.class)
public class MyExampleBeanTest {

    @Inject
    private MyExampleBean myExampleBean;
  
    @Test
    public void testGetName() {
        assertNotNull(myExampleBean);
        assertNotNull(myExampleBean.myConverter);
        assertEquals("TIM", myExampleBean.getName());
    }
}