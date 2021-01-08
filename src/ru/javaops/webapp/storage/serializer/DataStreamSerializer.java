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
                    List<String> listSection = new ArrayList<>();
                    readWithException(dis, () -> listSection.add(dis.readUTF()));
                    resume.addSection(sectionType, new ListSection(listSection));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> listOrg = new ArrayList<>();
                    readWithException(dis, () -> {
                        String name = dis.readUTF();
                        String url = dis.readUTF();
                        Link homePage = new Link(name, url.equals("") ? null : url);
                        List<Organization.Position> positions = new ArrayList<>();
                        readWithException(dis, () -> {
                            LocalDate dateBegin = LocalDate.parse(dis.readUTF(), FORMATTER);
                            LocalDate dateEnd = LocalDate.parse(dis.readUTF(), FORMATTER);
                            String title = dis.readUTF();
                            String description = dis.readUTF();
                            positions.add(new Organization.Position(
                                    dateBegin,
                                    dateEnd,
                                    title,
                                    description.equals("") ? null : description));
                        });
                        listOrg.add(new Organization(homePage, positions));
                    });
                    resume.addSection(sectionType, new OrganizationSection(listOrg));
                    break;
            }
        }
    }

    private void readWithException(DataInputStream dis, SupplierWithIOException supplier) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            supplier.action();
        }
    }
}
