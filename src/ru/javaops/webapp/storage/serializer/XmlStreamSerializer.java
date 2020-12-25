package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.*;
import ru.javaops.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, Link.class,
                OrganizationSection.class, TextSection.class,
                ListSection.class, Organization.Position.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream stream) throws IOException {
        try (Writer w = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume doRead(InputStream stream) throws IOException {
        try (Reader r = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
