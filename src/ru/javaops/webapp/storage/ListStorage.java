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
    protected void insertElement(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void fillDeletedElement(Resume resume) {
        storage.remove(resume);
    }

    @Override
    protected void updateElement(Resume resume) {
        int index = storage.indexOf(new Resume(resume.getUuid()));
        storage.set(index, resume);
    }

    @Override
    protected Resume getElement(String uuid) {
        for (Resume resume : storage) {
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
        }
        return null;
    }
}
