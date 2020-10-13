package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (storageSize == STORAGE_LIMIT) {
            System.out.println("ERROR: The array size would be exceeded.");
            return;
        }
        if (index >= 0) {
            System.out.println("ERROR: Resume with uuid " + resume.getUuid() + " already exists.");
        } else {
            index = -index - 1;
            System.arraycopy(storage, index, storage, index + 1, storageSize - index);
            storage[index] = resume;
            storageSize++;
        }

    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchKey);
    }
}
