package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            readSection(dis, resume);
            return resume;
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

    private void readSection(DataInputStream dis, Resume resume) throws IOException {
        int sectionsNum = dis.readInt();
        for (int i = sectionsNum; i > 0 ; i--) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    int listSize = dis.readInt();
                    List<String> list = new ArrayList<>(listSize);
                    for (int j = 0; j < listSize; j++) {
                        list.add(dis.readUTF());
                    }
                    resume.addSection(sectionType, new ListSection(list));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    int listOrgSize = dis.readInt();
                    List<Organization> listOrg = new ArrayList<>(listOrgSize);
                    for (int j = 0; j < listOrgSize; j++) {
                        String homePageName = dis.readUTF();
                        String homePageURL = dis.readUTF();
                        Link homePage = new Link(homePageName, homePageURL.equals("") ? null : homePageURL);
                        int positionListSize = dis.readInt();
                        List<Organization.Position> positions = new ArrayList<>(positionListSize);
                        for (int k = 0; k < positionListSize; k++) {
                            LocalDate dateBegin = LocalDate.parse(dis.readUTF(), FORMATTER);
                            LocalDate dateEnd = LocalDate.parse(dis.readUTF(), FORMATTER);
                            String title = dis.readUTF();
                            String description = dis.readUTF();
                            positions.add(new Organization.Position(
                                    dateBegin,
                                    dateEnd,
                                    title,
                                    description.equals("") ? null : description));
                        }
                        listOrg.add(new Organization(homePage, positions));
                    }
                    resume.addSection(sectionType, new OrganizationSection(listOrg));
                    break;
            }
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerWithIOException<? super T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            action.accept(t);
        }
    }
}
