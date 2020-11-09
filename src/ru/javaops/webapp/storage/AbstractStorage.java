package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void delete(Resume resume) {
        int searchKey = checkExist(resume.getUuid());
        fillDeletedElement(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        int searchKey = checkNotExist(resume.getUuid());
        insertElement(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        int searchKey = checkExist(resume.getUuid());
        updateElement(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        checkExist(uuid);
        return getElement(uuid);
    }

    private int checkNotExist(String uuid) {
        int searchKey = getKey(uuid);
        if (searchKey >= 0) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private int checkExist(String uuid) {
        int searchKey = getKey(uuid);
        if (searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract int getKey(String uuid);

    protected abstract void insertElement(Resume resume, int searchKey);

    protected abstract void fillDeletedElement(Resume resume, int searchKey);

    protected abstract void updateElement(Resume resume, int searchKey);

    protected abstract Resume getElement(String uuid);
}
