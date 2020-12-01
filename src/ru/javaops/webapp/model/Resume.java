package ru.javaops.webapp.model;

import ru.javaops.webapp.exception.ExistContactException;
import ru.javaops.webapp.exception.ExistSectionException;
import ru.javaops.webapp.exception.NotExistContactException;
import ru.javaops.webapp.exception.NotExistSectionException;

import java.util.*;
import java.util.logging.Logger;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private static final Logger LOG = Logger.getLogger(Resume.class.getName());

    // Unique identifier
    private final String uuid;
    private String fullName;
    private final HashMap<ContactType, String> contacts = new HashMap<>();
    private final HashMap<SectionType, Section> sections = new LinkedHashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        checkNullParameters(uuid, fullName);
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addContact(ContactType contactType, String contact) {
        checkNullParameters(contactType, contact);
        checkIfContactExists(contactType);
        contacts.put(contactType, contact);
    }

    public void editContact(ContactType contactType, String newContact) {
        checkNullParameters(contactType, newContact);
        checkIfContactNotExists(contactType);
        contacts.put(contactType, newContact);
    }

    public void deleteContact(ContactType contactType) {
        checkNullParameters(contactType);
        checkIfContactNotExists(contactType);
        contacts.remove(contactType);
    }

    public void addSection(SectionType sectionType, Section section) {
        checkNullParameters(sectionType, section);
        checkIfSectionExists(sectionType);
        sections.put(sectionType, section);
    }

    public void editSection(SectionType sectionType, Section newSection) {
        checkNullParameters(sectionType, newSection);
        checkIfSectionNotExists(sectionType);
        sections.put(sectionType, newSection);
    }

    public void deleteSection(SectionType sectionType) {
        checkNullParameters(sectionType);
        checkIfSectionNotExists(sectionType);
        sections.remove(sectionType);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        checkNullParameters(fullName);
        this.fullName = fullName;
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
        return "uuid = " + uuid + "; fullName = " + fullName;
    }

    public void printToConsole() {
        System.out.println(fullName + "\n");
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        System.out.println();
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            System.out.println(entry.getKey().getTitle());
            entry.getValue().printToConsole();
        }
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    private void checkIfContactExists(ContactType contactType) {
        if (contacts.containsKey(contactType)) {
            LOG.warning("ERROR: Contact '" + contactType.getTitle() + "' already exist");
            throw new ExistContactException(contactType.getTitle());
        }
    }

    private void checkIfContactNotExists(ContactType contactType) {
        if (!contacts.containsKey(contactType)) {
            LOG.warning("ERROR: Contact '" + contactType.getTitle() + "' does not exist");
            throw new NotExistContactException(contactType.getTitle());
        }
    }

    private void checkIfSectionExists(SectionType sectionType) {
        if (sections.containsKey(sectionType)) {
            LOG.warning("ERROR: Section '" + sectionType.getTitle() + "' already exist");
            throw new ExistSectionException(sectionType.getTitle());
        }
    }

    private void checkIfSectionNotExists(SectionType sectionType) {
        if (!sections.containsKey(sectionType)) {
            LOG.warning("ERROR: Section '" + sectionType.getTitle() + "' does not exist");
            throw new NotExistSectionException(sectionType.getTitle());
        }
    }

    @SafeVarargs
    private final <T> void checkNullParameters(T... args) {
        for (int i = 0; i < args.length; i++) {
            Objects.requireNonNull(args[i], "Parameter #" + i + " must not be null");
        }
    }
}
