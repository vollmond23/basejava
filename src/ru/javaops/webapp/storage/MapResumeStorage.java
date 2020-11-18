package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Map;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected Resume getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getKey().equals(uuid)) {
                return entry.getValue();
            }
        }
        return new Resume(uuid, "NEW_RESUME");
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsValue(searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume resume = (Resume) searchKey;
        storage.remove(resume.getUuid());
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }
}
