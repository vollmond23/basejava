package ru.javaops.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10_000;
    private static final Lock LOCK = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(SimpleDateFormat::new);

    private static AtomicInteger atomicCounter = new AtomicInteger();

    private static int counter;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " , " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + " , " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
//            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(threadLocal.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(counter);
        System.out.println(atomicCounter.get());
    }

    private void inc() {
        atomicCounter.incrementAndGet();
//        LOCK.lock();
//        try {
//            counter++;
//        } finally {
//            LOCK.unlock();
//        }
    }
}
