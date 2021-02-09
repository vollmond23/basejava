package ru.javaops.webapp.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.Section;
import ru.javaops.webapp.model.TextSection;

import static ru.javaops.webapp.TestData.R2;

public class JsonParserTest {

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(R2);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R2, resume);
    }

    @Test
    public void write() {
        Section section1 = new TextSection("TextSection");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}