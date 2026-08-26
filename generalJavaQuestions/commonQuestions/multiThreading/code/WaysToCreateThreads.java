package generalJavaQuestions.commonQuestions.multiThreading.code;

import lombok.extern.slf4j.Slf4j;

/*
* Via Thread class extend
* The Thread class implements the Runnable interface
* The start method is responsible for the thread creation and starting the run execution in it
* */
@Slf4j
class ViaThreadClass extends Thread{

    @Override
    public void run() {
        log.info("This is the task executed by Thread extended class, Thread name {} ",Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ViaThreadClass viaThreadClass = new ViaThreadClass();
        viaThreadClass.start();

        /*with single runnable impl class u can create n threads but this is not true when u have thread extended class everytime u need new instance for the new thread entry*/
        /* viaThreadClass.start(); --> illegalThreadState exception can't run same thread > 1 */
    }
}

/*
* Via Runnable interface implementation and passing it to Thread constructor
*
* */

@Slf4j
class ViaRunnableInterface implements Runnable{

    @Override
    public void run() {
        log.info("this is the task executed by runnable interface implementation, thread name {} ",Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ViaRunnableInterface viaRunnableInterface = new ViaRunnableInterface();
        Thread thread = new Thread(viaRunnableInterface);
        thread.start();

        /*
        * u can create multiple thread withe same runnable impl
        *  Thread threa2 = new Thread(viaRunnableInterface);
        *  thread2.start(); --> creates a new thread
        * */


        /*We no need to have the class which implements the Runnable interface to have a thread based execution we can just use
        * java 8 feature of lambda for anonymous function
        * */

        Thread viaLambdaAnonymousFun = new Thread(() -> log.info("this is the task executed by lambda impl of runnable anonymous fun, thread name {} ", Thread.currentThread().getName()));
        viaLambdaAnonymousFun.start();

    }
}
