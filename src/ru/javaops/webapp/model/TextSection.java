package ru.javaops.webapp.model;

public class TextSection implements Section {
    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void printToConsole() {
        System.out.println("    " + content);
    }
}
