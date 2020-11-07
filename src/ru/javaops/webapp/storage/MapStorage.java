package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected int getIndex(String uuid) {
        int index = 0;
        for (String keyUuid : storage.keySet()) {
            if (keyUuid.equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void fillDeletedElement(int index) {
        int i = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (i == index) {
                storage.remove(entry.getKey());
                break;
            }
            i++;
        }
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(int index) {
        int i = 0;
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (i == index) {
                return storage.get(entry.getKey());
            }
            i++;
        }
        return null;
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
