package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

public interface Storage {

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(Resume resume);

    Resume[] getAll();

    int size();
}
