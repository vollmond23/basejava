package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume, int index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, storageSize - index);
        storage[index] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, storageSize - index - 1);

    }

    @Override
    protected int getIndex(Resume resume) {
        return Arrays.binarySearch(storage, 0, storageSize, resume);
    }
}
