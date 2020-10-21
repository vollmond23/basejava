package ru.javaops.webapp.exception;

public class NotExistStorageException extends StorageException{
    public NotExistStorageException(String uuid) {
        super("ERROR: Resume " + uuid + " is not exists.", uuid);
    }
}
