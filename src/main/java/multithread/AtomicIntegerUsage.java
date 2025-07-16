package multithread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerUsage {
    /*
    * Here we are trying to update a counter in 2 threads simultaneously and expecting it to be 2000
    * 1. without atomic integer
    *    as we know the 2 threads may try to increment at a time which may not be taken into consideration
    *    which makes it to be lesser all the time
    *    we cant use the volatile key here bcz the volatile used to make sure fetched value is recent it won't make sure update
    *    done by only one thread at time, or it won't make it a thread safe or the synchronous updates
    * Sol:
    *   a. make the increment method synchronous which cost some locking mechanism and time
    *   b. use the automatic integer which is a thread safe counter
    *
    * */
    public static void main(String[] args) throws InterruptedException {
        SharedCounter sharedCounter = new SharedCounter();

        Thread incrementCounter1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedCounter.increment();
            }
        });

        Thread incrementCounter2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedCounter.increment();
            }
        });

        /*start the threads*/
        incrementCounter1.start();
        incrementCounter2.start();

        /*wait for threads to complete*/
        incrementCounter1.join();
        incrementCounter2.join();

        sharedCounter.printCounter();
    }
}

@Slf4j
class SharedCounter{

    /*a. use the synchronous method to get in sync and make sure the method is accessed by only one thread at a time*/
//    private int counter;
//
//    public synchronized void increment(){
//        counter += 1;
//    }
//
//    public void printCounter(){
//      log.info("counter value {}", counter);
//    }

    /*b. using atomic counter*/
    private final AtomicInteger counter = new AtomicInteger(0);

    public synchronized void increment(){
        counter.incrementAndGet();
    }

    public void printCounter(){
        log.info("counter value {}", counter.get());
    }
}