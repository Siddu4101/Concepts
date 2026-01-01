package org.learning.interview.generalJavaQuestions.interfaceAbstractClassFunctionalInterface.interfaces;

public class MultiPleInheritanceViaInterface implements InterfaceTest, InterfaceTest2{
    @Override
    public void abstractMethodTest() {
        System.out.println("implementation for abstract method given from the MultiPleInheritanceViaInterface");
    }

    @Override
    public void defaultMethodTest() {
        InterfaceTest.super.defaultMethodTest(); /*from InterfaceTest*/
    }

    static void staticMethodTest(){
        InterfaceTest2.staticMethodTest(); /*from InterfaceTest2*/
    }

    public static void main(String[] args) {
        InterfaceTest interfaceTest = new MultiPleInheritanceViaInterface();
        interfaceTest.abstractMethodTest();
        interfaceTest.defaultMethodTest();
        staticMethodTest();
    }
}
