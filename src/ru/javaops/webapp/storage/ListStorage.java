package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        storage.toArray(resumes);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(Resume resume) {
        return storage.indexOf(resume);
    }

    @Override
    protected void saveElement(Resume resume, int searchKey) {
        storage.add(resume);
    }

    @Override
    protected void deleteElement(Resume resume, int searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void updateElement(Resume resume, int searchKey) {
        storage.set(searchKey, resume);
    }

    @Override
    protected Resume getElement(int index, String uuid) {
        return storage.get(index);
    }
}
