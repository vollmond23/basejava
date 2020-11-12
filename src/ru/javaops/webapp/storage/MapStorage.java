package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
    protected int getIndex(Resume resume) {
        return storage.containsValue(resume) ? 0 : -1;
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
        List<Resume> resumeList = new ArrayList<>(storage.values());
        resumeList.toArray(resumes);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
