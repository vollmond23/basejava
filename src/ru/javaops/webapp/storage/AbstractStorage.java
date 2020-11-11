package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void delete(Resume resume) {
        checkExist(resume.getUuid());
        fillDeletedElement(resume);
    }

    @Override
    public void save(Resume resume) {
        checkNotExist(resume.getUuid());
        insertElement(resume);
    }

    @Override
    public void update(Resume resume) {
        checkExist(resume.getUuid());
        updateElement(resume);
    }

    @Override
    public Resume get(String uuid) {
        checkExist(uuid);
        return getElement(uuid);
    }

    private void checkNotExist(String uuid) {
        if (getElement(uuid) != null) {
            throw new ExistStorageException(uuid);
        }
    }

    private void checkExist(String uuid) {
        if (getElement(uuid) == null) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void insertElement(Resume resume);

    protected abstract void fillDeletedElement(Resume resume);

    protected abstract void updateElement(Resume resume);

    protected abstract Resume getElement(String uuid);
}
