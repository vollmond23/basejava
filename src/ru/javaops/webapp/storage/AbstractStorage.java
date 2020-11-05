package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void delete(String uuid) {
        int index = checkNotExist(uuid);
        fillDeletedElement(index);
    }

    @Override
    public void save(Resume resume) {
        int index = checkExist(resume.getUuid());
        insertElement(resume, index);
    }

    @Override
    public void update(Resume resume) {
        int index = checkNotExist(resume.getUuid());
        updateElement(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = checkNotExist(uuid);
        return getElement(index);
    }

    protected int checkExist(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    protected int checkNotExist(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);

    protected abstract void updateElement(int index, Resume resume);

    protected abstract Resume getElement(int index);
}
