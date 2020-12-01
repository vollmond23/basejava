package ru.javaops.webapp.exception;

public class ExistContactException extends ResumeException{
    public ExistContactException(String contactTitle) {
        super("ERROR: Contact '" + contactTitle + "' already exist");
    }
}
