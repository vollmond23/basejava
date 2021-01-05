package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(sectionType.name());
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                dos.writeUTF(sectionType.name());
                writeWithException(((ListSection) section).getContent(), dos, dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                dos.writeUTF(sectionType.name());
                writeWithException(((OrganizationSection) section).getContent(), dos, x -> {
                    dos.writeUTF(x.getHomePage().getName());
                    String url = x.getHomePage().getUrl();
                    dos.writeUTF(url != null ? url : "");
                    writeWithException(x.getPositions(), dos, y -> {
                        dos.writeUTF(y.getDateBegin().format(FORMATTER));
                        dos.writeUTF(y.getDateEnd().format(FORMATTER));
                        dos.writeUTF(y.getTitle());
                        String description = y.getDescription();
                        dos.writeUTF(description != null ? description : "");
                    });
                });
                break;
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerWithIOException<? super T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            action.accept(t);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            readSection(dis, resume);
            return resume;
        }
    }

    private void readSection(DataInputStream dis, Resume resume) throws IOException {
        int sectionsNum = dis.readInt();
        for (int i = sectionsNum; i > 0; i--) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.addSection(sectionType, new ListSection(readWithException(dis.readInt(), dis::readUTF)));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> listOrg = readWithException(dis.readInt(), () -> {
                        List<String> homePageFields = readWithException(2, dis::readUTF);
                        String url;
                        Link homePage = new Link(homePageFields.get(0), (url = homePageFields.get(1)).equals("") ? null : url);
                        List<Organization.Position> positions = readWithException(dis.readInt(), () -> {
                            List<String> positionFields = readWithException(4, dis::readUTF);
                            String description;
                            return new Organization.Position(
                                    LocalDate.parse(positionFields.get(0), FORMATTER),
                                    LocalDate.parse(positionFields.get(1), FORMATTER),
                                    positionFields.get(2),
                                    (description = positionFields.get(3)).equals("") ? null : description);
                        });
                        return new Organization(homePage, positions);
                    });
                    resume.addSection(sectionType, new OrganizationSection(listOrg));
                    break;
            }
        }
    }

    private <T> List<T> readWithException(int count, SupplierWithIOException<T> action) throws IOException {
        List<T> strings = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            strings.add(action.get());
        }
        return strings;
    }
}
