package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void delete(Resume resume) {
        int searchKey = checkExist(resume);
        deleteElement(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        int searchKey = checkNotExist(resume);
        saveElement(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        int searchKey = checkExist(resume);
        updateElement(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        int searchKey = checkExist(new Resume(uuid));
        return getElement(searchKey, uuid);
    }

    private int checkNotExist(Resume resume) {
        int searchKey = getIndex(resume);
        if (searchKey >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    private int checkExist(Resume resume) {
        int searchKey = getIndex(resume);
        if (searchKey < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    protected abstract int getIndex(Resume resume);

    protected abstract void saveElement(Resume resume, int searchKey);

    protected abstract void deleteElement(Resume resume, int searchKey);

    protected abstract void updateElement(Resume resume, int searchKey);

    protected abstract Resume getElement(int searchKey, String uuid);
}
