package de.lathspell.test.junit;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class SortByMethodNameRunner extends BlockJUnit4ClassRunner {

    private static class NameCmp implements Comparator<FrameworkMethod> {
        @Override
        public int compare(FrameworkMethod o1, FrameworkMethod o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    public SortByMethodNameRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> list = super.computeTestMethods();
        // Eine Anonymous Class als Comparator führt zu einer Exception :)
        Collections.sort(list, new NameCmp());
        return list;
    }
}
