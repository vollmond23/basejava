package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(resume.getContacts().size());
            writeWithException(resume.getContacts().entrySet(), x -> {dos.writeUTF(x.getKey().name()); dos.writeUTF(x.getValue());});
            writeWithException(resume.getSections().entrySet(), x -> writeSection(dos, x.getKey(), x.getValue()));
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
                dos.writeInt(((ListSection) section).getContent().size());
                writeWithException(((ListSection) section).getContent(), dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                dos.writeUTF(sectionType.name());
                dos.writeInt(((OrganizationSection) section).getContent().size());
                writeWithException(((OrganizationSection) section).getContent(), x -> {
                    dos.writeUTF(x.getHomePage().getName());
                    dos.writeUTF(x.getHomePage().getUrl() != null ? x.getHomePage().getUrl() : "");
                    dos.writeInt(x.getPositions().size());
                    writeWithException(x.getPositions(), y -> {
                        dos.writeUTF(y.getDateBegin().toString());
                        dos.writeUTF(y.getDateEnd().toString());
                        dos.writeUTF(y.getTitle());
                        dos.writeUTF(y.getDescription() != null ? y.getDescription() : "");
                    });
                });
                break;
        }
    }

    private void readSection(DataInputStream dis, Resume resume) throws IOException {
        while (dis.available() != 0) {
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
                    for (int i = 0; i < listSize; i++) {
                        list.add(dis.readUTF());
                    }
                    resume.addSection(sectionType, new ListSection(list));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    int listOrgSize = dis.readInt();
                    List<Organization> listOrg = new ArrayList<>(listOrgSize);
                    for (int i = 0; i < listOrgSize; i++) {
                        String homePageName = dis.readUTF();
                        String homePageURL = dis.readUTF();
                        Link homePage = new Link(homePageName, homePageURL.equals("") ? null : homePageURL);
                        int positionListSize = dis.readInt();
                        List<Organization.Position> positions = new ArrayList<>(positionListSize);
                        for (int j = 0; j < positionListSize; j++) {
                            LocalDate dateBegin = LocalDate.parse(dis.readUTF());
                            LocalDate dateEnd = LocalDate.parse(dis.readUTF());
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

    private <T> void writeWithException(Collection<T> collection, ConsumerWithIOException<? super T> action) throws IOException {
        for (T t : collection) {
            action.accept(t);
        }
    }
}
