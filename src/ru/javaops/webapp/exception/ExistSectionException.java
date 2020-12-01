package ru.javaops.webapp.exception;

public class ExistSectionException extends ResumeException{
    public ExistSectionException(String sectionTitle) {
        super("ERROR: Section '" + sectionTitle + "' already exist");
    }
}
