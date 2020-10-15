package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (isRoomy() && !isExists(getIndex(resume.getUuid()))) {
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
