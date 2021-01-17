package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.*;
import ru.javaops.webapp.util.DateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeCollection(dos, resume.getContacts().entrySet(), x -> {
                dos.writeUTF(x.getKey().name());
                dos.writeUTF(x.getValue());
            });
            writeCollection(dos, resume.getSections().entrySet(), x -> writeSection(dos, x.getKey(), x.getValue()));
        }
    }

    private void writeSection(DataOutputStream dos, SectionType sectionType, Section section) throws IOException {
        dos.writeUTF(sectionType.name());
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeCollection(dos, ((ListSection) section).getContent(), dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeCollection(dos, ((OrganizationSection) section).getContent(), x -> {
                    Link homePage = x.getHomePage();
                    dos.writeUTF(homePage.getName());
                    dos.writeUTF(writeNullString(homePage.getUrl()));
                    writeCollection(dos, x.getPositions(), y -> {
                        dos.writeUTF(DateUtil.format(y.getDateBegin()));
                        dos.writeUTF(DateUtil.format(y.getDateEnd()));
                        dos.writeUTF(y.getTitle());
                        dos.writeUTF(writeNullString(y.getDescription()));
                    });
                });
                break;
        }
    }

    private String writeNullString(String string) {
        return string == null ? "" : string;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<? super T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readItems(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(
                        readList(dis, () -> new Organization(
                                new Link(dis.readUTF(), readNullString(dis.readUTF())),
                                readList(dis, () -> new Organization.Position(
                                        DateUtil.parse(dis.readUTF()),
                                        DateUtil.parse(dis.readUTF()),
                                        dis.readUTF(),
                                        readNullString(dis.readUTF()))
                                ))));
            default:
                throw new IllegalStateException();
        }
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private String readNullString(String string) {
        return string.equals("") ? null : string;
    }

    private void readItems(DataInputStream dis, ElementProcessor processor) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            processor.process();
        }
    }
}
