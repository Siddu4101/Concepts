package generalJavaQuestions.commonQuestions.multiThreading.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class DaemonThread {

    public static void main(String[] args) {
        /*Daemon thread will be terminated if no user thread is alive irrespective of it's current state
        * And this is not true for the non-daemon threads*/
        Thread dt2 = new Thread(() -> {
            while (true) {
                log.info("Hey i am daemon thread in infinite loop but will terminate if no user thread present");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
        });

        /*creation of daemon thread need to setDaemon(true) before starting the thread
        * if u set this flag after start it will throw IllegalThreadState and continue as user thread without failure
        * */
        dt2.setDaemon(true);
        dt2.start();
        log.info("This line to keep daemon alive for sometime");
    }
}
