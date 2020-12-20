package ru.javaops.webapp.storage.strategies;

import ru.javaops.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOStrategy {

    void doWrite(Resume resume, OutputStream stream) throws IOException;

    Resume doRead(InputStream stream) throws IOException;
}
