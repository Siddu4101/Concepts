package org.learning.interview.generalJavaQuestions.interfaceAbstractClassFunctionalInterface.interfaces;

public class InheritanceInInterface implements InterfaceTest{

    /*overriding the abstract class which is compulsory*/
    @Override
    public void abstractMethodTest() {
        System.out.println("for abstract method Implementation given from the InheritanceInInterface class");
    }

    /*overriding the default method*/
    @Override
    public void defaultMethodTest() {
        System.out.println("This is coming from the InheritanceInInterface class by overriding default method");
    }

    /*method hiding*/
    static void staticMethodTest(){
        System.out.println("This is just handing the static method coming from the InterfaceTest");
    }

    public static void main(String[] args) {
        /*loose coupling as interface(parent) can hold the child*/
        InterfaceTest interfaceTest = new InheritanceInInterface();

        interfaceTest.abstractMethodTest();
        interfaceTest.defaultMethodTest();
        staticMethodTest(); /* u can call the InterfaceTest.staticMethodTest() to call the interface method*/
    }
}
