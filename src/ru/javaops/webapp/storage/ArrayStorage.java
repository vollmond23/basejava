package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int storageSize = 0;

    private int searchIndex(Resume r) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                return i;
            }
        }
        return -1;
    }

    private int searchIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        Arrays.fill(storage, null);
        storageSize = 0;
    }

    public void save(Resume r) {
        if (storageSize == storage.length) {
            System.out.println("ERROR: The array size would be exceeded.");
            return;
        }
        if (searchIndex(r) != -1) {
            System.out.println("ERROR: Resume with such uuid already exists.");
        } else {
            storage[storageSize] = r;
            storageSize++;
        }

    }

    public void update(Resume r, String newUuid) {
        int index = searchIndex(r);
        if (index == -1) {
            System.out.println("ERROR: No such resume in storage.");
        } else {
            storage[index].setUuid(newUuid);
        }
    }

    public Resume get(String uuid) {
        int i = searchIndex(uuid);
        return i != -1 ? storage[i] : null;
    }

    public void delete(String uuid) {
        int i = searchIndex(uuid);
        if (i == -1) {
            System.out.println("ERROR: No such resume in storage.");
        } else {
            System.arraycopy(storage, i + 1, storage, i, storageSize - i - 1);
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
}
