package de.lathspell.java_test_ee7_websockets;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringEscapeUtils;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class BillboardView {

    private static final Logger log = LoggerFactory.getLogger(BillboardView.class);

    private final static String CHANNEL = "/billboard";

    private String msg;

    public void send() {
        log.info("Sending to {}: {}", CHANNEL, msg);
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(CHANNEL, StringEscapeUtils.escapeHtml(msg));
    }

    public String getMsg() {
        log.info("getMsg");
        return msg;
    }

    public void setMsg(String msg) {
        log.info("setMsg");
        this.msg = msg;
    }

}
