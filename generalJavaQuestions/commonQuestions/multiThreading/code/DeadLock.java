package generalJavaQuestions.commonQuestions.multiThreading.code;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DeadLock {

    public static void main(String[] args) throws InterruptedException {
        log.info("=================With synchronized locks==============");

        Runnable runnable = new Runnable() {
           private final Object lock1 = new Object();
           private final Object lock2 = new Object();

            @Override
            public void run() {
                log.info("acquiring lock1 by thread {}", Thread.currentThread().getName());
                synchronized (lock1){ /*order is different for both the section */
                    log.info("{} acquired lock1", Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {}

                    log.info("acquiring lock2 by thread {}", Thread.currentThread().getName());
                    synchronized (lock2){ /*acquired lock1 and waiting and waiting for lock2 circular wait*/
                        log.info("{} acquired lock2", Thread.currentThread().getName());
                    }
                }


                log.info("acquiring lock2 by thread {}", Thread.currentThread().getName());
                synchronized (lock2){ /*mutual exclusion as sync block can be accessed by only one thread*/
                    log.info("{} acquired lock2", Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {}

                    log.info("acquiring lock1 by thread {}", Thread.currentThread().getName());
                    synchronized (lock1){
                        log.info("{} acquired lock1", Thread.currentThread().getName());
                    }
                }
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.start();
        t2.start();
//
        log.info("================With reentrant try lock================");
        Runnable runnable2 = new Runnable() {

            private final ReentrantLock lock1 = new ReentrantLock();
            private final ReentrantLock lock2 = new ReentrantLock();

            @SneakyThrows
            @Override
            public void run() {
                log.info("acquiring lock1 by thread {}", Thread.currentThread().getName());
                if (lock1.tryLock()) {
                    try {
                        log.info("{} acquired lock1", Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                        log.info("acquiring lock2 by thread {}", Thread.currentThread().getName());
                        if (lock2.tryLock()) {
                            try {
                                log.info("{} acquired lock2", Thread.currentThread().getName());
                            } finally {
                                lock2.unlock();
                            }
                        }
                    } finally {
                        lock1.unlock();
                    }
                }

                log.info("acquiring lock2 by thread {}", Thread.currentThread().getName());
                 if (lock2.tryLock()) {
                    try {
                        log.info("{} acquired lock2", Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                        log.info("acquiring lock1 by thread {}", Thread.currentThread().getName());
                        if (lock1.tryLock()) {
                            try {
                                log.info("{} acquired lock1", Thread.currentThread().getName());
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } finally {
                        lock2.unlock();
                    }
                }
            }
        };
        /*
        * Reentrant lock just tries to acquire the lock if not possible just fail and move to next it won't wait indefinitely
        * */

        Thread t3 = new Thread(runnable2);
        Thread t4 = new Thread(runnable2);
        t3.start();
        t4.start();

    }
}
