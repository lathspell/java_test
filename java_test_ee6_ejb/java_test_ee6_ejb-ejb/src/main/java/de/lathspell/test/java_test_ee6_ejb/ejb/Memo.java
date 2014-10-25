package de.lathspell.test.java_test_ee6_ejb.ejb;

import java.util.List;
import javax.ejb.Remote;


@Remote
public interface Memo {

    public void addItem(String item);

    public void removeItem(String item);

    public List<String> getAll();

    public void clear();

    public void remove();
}
