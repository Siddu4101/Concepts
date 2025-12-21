package org.learning.interfaceAbstractClassFunctionalInterface.abstractClass;

public class AbstractClassExample extends AbstractClass1{ /*if we don't provide implementation for abstract method then this class also needs to be declared as abstract*/

    public AbstractClassExample(String name) {
        super(name);//calls abstract class constructor
    }

    @Override
    void abstractMethod() {
        System.out.println("Implementation for abstract method from AbstractClassExample");
    }

    public static void main(String[] args) {
        AbstractClassExample abstractClassExample = new AbstractClassExample("Sid");
        abstractClassExample.abstractMethod();
        abstractClassExample.instanceMethod();
        AbstractClass1.staticMethod();
    }
}

abstract class AbstractClass1 extends AbstractClass2{ /*we can extend one abstract class from another*/
    /*instance var*/
    private String name;

    /*static and final var*/
    private final static Integer CON = 100;

    /*constructor*/
    public AbstractClass1(String name) {
        this.name = name;
    }

    abstract void abstractMethod();

    /*instance method*/
    void instanceMethod(){
        System.out.println("Name is "+name);
        System.out.println("instance methods inside AbstractClass1");
    }

    /*final method*/
    final void finalMethod(){
        System.out.println("final method inside AbstractClass1");
    }

    /*static method*/
    static void staticMethod(){
        System.out.println("static method inside AbstractClass1");
    }
}

abstract class AbstractClass2{ /*with 0 abstract method it is still a abstract class*/
}
