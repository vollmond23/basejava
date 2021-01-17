package ru.javaops.webapp.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ElementReader<T> {
    T read() throws IOException;
}