package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.ResumeTestData;
import ru.javaops.webapp.model.Resume;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class StorageIOContextTest {
    private static final String PATH = ".\\storage";

    private static final String UUID_1 = "uuid1";

    private static final Resume R1;

    static {
        R1 = ResumeTestData.createResume(UUID_1, "FULL_NAME_1");
    }

    @Test
    public void saveFileContext() {
        StorageIOContext context = new StorageIOContext(new ObjectStreamStorage(new File(PATH)));
        context.clear();
        context.save(R1);
        assertEquals(1, context.size());
        assertEquals(R1, context.get(UUID_1));
    }

    @Test
    public void savePathContext() {
        StorageIOContext context = new StorageIOContext(new ObjectStreamPathStorage(PATH));
        context.clear();
        context.save(R1);
        assertEquals(1, context.size());
        assertEquals(R1, context.get(UUID_1));
    }
}