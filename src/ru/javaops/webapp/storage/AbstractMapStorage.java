package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {

    protected Map<Object, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedResumes = new ArrayList<>(storage.values());
        sortedResumes.sort(Comparator.comparing(Resume::getFullName));
        return sortedResumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
