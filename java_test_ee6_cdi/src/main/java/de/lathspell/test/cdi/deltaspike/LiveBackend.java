package de.lathspell.test.cdi.deltaspike;

import javax.enterprise.inject.Alternative;

import org.apache.deltaspike.core.api.exclude.annotation.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage;

@Exclude(exceptIfProjectStage = ProjectStage.Production.class)
@Alternative
public class LiveBackend implements Backend {

    public String getName() {
        return "mock";
    }
}