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
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                writeSection(entry.getKey(), entry.getValue(), dos);
            }
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

    private void writeSection(SectionType sectionType, Section section, DataOutputStream dos) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(sectionType.name());
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                dos.writeUTF(sectionType.name());
                List<String> list = ((ListSection) section).getContent();
                int listSize = list.size();
                dos.writeInt(listSize);
                for (String content : list) {
                    dos.writeUTF(content);
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                dos.writeUTF(sectionType.name());
                List<Organization> listOrg = ((OrganizationSection) section).getContent();
                dos.writeInt(listOrg.size());
                for (Organization organization : listOrg) {
                    Link link = organization.getHomePage();
                    dos.writeUTF(link.getName());
                    dos.writeUTF(link.getUrl());
                    List<Organization.Position> positionList = organization.getPositions();
                    dos.writeInt(positionList.size());
                    for (Organization.Position position : positionList) {
                        dos.writeUTF(position.getDateBegin().toString());
                        dos.writeUTF(position.getDateEnd().toString());
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(position.getDescription() != null ? position.getDescription() : "");
                    }
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
}
