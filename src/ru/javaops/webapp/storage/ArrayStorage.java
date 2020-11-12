package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume, int index) {
        storage[storageSize] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = storage[storageSize - 1];
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
