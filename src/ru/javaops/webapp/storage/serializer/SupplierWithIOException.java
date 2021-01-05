package ru.javaops.webapp.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface SupplierWithIOException<T> {
    T get() throws IOException;
}