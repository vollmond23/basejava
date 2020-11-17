package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Map;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected Resume getSearchKey(String uuid) {
        for (Map.Entry<Object, Resume> entry : storage.entrySet()) {
            Resume currentResume = (Resume) entry.getKey();
            if (currentResume.getUuid().equals(uuid)) {
                return currentResume;
            }
        }
        return new Resume(uuid, "NEW_RESUME");
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put((Resume) searchKey, resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put((Resume) searchKey, resume);
    }
}
