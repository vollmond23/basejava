package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract boolean isExist(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract List<Resume> getAllResumes();

    @Override
    public void delete(Resume resume) {
        LOG.info("Delete " + resume.getUuid());
        SK searchKey = getExistedSearchKey(resume);
        doDelete(searchKey);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume.getUuid());
        SK searchKey = getNotExistedSearchKey(resume);
        doSave(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume.getUuid());
        SK searchKey = getExistedSearchKey(resume);
        doUpdate(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(new Resume(uuid, uuid));
        return doGet(searchKey);
    }

    private SK getNotExistedSearchKey(Resume resume) {
        SK searchKey = getSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            LOG.warning("ERROR: Resume " + resume.getUuid() + " already exists.");
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    private SK getExistedSearchKey(Resume resume) {
        SK searchKey = getSearchKey(resume.getUuid());
        if (!isExist(searchKey)) {
            LOG.warning("ERROR: Resume " + resume.getUuid() + " is not exists.");
            throw new NotExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> allResumes = getAllResumes();
        Collections.sort(allResumes);
        return allResumes;
    }
}
