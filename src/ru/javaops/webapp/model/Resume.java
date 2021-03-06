package ru.javaops.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    public static final Resume EMPTY = new Resume("");

    static {
        EMPTY.setSection(SectionType.OBJECTIVE, TextSection.EMPTY);
        EMPTY.setSection(SectionType.PERSONAL, TextSection.EMPTY);
        EMPTY.setSection(SectionType.ACHIEVEMENT, ListSection.EMPTY);
        EMPTY.setSection(SectionType.QUALIFICATIONS, ListSection.EMPTY);
        EMPTY.setSection(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
        EMPTY.setSection(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
    }

    // Unique identifier
    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        checkNullParameters(uuid, fullName);
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setContact(ContactType contactType, String contact) {
        checkNullParameters(contactType, contact);
        contacts.put(contactType, contact);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setSection(SectionType sectionType, Section section) {
        checkNullParameters(sectionType, section);
        sections.put(sectionType, section);
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (!contacts.equals(resume.contacts)) return false;
        return sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contacts.hashCode();
        result = 31 * result + sections.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fullName).append('\n');
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            stringBuilder.append("    ").append(entry.getKey().getTitle()).append(": ").append(entry.getValue()).append('\n');
        }
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            stringBuilder.append(entry.getKey().getTitle()).append('\n').append(entry.getValue());
        }
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    @SafeVarargs
    private final <T> void checkNullParameters(T... args) {
        for (int i = 0; i < args.length; i++) {
            Objects.requireNonNull(args[i], "Parameter #" + i + " must not be null");
        }
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact(ContactType type) {
        return getContacts().get(type);
    }

    public Section getSection(SectionType type) {
        return getSections().get(type);
    }
}
