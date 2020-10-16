package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;
    protected int index;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void save(Resume resume) {
        if (resume.getUuid() == null) {
            System.out.println("ERROR: Uuid cannot be null.");
            return;
        }
        if (storageSize == STORAGE_LIMIT) {
            System.out.println("ERROR: The array size would be exceeded.");
            return;
        }
        index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("ERROR: Resume with uuid " + resume.getUuid() + " already exists.");
        } else {
            addResume(resume);
            storageSize++;
        }
    }

    public void update(Resume resume) {
        index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("ERROR: There is no resume with uuid " + resume.getUuid() + " in the storage.");
        } else {
            storage[index] = resume;
        }
    }

    public void delete(String uuid) {
        if (uuid == null) {
            System.out.println("ERROR: Uuid cannot be null.");
            return;
        }
        index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: There is no resume with uuid " + uuid + " in the storage.");
        } else {
            delResume(index);
            storageSize--;
        }
    }

    public Resume get(String uuid) {
        index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: There is no resume with uuid " + uuid + " in the storage.");
            return null;
        }
        return storage[index];
    }

    public int size() {
        return storageSize;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void addResume(Resume resume);

    protected abstract void delResume(int index);
}
