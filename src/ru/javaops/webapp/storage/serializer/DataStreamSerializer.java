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
            writeWithException(resume.getContacts().entrySet(), dos, x -> {
                dos.writeUTF(x.getKey().name());
                dos.writeUTF(x.getValue());
            });
            writeWithException(resume.getSections().entrySet(), dos, x -> writeSection(dos, x.getKey(), x.getValue()));
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
                writeWithException(((ListSection) section).getContent(), dos, dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeWithException(((OrganizationSection) section).getContent(), dos, x -> {
                    Link homePage = x.getHomePage();
                    dos.writeUTF(homePage.getName());
                    dos.writeUTF(writeNullString(homePage.getUrl()));
                    writeWithException(x.getPositions(), dos, y -> {
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

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerWithIOException<? super T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.accept(t);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readSection(dis, resume);
            return resume;
        }
    }

    private void readSection(DataInputStream dis, Resume resume) throws IOException {
        readWithException(dis, () -> {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> listSection = new ArrayList<>();
                    readWithException(dis, () -> listSection.add(dis.readUTF()));
                    resume.addSection(sectionType, new ListSection(listSection));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> listOrg = new ArrayList<>();
                    readWithException(dis, () -> {
                        Link homePage = new Link(dis.readUTF(), readNullString(dis.readUTF()));
                        List<Organization.Position> positions = new ArrayList<>();
                        readWithException(dis, () -> positions.add(new Organization.Position(
                                DateUtil.parse(dis.readUTF()),
                                DateUtil.parse(dis.readUTF()),
                                dis.readUTF(),
                                readNullString(dis.readUTF()))));
                        listOrg.add(new Organization(homePage, positions));
                    });
                    resume.addSection(sectionType, new OrganizationSection(listOrg));
                    break;
            }
        });
    }

    private String readNullString(String string) {
        return string.equals("") ? null : string;
    }

    private void readWithException(DataInputStream dis, SupplierWithIOException supplier) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            supplier.act();
        }
    }
}
