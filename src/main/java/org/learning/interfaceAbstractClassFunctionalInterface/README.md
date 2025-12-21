
# Interface 🆚 Abstract Class & Functional Interface

## 📑 Navigation

- [Interface Overview](#interface-overview)

<details>
<summary id="interface-overview"> <strong>Interface Overview 🧩</strong></summary>

🔗 Reference: [interfaces](./interfaces)

- Interfaces help to loosely couple classes between different layers.
- All methods in an interface are <b>public</b> by default.
- Variables are <code>public static final</code> (non-mutable).
- Interfaces can have <b>abstract</b>, <b>default</b>, <b>static</b>, and <b>private</b> methods (private methods are accessible only inside default methods).
- <b>Default methods</b> can be overridden, but <b>static methods</b> (class properties) cannot be overridden, only hidden.

```java
@FunctionalInterface
interface InterfaceTest {
    String test = "Sid"; // public static final by default

    // Abstract method
    void abstractMethodTest();

    // Static method
    static void staticMethodTest() {
        System.out.println(test);
        System.out.println("This is coming from the InterfaceTest static method");
    }

    // Default method
    default void defaultMethodTest() {
        sayHi();
        System.out.println(test);
        System.out.println("This is coming from the InterfaceTest default method");
    }

    // Private method
    private void sayHi() {
        System.out.println("Hello, this is a private method inside the interface used only by default methods");
    }
}
```

### <strong>Functional Interface 💡</strong>

- An interface is a <b>@FunctionalInterface</b> if it has only one abstract method.
- This allows it to be used in lambda expressions.

```java
@FunctionalInterface
interface InterfaceTest {
    void abstractMethodTest();
    // ...other default/static methods
}
```

## <strong>Multiple Inheritance via Interface 🔗</strong>

- Interfaces provide <b>multiple inheritance</b>.
- You can call default methods using <code>Interface.super.methodName()</code>.
- Static methods are called directly via <code>Interface.staticMethodName()</code>.
- Abstract methods must be implemented in the concrete class.

```java
public class MultiPleInheritanceViaInterface implements InterfaceTest, InterfaceTest2 {
    @Override
    public void abstractMethodTest() {
        System.out.println("Implementation for abstract method from MultiPleInheritanceViaInterface");
    }

    @Override
    public void defaultMethodTest() {
        InterfaceTest.super.defaultMethodTest(); // from InterfaceTest
    }

    static void staticMethodTest() {
        InterfaceTest2.staticMethodTest(); // from InterfaceTest2
    }

    public static void main(String[] args) {
        InterfaceTest interfaceTest = new MultiPleInheritanceViaInterface();
        interfaceTest.abstractMethodTest();
        interfaceTest.defaultMethodTest();
        staticMethodTest();
    }
}
```

> ℹ️ <b>Note:</b> Interfaces can extend multiple interfaces, but classes cannot extend multiple classes (diamond problem).


</details>
