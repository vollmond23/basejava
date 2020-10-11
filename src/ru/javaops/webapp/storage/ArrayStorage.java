package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void save(Resume resume) {
        if (storageSize == storage.length) {
            System.out.println("ERROR: The array size would be exceeded.");
            return;
        }
        if (searchIndex(resume.getUuid()) != -1) {
            System.out.println("ERROR: Resume with uuid " + resume.getUuid() + " already exists.");
        } else {
            storage[storageSize] = resume;
            storageSize++;
        }

    }

    public void update(Resume resume) {
        int index = searchIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("ERROR: There is no resume with uuid " + resume.getUuid() + " in the storage.");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = searchIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR: There is no resume with uuid " + uuid + " in the storage.");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = searchIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR: There is no resume with uuid " + uuid + " in the storage.");
        } else {
            System.arraycopy(storage, index + 1, storage, index, storageSize - index - 1);
            storageSize--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storageSize];
        System.arraycopy(storage, 0, resumes, 0, storageSize);
        return resumes;
    }

    public int size() {
        return storageSize;
    }

    private int searchIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
