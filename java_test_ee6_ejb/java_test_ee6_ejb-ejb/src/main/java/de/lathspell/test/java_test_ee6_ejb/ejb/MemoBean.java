package de.lathspell.test.java_test_ee6_ejb.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Stateful
public class MemoBean implements Memo {

    private static final Logger logger = LogManager.getLogger(MemoBean.class);
    List<String> list;

    public MemoBean() {
        logger.entry();
        list = new ArrayList<>();
    }

    @Override
    public void addItem(String item) {
        logger.entry(item);
        list.add(item);
    }

    @Override
    public void removeItem(String item) {
        logger.entry(item);
        list.remove(item);
    }

    @Override
    public List<String> getAll() {
        logger.entry();
        return list;
    }

    @Override
    public void clear() {
        logger.entry();
        list = new ArrayList<>();
    }

    @Remove()
    @Override
    public void remove() {
        logger.entry();
        list = null;
    }
}
