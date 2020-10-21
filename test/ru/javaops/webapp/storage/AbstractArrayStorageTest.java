package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.lang.reflect.Field;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume newResume = new Resume("uuid4");
        storage.save(newResume);
        Assert.assertEquals(4, storage.size());
        Assert.assertSame(storage.get("uuid4"), newResume);
    }

    @Test
    public void update() {
        Resume updResume = new Resume("uuid3");
        storage.update(updResume);
        Assert.assertSame(storage.get("uuid3"), updResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid2");
        Assert.assertEquals(2, storage.size());
        storage.get("uuid2");
    }

    @Test
    public void get() {
        Resume newResume = new Resume("uuid2");
        Assert.assertEquals(storage.get("uuid2"), newResume);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals(storage.get(UUID_1), new Resume(UUID_1));
        Assert.assertEquals(storage.get(UUID_2), new Resume(UUID_2));
        Assert.assertEquals(storage.get(UUID_3), new Resume(UUID_3));
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() {
        storage.save(new Resume("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void checkOverflow() {
        storage.clear();
        int size = 0;
        try {
            Field storageLimit = storage.getClass().getSuperclass().getDeclaredField("STORAGE_LIMIT");
            storageLimit.setAccessible(true);
            size = storageLimit.getInt(storage);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < size; i++) {
            try {
                storage.save(new Resume());
            } catch (StorageException e) {
                Assert.fail("Overflow was too soon.");
            }
        }
        storage.save(new Resume());
    }
}