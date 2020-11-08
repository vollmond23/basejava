package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected int getKey(String uuid) {
        int searchKey = 0;
        for (String keyUuid : storage.keySet()) {
            if (keyUuid.equals(uuid)) {
                return searchKey;
            }
            searchKey++;
        }
        return -1;
    }

    private String getUuid(int searchKey) {
        int i = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (i == searchKey) {
                return entry.getKey();
            }
            i++;
        }
        return null;
    }

    @Override
    protected void insertElement(Resume resume, int searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void fillDeletedElement(int searchKey) {
        storage.remove(getUuid(searchKey));
    }

    @Override
    protected void updateElement(int searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(int searchKey) {
        return storage.get(getUuid(searchKey));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        List<Resume> resumeList = new ArrayList<>(storage.values());
        resumeList.toArray(resumes);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
