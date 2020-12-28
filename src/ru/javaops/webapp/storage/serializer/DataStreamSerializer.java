package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            writeUTFtoDOS(dos, resume.getUuid(), resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            contacts.forEach((key, value) -> writeUTFtoDOS(dos, key.name(), value));
            resume.getSections().forEach((key, value) -> writeSection(dos, key, value));
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

    private void writeSection(DataOutputStream dos, SectionType sectionType, Section section) {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                writeUTFtoDOS(dos, sectionType.name(), ((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeUTFtoDOS(dos, sectionType.name());
                List<String> list = ((ListSection) section).getContent();
                writeIntToDOS(dos, list.size());
                writeUTFtoDOS(dos, list.toArray(new String[0]));
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeUTFtoDOS(dos, sectionType.name());
                List<Organization> listOrg = ((OrganizationSection) section).getContent();
                writeIntToDOS(dos, listOrg.size());
                for (Organization organization : listOrg) {
                    Link link = organization.getHomePage();
                    writeUTFtoDOS(dos, link.getName(), link.getUrl());
                    List<Organization.Position> positionList = organization.getPositions();
                    writeIntToDOS(dos, positionList.size());
                    positionList.forEach(x -> writeUTFtoDOS(dos,
                            x.getDateBegin().toString(),
                            x.getDateEnd().toString(),
                            x.getTitle(),
                            x.getDescription() != null ? x.getDescription() : ""));
                }
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
                        Link homePage = new Link(dis.readUTF(), dis.readUTF());
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

    private void writeUTFtoDOS(DataOutputStream dos, String... strings) {
        for (String string : strings) {
            try {
                dos.writeUTF(string);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void writeIntToDOS(DataOutputStream dos, int... ints) {
        for (int intElement : ints) {
            try {
                dos.writeInt(intElement);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
