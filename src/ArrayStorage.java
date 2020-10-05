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
        boolean error = false;
        if (storageSize == storage.length) {
            System.out.println("The array size would be exceeded.");
            error = true;
        }
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                System.out.println("Resume with such uuid already exists.");
                error = true;
            }
        }
        if (!error) {
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
        if (storageSize == 0) {
            System.out.println("Storage is already empty.");
        } else {
            int oldSize = storageSize;
            for (int i = 0; i < storageSize; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    System.arraycopy(storage, i + 1, storage, i, storageSize - i - 1);
                    storageSize--;
                }
            }
            if (oldSize == storageSize) {
                System.out.println("No such resume in storage.");
            }
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
