package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream stream) throws IOException {
        try (Writer w = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, w);
        }
    }

    @Override
    public Resume doRead(InputStream stream) throws IOException {
        try (Reader r = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.read(r, Resume.class);
        }
    }
}
