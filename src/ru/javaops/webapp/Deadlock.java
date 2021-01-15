package ru.javaops.webapp;

public class Deadlock {
    private static final int MILLIS = 100;
    private static final String STRING_1 = "string1";
    private static final String STRING_2 = "string2";

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            synchronized (STRING_1) {
                System.out.println("Thread1 locked string1");
                try {
                    Thread.sleep(MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (STRING_2) {
                    System.out.println("Thread1 locked string2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (STRING_2) {
                System.out.println("Thread2 locked string2");
                try {
                    Thread.sleep(MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (STRING_1) {
                    System.out.println("Thread2 locked string1");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
