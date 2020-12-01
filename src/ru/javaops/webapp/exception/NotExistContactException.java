package ru.javaops.webapp.exception;

public class NotExistContactException extends ResumeException{
    public NotExistContactException(String contactTitle) {
        super("ERROR: Contact '" + contactTitle + "' does not exist");
    }
}
