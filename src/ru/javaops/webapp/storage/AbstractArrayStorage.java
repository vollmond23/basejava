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

    public void save(Resume resume) {
        if (storageSize == STORAGE_LIMIT) {
            throw new StorageException("ERROR: The array size would be exceeded.", resume.getUuid());
        } else {
            super.save(resume);
        }
        storageSize++;
    }

    public void delete(String uuid) {
        super.delete(uuid);
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
    protected void updateElement(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected Resume getElement(int index) {
        return storage[index];
    }
}
