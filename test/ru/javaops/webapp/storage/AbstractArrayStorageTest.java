package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.ResumeTestData;
import ru.javaops.webapp.exception.StorageException;

import java.util.UUID;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        int length = AbstractArrayStorage.STORAGE_LIMIT;
        for (int i = 0; i < length; i++) {
            try {
                storage.save(ResumeTestData.createResume(UUID.randomUUID().toString(), "NEW_RESUME"));
                System.out.println(i);
            } catch (StorageException e) {
                fail("Overflow was too soon.");
            }
        }
        storage.save(ResumeTestData.createResume(UUID.randomUUID().toString(),"NEW_RESUME"));
    }
}