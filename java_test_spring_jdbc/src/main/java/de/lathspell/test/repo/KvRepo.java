package de.lathspell.test.repo;

import common.model.Kv;

public interface KvRepo {

    public Kv findByKey(String key);

    public void loggingFindByKey();

}
