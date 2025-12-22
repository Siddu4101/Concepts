package org.learning.interfaceAbstractClassFunctionalInterface.functionalInterface;

public class FunctionalInterfaceExample {
    public static void main(String[] args) {
        TestFunctionalInterface testFunctionalInterface = () -> System.out.println("Hello from lambda! coming from Interface");
        testFunctionalInterface.sayGreet();
    }

}
@FunctionalInterface /*to inform compiler it is a functional interface*/
interface TestFunctionalInterface{
    void sayGreet();

    static void staticMethod(){
        System.out.println("Interface static fun");
    }

    default void defaultMethod(){
        privateMethod();
        System.out.println("Interface default method");
    }
    private void privateMethod(){
        System.out.println("Interface private method");
    }

}
