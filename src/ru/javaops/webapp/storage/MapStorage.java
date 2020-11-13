package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected void saveElement(Resume resume, int searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteElement(Resume resume, int searchKey) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected int getSearchKey(String uuid) {
        return storage.containsValue(new Resume(uuid)) ? 0 : -1;
    }

    @Override
    protected void updateElement(Resume resume, int searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(int searchKey, String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        return storage.values().toArray(resumes);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
