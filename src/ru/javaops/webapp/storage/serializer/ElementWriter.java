package ru.javaops.webapp.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ElementWriter<T> {
    void write(T t) throws IOException;
}