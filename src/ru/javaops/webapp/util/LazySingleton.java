package ru.javaops.webapp.util;

public class LazySingleton {
    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    private static class LazySinletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
    public static LazySingleton getInstance() {
        return LazySinletonHolder.INSTANCE;
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
    }
}
