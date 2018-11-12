package de.lathspell.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import de.lathspell.test.service.CalculatorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AopTest {

    @Autowired
    private CalculatorService svc;

    @Test
    public void testAdd() {
        assertEquals(3, svc.add(1, 2));
    }

    /** Will be logged by the @AfterThrowing advice but cannot be prevented. */
    @Test(expected = Exception.class)
    public void testDivByZero() {
        svc.div(1, 0);
    }

    /** Exception will be prevented by the @AroundAdvice. */
    @Test
    public void testStrangeDivByZero() {
        assertEquals(-1, svc.strangeDiv(2, 0));
    }

}
