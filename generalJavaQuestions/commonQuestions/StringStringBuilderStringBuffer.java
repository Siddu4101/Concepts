package generalJavaQuestions.commonQuestions;

public class StringStringBuilderStringBuffer {


    public static void main(String[] args) throws InterruptedException {
        /*1. String*/
        String s1 = "Hello"; /*string pool*/
        String s2 = "Hello"; /*same element as above*/

        System.out.println(s1 == s2);

        String s3 = new String("Hello");
        String s4 = new String("Hello"); /*they r 2 different objects*/
        System.out.println(s3 == s4);
        System.out.println(s3.equals(s4));/*this compares the value not reference like ==*/

        String sharedString = "a";

        Thread thread1 = new Thread(new Task(sharedString));
        Thread thread2 = new Thread(new Task(sharedString));
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("The light of string after increment " + sharedString.length());/* 1 bcz everytime it creates the new but the count remains at 20001*/
        /*2. StringBuilder*/
        StringBuilder s5 = new StringBuilder("Hello");
        StringBuilder s6 = new StringBuilder("Hello");
        System.out.println(s5 == s6);
        System.out.println(s5.equals(s6));/*false bcz StringBuilder doesnt override the equals method*/
        System.out.println(s5.toString().equals(s6.toString()));

        StringBuilder sharedStringBuilder = new StringBuilder("a");
        Thread thread3 = new Thread(new TaskStringBuilder(sharedStringBuilder));
        Thread thread4 = new Thread(new TaskStringBuilder(sharedStringBuilder));
        thread3.start();
        thread4.start();

        thread3.join();
        thread4.join();

        System.out.println("The light of string after increment " + sharedStringBuilder.length());/*count varies always as non thread safe*/

        /*3. StringBuffer*/
        StringBuffer s7 = new StringBuffer("Hello");
        StringBuffer s8 = new StringBuffer("Hello");
        System.out.println(s7 == s8);
        System.out.println(s7.equals(s8));/*false bcz StringBuffer doesnt override the equals method*/
        System.out.println(s7.toString().equals(s8.toString()));

        StringBuffer sharedStringBuffer = new StringBuffer("a");
        Thread thread5 = new Thread(new TaskStringBuffer(sharedStringBuffer));
        Thread thread6 = new Thread(new TaskStringBuffer(sharedStringBuffer));
        thread5.start();
        thread6.start();

        thread5.join();
        thread6.join();

        System.out.println("The light of string after increment " + sharedStringBuffer.length()); /*always 20001*/

    }
}

class Task extends Thread{
    String string;
    Task(String s){
        this.string = s;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            string += "a";
        }
    }

}



class TaskStringBuilder extends Thread{
    StringBuilder stringbuilder;

    TaskStringBuilder(StringBuilder s){
        this.stringbuilder = s;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            stringbuilder.append("a");
        }
    }

}


class TaskStringBuffer extends Thread{
    StringBuffer stringBuffer;
    TaskStringBuffer(StringBuffer stringBuffer){
        this.stringBuffer = stringBuffer;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            stringBuffer.append("a");
        }
    }

}
