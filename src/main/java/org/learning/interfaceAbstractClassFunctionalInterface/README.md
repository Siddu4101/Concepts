# Interface 🆚 Abstract Class & Functional Interface

## 📑 Navigation

- [Interface Overview 🛝](#interface-overview)
- [Abstract class 🏛️](#abstract-class)
- [Functional Interfaces ⚡](#functional-interfaces)

<details>
<summary id="interface-overview"> <strong>Interface Overview 🧩</strong></summary>

🔗 Reference: [interfaces example](./interfaces)

- Interfaces help to loosely couple classes between different layers.
- Interfaces can't have final methods as they are designed to inherit but final avoids that
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

<details>
<summary id="abstract-class"><strong>Abstract class 🏛️</strong></summary>

🔗 Reference: [abstract class examples](./abstractclass)


### What is an Abstract Class?

- An <b>abstract class</b> is declared using the <code>abstract</code> keyword and may contain zero or more abstract methods.
- The <code>abstract</code> keyword is required for both the class and its abstract methods (methods without a body).
- You <b>cannot instantiate</b> an abstract class directly. Any subclass must implement all abstract methods, unless the subclass is also abstract.
- Abstract classes can have: It is similar to normal class and can have all things like normal class 
  - Constructors (used by subclasses)
  - Fields (instance/static/final)
  - Concrete methods
  - Final methods
  - Static methods
- <b>Abstract methods</b> <i>cannot</i> be <code>final</code>, <code>static</code>, or <code>private</code>:
  - <b>final</b> ⛔ prevents overriding
  - <b>static</b> 🏷️ is class-level (only method hiding, not overriding)
  - <b>private</b> 🔒 makes methods inaccessible to subclasses

```java
// Example: Abstract class and inheritance
public class AbstractClassExample extends AbstractClass1 {
    // If we don't implement abstractMethod(), this class must also be abstract
    public AbstractClassExample(String name) {
        super(name); // Calls abstract class constructor
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

abstract class AbstractClass1 extends AbstractClass2 { // You can extend one abstract class from another
    // Instance variable
    private String name;

    // Static and final variable
    private final static Integer CON = 100;

    // Constructor
    public AbstractClass1(String name) {
        this.name = name;
    }

    // Abstract method
    abstract void abstractMethod();

    // Instance method
    void instanceMethod() {
        System.out.println("Name is " + name);
        System.out.println("Instance method inside AbstractClass1");
    }

    // Final method
    final void finalMethod() {
        System.out.println("Final method inside AbstractClass1");
    }

    // Static method
    static void staticMethod() {
        System.out.println("Static method inside AbstractClass1");
    }
}

abstract class AbstractClass2 { // With 0 abstract methods, still an abstract class
}
```

---

### When to Choose Interface vs Abstract Class?

| ❓ Question                           | ✅ Choose         |
|---------------------------------------|------------------|
| Need fields / state?                  | Abstract class   |
| Need constructors?                    | Abstract class   |
| Need method implementations?          | Abstract class   |
| IS-A relationship?                    | Abstract class   |
| Classes are unrelated?                | Interface        |
| Need loose coupling / API contract?   | Interface        |
| Need multiple inheritance?            | Interface        |

</details>

<details>
<summary id="functional-interfaces"><strong>Functional Interfaces ⚡</strong></summary>

🔗 Reference: [functional interface examples](./functionalInterface)

### What is a Functional Interface? 💡

- A <b>functional interface</b> is an interface with exactly one abstract method (can have static, default, or private methods).
- Enables functional programming in Java using <b>lambda expressions</b>.

```java
@FunctionalInterface // Informs the compiler this is a functional interface
interface TestFunctionalInterface {
    void sayGreet();

    static void staticMethod() {
        System.out.println("Interface static fun");
    }

    default void defaultMethod() {
        privateMethod();
        System.out.println("Interface default method");
    }

    private void privateMethod() {
        System.out.println("Interface private method");
    }
}

public class FunctionalInterfaceExample {
    public static void main(String[] args) {
        TestFunctionalInterface testFunctionalInterface = () -> System.out.println("Hello from lambda! coming from Interface");
        testFunctionalInterface.sayGreet();
    }
}
```

---

### Common Built-in Functional Interfaces in Java

Below are some built-in functional interfaces

#### 1. Predicate<T> 🧐
Represents a boolean-valued function of one argument.
```java
Predicate<Integer> predicate = (num) -> num > 10;
System.out.println("Predicate test for 15: " + predicate.test(15));
```

#### 2. Function<T, R> 🔄
Takes one argument and returns a result.
```java
Function<String, String> function = (name) -> "Length of the name " + name + " is: " + name.length();
System.out.println("Function apply for 'Hello': " + function.apply("Hello"));
```

#### 3. Consumer<T> 🍽️
Performs an action on a single argument and returns no result.
```java
Consumer<String> consumer = (str) -> System.out.println("Consumed: " + str);
consumer.accept("Hello Consumer!");
```

#### 4. Supplier<T> 🏭
Supplies a result of a given type (no input argument).
```java
Supplier<Double> supplier = () -> Math.random();
System.out.println("Supplier provided value: " + supplier.get());
```

#### 5. BiFunction<T, U, R> ➗
Takes two arguments and returns a result.
```java
BiFunction<Integer, Integer, Integer> biFunction = (a, b) -> a + b;
System.out.println("BiFunction apply for 5 and 10: " + biFunction.apply(5, 10));
```

#### 6. UnaryOperator<T> ➡️
Special kind of Function that takes same type of input and returns sa,e type of output.
Takes one argument and returns a result of the same type.
```java
UnaryOperator<Integer> unaryOperator = (num) -> num * num;
System.out.println("UnaryOperator apply for 7: " + unaryOperator.apply(7));
```

#### 7. BinaryOperator<T> ✖️
Special kind of BiFunction that takes same type of input and returns same type of output.
Takes two arguments of the same type and returns a result of the same type.
```java
BinaryOperator<Integer> binaryOperator = (a, b) -> a * b;
System.out.println("BinaryOperator apply for 4 and 5: " + binaryOperator.apply(4, 5));
```

</details>

