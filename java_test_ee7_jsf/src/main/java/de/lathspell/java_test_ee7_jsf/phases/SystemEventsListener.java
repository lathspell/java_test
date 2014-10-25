package de.lathspell.java_test_ee7_jsf.phases;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemEventsListener implements SystemEventListener {

    private static final Logger log = LoggerFactory.getLogger(SystemEventsListener.class);

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof Application;
    }

    /**
     * Handles the event.
     *
     * At least the following events are available: *
     * ComponentSystemEvent,
     * ExceptionQueuedEvent,
     * PostConstructApplicationEvent,
     * PostConstructCustomScopeEvent,
     * PostKeepFlashValueEvent,
     * PostPutFlashValueEvent,
     * PreClearFlashEvent,
     * PreDestroyApplicationEvent,
     * PreDestroyCustomScopeEvent,
     * PreRemoveFlashValueEvent
     *
     * https://javaserverfaces.java.net/nonav/docs/2.2/javadocs/index.html?javax/faces/event/SystemEvent.html
     *
     * @param event
     * @throws AbortProcessingException
     */
    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        log.debug("~~~~ " + event.getClass().getSimpleName() + " ~~~~");
    }

}
