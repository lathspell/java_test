package de.lathspell.test.cdi.deltaspike;

import javax.enterprise.inject.Alternative;

import org.apache.deltaspike.core.api.exclude.annotation.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage.Development;
import org.apache.deltaspike.core.api.projectstage.ProjectStage.UnitTest;

@Exclude(exceptIfProjectStage = {Development.class, UnitTest.class})
@Alternative
public class MockBackend implements Backend {

    public String getName() {
        return "mock";
    }
}
