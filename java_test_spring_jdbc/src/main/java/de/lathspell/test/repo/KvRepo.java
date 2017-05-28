package de.lathspell.test.repo;

public interface KvRepo {

    public Kv findByKey(String key);

    public void loggingFindByKey();

}
