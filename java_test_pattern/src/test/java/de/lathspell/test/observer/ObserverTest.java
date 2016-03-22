package de.lathspell.test.observer;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObserverTest {

    /**
     * 11:37:09.372 [main] INFO de.lathspell.test.observer.JobQueue - Adding job Job(id=2, cmd=foo)
     * 11:37:09.377 [main] INFO de.lathspell.test.observer.Handler1 - Received Upate for Job(id=2, cmd=foo)
     * 11:37:09.377 [main] INFO de.lathspell.test.observer.JobQueue - Finished
     * 11:37:09.378 [main] INFO de.lathspell.test.observer.JobQueue - Adding job Job(id=4, cmd=bar)
     * 11:37:09.379 [main] INFO de.lathspell.test.observer.Handler2 - Received Upate for Job(id=4, cmd=bar)
     * 11:37:09.379 [main] INFO de.lathspell.test.observer.Handler1 - Received Upate for Job(id=4, cmd=bar)
     * 11:37:09.379 [main] INFO de.lathspell.test.observer.JobQueue - Finished
     */
    @Test
    public void test() {
        Handler1 h1 = new Handler1();
        Handler2 h2 = new Handler2();

        Job j1 = new Job(2, "foo");
        Job j2 = new Job(4, "bar");

        JobQueue q = new JobQueue();
        q.addObserver(h1);
        q.addJob(j1);
        q.addObserver(h2);
        q.addJob(j2);

        assertEquals(Arrays.asList(2, 4), h1.debugList);
        assertEquals(Arrays.asList(4), h2.debugList);
    }

}
