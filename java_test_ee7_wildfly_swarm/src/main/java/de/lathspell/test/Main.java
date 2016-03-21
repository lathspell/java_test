package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.weld.WeldFraction;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        log.info("entering");
        Swarm swarm = new Swarm();
        swarm.fraction(new WeldFraction().requireBeanDescriptor(false));
        // auto: swarm.fraction(new JSFFraction());
        log.info("starting");
        swarm.start();
        log.info("deploying");
        swarm.deploy();
    }
}
