package de.lathspell.test.jsf;

import javax.inject.Named;

@Named
public class MyBean {

    private static final java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(MyBean.class.getName());

    private static final org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(MyBean.class);

    private static final org.apache.commons.logging.Log jclLogger = org.apache.commons.logging.LogFactory.getLog(MyBean.class);

    public MyBean() {
        julLogger.finest("jul finest");
        julLogger.finer("jul finer");
        julLogger.fine("jul fine");
        julLogger.config("jul config");
        julLogger.info("jul info (expect 7 entries");
        julLogger.warning("jul warning");
        julLogger.severe("jul severe");

        jclLogger.trace("jcl trace");
        jclLogger.debug("jcl debug");
        jclLogger.info("jcl info (expect 6 entries)");
        jclLogger.warn("jcl warn");
        jclLogger.error("jcl error");
        jclLogger.fatal("jcl fatal");

        slf4jLogger.trace("slf4j trace");
        slf4jLogger.debug("slf4j debug");
        slf4jLogger.info("slf4j info (expect 5 entries)");
        slf4jLogger.warn("slf4j warn");
        slf4jLogger.error("slf4j error");
    }

}
