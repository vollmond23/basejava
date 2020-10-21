package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;
    protected int index;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void save(Resume resume) {
        if (storageSize == STORAGE_LIMIT) {
            throw new StorageException("ERROR: The array size would be exceeded.", resume.getUuid());
        }
        index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            addResume(resume);
            storageSize++;
        }
    }

    public void update(Resume resume) {
        index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public void delete(String uuid) {
        index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            delResume(index);
            storage[storageSize - 1] = null;
            storageSize--;
        }
    }

    public Resume get(String uuid) {
        index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
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

    protected abstract int getIndex(String uuid);

    protected abstract void addResume(Resume resume);

    protected abstract void delResume(int index);
}
