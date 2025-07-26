package org.example.stream.multithreading;

import lombok.extern.slf4j.Slf4j;


public class VolatileKeyUsage {
    public static void main(String[] args) throws InterruptedException {
        /*
        * we have shared obj and has a property flag
        * 1. when flag is not a volatile
        *   we will try to introduce the wait for setFlag to 1sec so that read start first in a thread to read the flag first
        *   and the read will be stuck forever as it reads the cache even the flag is set after 1 sec
        *  2. make the flag as volatile so that it reads the value directly from the disk so it reflects in the read thread
        * */
        SharedObject sharedObject = new SharedObject();

        Thread writeFlgStatus = new Thread(() -> {
            try {
                Thread.sleep(1000);
                sharedObject.setFlag();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread readFlagStatus = new Thread(sharedObject::printFlag);

        /*start the threads*/
        writeFlgStatus.start();
        readFlagStatus.start();

        /*wait for thread completion*/
        writeFlgStatus.join();
        readFlagStatus.join();
    }
}

@Slf4j
class SharedObject{
//    private boolean flag;
    private volatile boolean flag;

    public void setFlag(){
        flag = true;
    }

    public void printFlag() {
        while (!flag){
            /*until flag is true do nothing*/
        }
        /*once flag is true come out of the loop and print the flag status*/
        log.info("flag status: {}",flag);
    }
}