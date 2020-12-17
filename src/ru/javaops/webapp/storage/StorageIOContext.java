package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.List;

public class StorageIOContext {
    private final Storage storage;

    public StorageIOContext(Storage storage) {
        this.storage = storage;
    }

    void clear() {
        storage.clear();
    }

    void save(Resume resume) {
        storage.save(resume);
    }

    void update(Resume resume) {
        storage.update(resume);
    }

    Resume get(String uuid) {
        return storage.get(uuid);
    }

    void delete(Resume resume) {
        storage.delete(resume);
    }

    int size() {
        return storage.size();
    }

    List<Resume> getAllSorted() {
        return storage.getAllSorted();
    }
}
