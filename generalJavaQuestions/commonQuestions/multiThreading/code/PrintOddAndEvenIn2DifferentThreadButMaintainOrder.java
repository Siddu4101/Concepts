package generalJavaQuestions.commonQuestions.multiThreading.code;

public class PrintOddAndEvenIn2DifferentThreadButMaintainOrder {
/*
* This is not a guaranteed solution for maintaining the order as cpu/jvm decide which task to schedule but here it may looks works fins
* there can be issue in resource contention time so use the locks, notify or the semaphores for better solution
* */
    public static void main(String[] args) throws InterruptedException {

        Thread even = new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                if (i % 2 == 0)
                    System.out.println("Thread "+ Thread.currentThread().getName()+" even -->" + i);

                try {
                    Thread.sleep(2000);
                }catch (InterruptedException ex){}
            }
        });

        Thread odd = new Thread(() -> {
            for (int i = 0; i < 11; i++) {
                if (i % 2 != 0)
                    System.out.println("Thread "+ Thread.currentThread().getName()+" odd -->" + i);
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException ex){}
            }
        });

        even.start();
        Thread.sleep(1000);
        odd.start();
    }
}
