package de.lathspell.java_test_ee7_jsf.phases;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhasesListener implements PhaseListener {

    private static final Logger log = LoggerFactory.getLogger(PhasesListener.class);

    @Override
    public PhaseId getPhaseId() {
        // It get's only called if the phase is actually used!
        return PhaseId.ANY_PHASE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        log.debug("vvvv Phase {} starting vvvv", event.getPhaseId());
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        log.debug("^^^^ Phase {} ending ^^^^", event.getPhaseId());
    }
}
