package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

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

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        if (storageSize == STORAGE_LIMIT) {
            throw new StorageException("ERROR: The array size would be exceeded.", resume.getUuid());
        }
        insertElement(resume, (Integer) searchKey);
        storageSize++;
    }

    @Override
    protected void doDelete(Object searchKey) {
        fillDeletedElement((Integer) searchKey);
        storage[storageSize - 1] = null;
        storageSize--;
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    public int size() {
        return storageSize;
    }

    @Override
    protected List<Resume> getAllResumes() {
        Resume[] resumes = Arrays.copyOf(storage, storageSize);
        return Arrays.asList(resumes);
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume resume, int index);

    @Override
    protected abstract Integer getSearchKey(String uuid);
}
