package ru.javaops.webapp.storage;

public class ObjectStreamPathStorage extends AbstractPathStorage {

    protected ObjectStreamPathStorage(String dir) {
        super(dir);
        setIoStrategy(new ObjectStreamStrategy());
    }
}
