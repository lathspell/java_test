package de.lathspell.java_test_ee7_rest_jpa.frontend.jsf.listeners;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LifeCycleListener implements PhaseListener {

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        log.debug("+++ " + event.getPhaseId());
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        log.debug("--- " + event.getPhaseId());
    }
}
