package ru.javaops.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {

    public static void printFiles(String filePath, String tab) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            System.out.println(tab + file.getName() + "(dir)");
            tab += "- ";
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File file1 : fileList) {
                    printFiles(file1.getAbsolutePath(), tab);
                }
            }
        } else {
            System.out.println(tab + file.getName());
        }
    }

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalFile());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File(".\\src\\ru\\javaops\\webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        printFiles(".", "");
    }
}
