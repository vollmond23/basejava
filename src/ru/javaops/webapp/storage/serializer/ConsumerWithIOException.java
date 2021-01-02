package ru.javaops.webapp.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerWithIOException<T> {
    void accept(T t) throws IOException;
}