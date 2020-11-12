package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void saveElement(Resume resume, int searchKey) {
        if (storageSize == STORAGE_LIMIT) {
            throw new StorageException("ERROR: The array size would be exceeded.", resume.getUuid());
        } else {
            insertElement(resume, searchKey);
        }
        storageSize++;
    }

    public void deleteElement(Resume resume, int searchKey) {
        fillDeletedElement(searchKey);
        storage[storageSize - 1] = null;
        storageSize--;
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

    @Override
    protected void updateElement(Resume resume, int searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected Resume getElement(int searchKey, String uuid) {
        return storage[searchKey];
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume resume, int index);
}
