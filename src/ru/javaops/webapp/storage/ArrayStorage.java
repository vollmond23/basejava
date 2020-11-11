package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume) {
        storage[storageSize] = resume;
    }

    @Override
    protected void fillDeletedElement(Resume resume) {
        storage[getIndex(resume)] = storage[storageSize - 1];
    }

    @Override
    protected int getIndex(Resume resume) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
