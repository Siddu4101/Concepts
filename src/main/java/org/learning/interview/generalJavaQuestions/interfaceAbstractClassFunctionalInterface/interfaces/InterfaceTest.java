package org.learning.interview.generalJavaQuestions.interfaceAbstractClassFunctionalInterface.interfaces;

@FunctionalInterface
interface InterfaceTest{
    String test ="Sid";/*by default it is public static final*/

    /*abstract method*/
    void abstractMethodTest();

    /*static method*/
    static void staticMethodTest(){
        System.out.println(test);
        System.out.println("This is coming from the InterfaceTest  static method");
    }

    /*default method*/
    default void defaultMethodTest(){
        sayHi();
        System.out.println(test );
        System.out.println("This is coming from the InterfaceTest default method");
    }

    /*private method*/
    private void sayHi(){
        System.out.println("Hello this is private method inside the interface used by only the default methods");
    }
}