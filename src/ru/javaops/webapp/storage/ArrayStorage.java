package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (storageSize == STORAGE_LIMIT) {
            System.out.println("ERROR: The array size would be exceeded.");
            return;
        }
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("ERROR: Resume with uuid " + resume.getUuid() + " already exists.");
        } else {
            storage[storageSize] = resume;
            storageSize++;
        }

    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
