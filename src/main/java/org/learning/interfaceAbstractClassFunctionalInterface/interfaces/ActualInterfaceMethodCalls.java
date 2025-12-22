package org.learning.interfaceAbstractClassFunctionalInterface.interfaces;

public class ActualInterfaceMethodCalls implements InterfaceTest{
    @Override
    public void abstractMethodTest() {

    }

    public static void main(String[] args) {
        System.out.println("Hey "+InterfaceTest.test);
        InterfaceTest.staticMethodTest();
        new ActualInterfaceMethodCalls().defaultMethodTest();

    }
}
