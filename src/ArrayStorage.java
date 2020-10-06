/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int storageSize = 0;

    void clear() {
        for (int i = 0; i < storageSize; i++) {
            storage[i] = null;
        }
        storageSize = 0;
    }

    void save(Resume r) {
        int index = -1;
        if (storageSize == storage.length) {
            System.out.println("The array size would be exceeded.");
            return;
        }
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                System.out.println("Resume with such uuid already exists.");
                index = i;
            }
        }
        if (index != -1) {
            storage[storageSize] = r;
            storageSize++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int index = -1;
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                System.arraycopy(storage, i + 1, storage, i, storageSize - i - 1);
                storageSize--;
                break;
            }
        }
        if (index == -1) {
            System.out.println("No such resume in storage.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[storageSize];
        System.arraycopy(storage, 0, resumes, 0, storageSize);
        return resumes;
    }

    int size() {
        return storageSize;
    }
}
