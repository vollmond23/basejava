package ru.javaops.webapp.storage;

import ru.javaops.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getProps()));
    }
}