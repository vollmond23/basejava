package ru.javaops.webapp.exception;

public class NotExistSectionException extends ResumeException{
    public NotExistSectionException(String sectionTitle) {
        super("ERROR: Section '" + sectionTitle + "' does not exist");
    }
}
