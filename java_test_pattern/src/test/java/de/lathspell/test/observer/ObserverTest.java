package de.lathspell.test.observer;

import org.junit.Test;

public class ObserverTest {

    @Test
    public void test() {
        Job j1 = new Job(2, "foo");
        Job j2 = new Job(4, "bar");
        
        JobQueue q = new JobQueue();
        q.addJob(j1);
        q.addJob(j2);
    }

}
