package generalJavaQuestions.commonQuestions.multiThreading.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class SynchronizedTest {
    public static void main(String[] args) throws InterruptedException {
        log.info("==========1. Instance Synchronized method/block single lock on this==========");
        Task sharedTask = new Task();
        Thread t1 = new Thread(sharedTask);
        Thread t2 = new Thread(sharedTask);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.info("The final count after increment {}", sharedTask.count);/*without sync gives wrong results*/

        log.info("==========2. With multiple instance without sync block also it works fine==============");
        /*if we have multiple instance of the object each get's it's own this and own lock on it so no waiting for any thread*/
        Task task3 = new Task();
        Task task4 = new Task();
        Thread t3 = new Thread(task3);
        Thread t4 = new Thread(task4);
        t3.start();
        t4.start();

        t3.join();
        t4.join();
        log.info("Without sync method or block with 2 different instance it gets own lock and work perfectly fine");
        log.info("task3 count {}", task3.count);
        log.info("task4 count {}", task4.count);


        log.info("==========3. With shared instance but multiple locks for each sync block===============");
        BankAccount bankAccount = new BankAccount();

        Thread t5 = new Thread(bankAccount);
        Thread t6 = new Thread(bankAccount);
        t5.start();
        t6.start();
        t5.join();
        t6.join();

        log.info("Here the benefit is that depositA and depositB can run parallel as they don't need same lock this they have " +
                "independent lockA and lockB if we used this then it should wait one to complete other");
        log.info("account A after deposit {}", bankAccount.accA);
        log.info("account B after deposit {}", bankAccount.accB);


        log.info("===========4. Static synchronized methods/blocks=============");
        StaticSync staticSync = new StaticSync();
        Thread t7 = new Thread(staticSync);
        Thread t8 = new Thread(staticSync);
        t7.start();
        t8.start();
        t7.join();
        t8.join();

        log.info("The static sync class locks the class(like Object.class) other static sync block or method should wait for this lock similar to " +
                "this in instance method locks in this ex instance method won't wait as it has this lock available while other static methods fighting for the class lock");
        log.info("Static counter {}", StaticSync.counter);
        log.info("instance counter {}", staticSync.instanceCounter);

    }

}

@Slf4j
class Task implements Runnable{
    int count = 0;

    @Override
//    public synchronized void run(){ /*instance sync method*/ --1
        public void run(){ /*instance sync method*/
        for (int i = 0; i < 10000; i++) {
//            synchronized (this) { /*instance sync block*/ --1
                count++;
//            }
        }
    }
}


@Slf4j
class BankAccount implements Runnable{
    int accA = 0;
    int accB = 0;

    /*individual locks*/
    private final Object lockA = new Object();
    private final Object lockB = new Object();

    void depositMoneyA(){
        for (int i = 0; i < 10000; i++) {
            synchronized (lockA) {
                accA++;
            }
        }
    }

    void depositMoneyB(){
        for (int i = 0; i < 10000; i++) {
            synchronized (lockB) {
                accB++;
            }
        }
    }

    @Override
    public void run() {
        depositMoneyA();
        depositMoneyB();
    }
}

class StaticSync implements Runnable {
    static int counter  = 0;
    int instanceCounter  = 0;

//    static synchronized void increment(){ /*static sync method --> 1*/
        static void increment(){
        for (int i = 0; i < 10000; i++) {
            synchronized (StaticSync.class){ /* static sync block can't use this as static --> 2*/
                counter++;
            }
        }
    }

    static void increment2(){
        for (int i = 0; i < 10000; i++) {
            synchronized (StaticSync.class){ /* if one static sync method/block already acquired the lock on class lock object u other static sync method should wait like instance method for this lock*/
                counter++;
            }
        }
    }

    void instanceIncrement(){
        for (int i = 0; i < 10000; i++) {
            synchronized (this){ /* This won't wait for the class object lock it id independent and just check for the this lock instance lock even incrementor 2 waits for StaticSync.class lock it can proceed without wait*/
                instanceCounter++;
            }
        }
    }


    @Override
    public void run() {
        increment();
        increment2();
        instanceIncrement();
    }
}