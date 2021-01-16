package ru.javaops.webapp;

public class Deadlock {
    private static final String STRING_1 = "string1";
    private static final String STRING_2 = "string2";

    public static void main(String[] args) {

        lockThread(STRING_1, STRING_2);
        lockThread(STRING_2, STRING_1);
    }

    private static <T> void lockThread(T lock1, T lock2) {
        Thread thread = new Thread(() -> {
                   synchronized (lock1) {
                       String threadName = Thread.currentThread().getName();
                       System.out.println(threadName + " locked " + lock1);
                       try {
                           Thread.sleep(100);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       synchronized (lock2) {
                           System.out.println(threadName + " locked " + lock2);
                       }
                   }
                });
        thread.start();
    }
}
