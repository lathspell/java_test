package de.lathspell.test.ws_standalone.client;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingMessageHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        SOAPMessage message = context.getMessage();
        boolean isOutboundMessage = (boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            message.writeTo(baos);
            log.debug((isOutboundMessage ? ">>>" : "<<<") + " " + baos.toString());
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        SOAPMessage message = context.getMessage();
        try {
            message.writeTo(System.out);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
