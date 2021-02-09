package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;

import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "FULL_NAME_1");
        R2 = ResumeTestData.createResume(UUID_2, "FULL_NAME_2");
        R3 = ResumeTestData.createResume(UUID_3, "FULL_NAME_3");
        R4 = ResumeTestData.createResume(UUID_4, "FULL_NAME_4");
    }
}
