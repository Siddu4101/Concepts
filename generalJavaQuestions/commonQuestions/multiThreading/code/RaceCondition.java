package generalJavaQuestions.commonQuestions.multiThreading.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class RaceCondition {

    public static void main(String[] args) throws InterruptedException {
        log.info("=============Read modify and write problem===========");
        ReadModifyWrite readModifyWrite = new ReadModifyWrite();
        Thread t1 = new Thread(readModifyWrite);
        Thread t2 = new Thread(readModifyWrite);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        log.info("The final count {}", readModifyWrite.count);/*This not give 20000 every time due to race condition*/


        log.info("=============Check and act problem===========");
        CheckAndAct checkAndAct = new CheckAndAct();
        Thread t3 = new Thread(checkAndAct);
        Thread t4 = new Thread(checkAndAct);

        t3.start();
        t4.start();

        t3.join();
        t4.join();
        log.info("The final balance {}", checkAndAct.balance);/*both thread pass >=100 and deducted and balance is -ve (-60)*/

    }

}

/*1. read-modify-write*/
class ReadModifyWrite implements Runnable{
    int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++)
            count++;/*This is not atomic ops so intermediate ops can clash and miss the count */
    }
}

/*2. check and act*/
@Slf4j
class CheckAndAct implements Runnable{
    int balance = 100;

    @Override
    public void run() {
            if (balance >= 100) { /*both thread may pass this at once*/
                log.info("withdrawing money...");
                balance -= 80;
                log.info("new balance is {}", balance);
            }
            else {
                log.info("low balance {}....", balance);
            }
    }
}
