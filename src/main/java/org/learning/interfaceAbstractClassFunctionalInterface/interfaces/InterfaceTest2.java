package org.learning.interfaceAbstractClassFunctionalInterface.interfaces;

interface InterfaceTest2{
    /*abstract method*/
    void abstractMethodTest();

    /*static method*/
    static void staticMethodTest(){
        System.out.println("This is coming from the InterfaceTest2 static method");
    }

    /*default method*/
    default void defaultMethodTest(){
        System.out.println("This is coming from the InterfaceTest2 default method");
    }
}
