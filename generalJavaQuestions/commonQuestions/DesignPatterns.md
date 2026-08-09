## 📋 Topics

1. [🧱 SOLID Principles](#solid-principles)
2. [🔁 DRY — Don't Repeat Yourself](#dry-principle)
3. [💡 KISS — Keep It Simple, Stupid](#kiss-principle)

---

<a id="solid-principles"></a>
# 1.  🧱 SOLID Principles in Java

> **SOLID** is a set of five object-oriented design principles that help us write code that is **maintainable, flexible, testable, and scalable**.

---

## 📌 SOLID at a Glance

| Principle                     | Meaning                                               |
| ----------------------------- | ----------------------------------------------------- |
| **S** — Single Responsibility | A class should have **one reason to change**          |
| **O** — Open/Closed           | Open for **extension**, closed for **modification**   |
| **L** — Liskov Substitution   | Subclasses should be **replaceable** for their parent |
| **I** — Interface Segregation | Prefer **small, specific interfaces**                 |
| **D** — Dependency Inversion  | Depend on **abstractions**, not concrete classes      |

---

## 1️⃣ S — Single Responsibility Principle

> A class should have **only one responsibility**.

❌ **Bad**

```java
class UserService {

    void createUser() {
        // Create user
    }

    void sendEmail() {
        // Send email
    }

    void generateReport() {
        // Generate report
    }
}
```

The class has multiple responsibilities.

✅ **Good**

```java
class UserService {
    void createUser() {
        // Create user
    }
}

class EmailService {
    void sendEmail() {
        // Send email
    }
}

class ReportService {
    void generateReport() {
        // Generate report
    }
}
```

🎯 **Idea:** Keep each class focused on one responsibility.

---

## 2️⃣ O — Open/Closed Principle

> Classes should be **open for extension but closed for modification**.

❌ **Bad**

```java
class PaymentService {

    void pay(String type) {
        if (type.equals("CARD")) {
            // Card payment
        } else if (type.equals("UPI")) {
            // UPI payment
        }
    }
}
```

Adding a new payment type requires modifying the existing class.

✅ **Good**

```java
interface Payment {
    void pay();
}

class CardPayment implements Payment {
    public void pay() {
        System.out.println("Card payment");
    }
}

class UpiPayment implements Payment {
    public void pay() {
        System.out.println("UPI payment");
    }
}
```

Now we can add:

```java
class CashPayment implements Payment {
    public void pay() {
        System.out.println("Cash payment");
    }
}
```

without modifying existing classes.

🎯 **Idea:** Extend behavior instead of modifying existing code.

---

## 3️⃣ L — Liskov Substitution Principle

> A child class should be usable wherever its parent class is expected **without breaking the behavior**.

❌ **Bad**

```java
class Bird {
    void fly() {
        System.out.println("Flying");
    }
}

class Penguin extends Bird {
    @Override
    void fly() {
        throw new UnsupportedOperationException();
    }
}
```

A `Penguin` cannot fulfill the contract of `Bird` because it cannot fly.

✅ **Good**

```java
interface Bird {
    void eat();
}

interface FlyingBird extends Bird {
    void fly();
}

class Sparrow implements FlyingBird {
    public void eat() {}
    public void fly() {}
}

class Penguin implements Bird {
    public void eat() {}
}
```

🎯 **Idea:** Don't force subclasses to support behavior they cannot actually perform.

- In method overriding, the parameter list must stay exactly the same as the parent method (you cannot widen or narrow parameter types).
- For the return type, Java allows a **covariant return type**, so the child can return a more specific type.
- For checked exceptions, the child method can throw the same exception, a narrower one, or no exception.

```java
import java.io.IOException;

class Parent {
    Parent test() {
        return new Parent();
    }

    void trial() throws Exception {
    }
}

class Child extends Parent {
    @Override
    Child test() {
        return new Child();
    }

    @Override
    void trial() throws IOException {
    }
}
```
---

## 4️⃣ I — Interface Segregation Principle

> Clients should not be forced to depend on methods they don't use.

❌ **Bad**

```java
interface Worker {
    void work();
    void eat();
}

class Robot implements Worker {

    public void work() {}

    public void eat() {
        // Robot doesn't eat 😕
    }
}
```

✅ **Good**

```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements Workable, Eatable {
    public void work() {}
    public void eat() {}
}

class Robot implements Workable {
    public void work() {}
}
```

🎯 **Idea:** Prefer multiple small interfaces over one large interface.

---

## 5️⃣ D — Dependency Inversion Principle

> High-level modules should depend on **abstractions**, not concrete implementations.

❌ **Bad**

```java
class MySQLDatabase {
    void save() {}
}

class UserService {

    private MySQLDatabase database = new MySQLDatabase();

    void saveUser() {
        database.save();
    }
}
```

`UserService` is tightly coupled to MySQL.

✅ **Good**

```java
interface Database {
    void save();
}

class MySQLDatabase implements Database {
    public void save() {}
}

class UserService {

    private final Database database;

    UserService(Database database) {
        this.database = database;
    }

    void saveUser() {
        database.save();
    }
}
```

Now we can easily switch implementations:

```java
Database db = new MySQLDatabase();
UserService service = new UserService(db);
```

🎯 **Idea:** Depend on interfaces/abstractions and inject implementations.

---

### ⭐ In One Sentence

> **SOLID helps us create code that is easier to change without breaking existing functionality.**


This supports:

* 🔌 Loose coupling
* 🧪 Easier testing
* 🔄 Easy implementation changes
* 🛠️ Better maintainability
* 📈 Scalable architecture


---

<a id="dry-principle"></a>
# 2. **DRY** 

## 🧠 DRY — Don't Repeat Yourself

> **Every piece of knowledge should have a single, authoritative representation in the codebase.**

The goal is to avoid **duplicated logic**, because when the same logic exists in multiple places, changes become error-prone.

### ❌ Without DRY

```java
class OrderService {

    double calculateTax(double amount) {
        return amount * 0.18;
    }
}

class InvoiceService {

    double calculateTax(double amount) {
        return amount * 0.18;
    }
}
```

If the tax rate changes, you have to remember to update it in multiple places. 😬

### ✅ With DRY

```java
class TaxService {

    double calculateTax(double amount) {
        return amount * 0.18;
    }
}

class OrderService {

    private final TaxService taxService;

    OrderService(TaxService taxService) {
        this.taxService = taxService;
    }
}
```

Now the tax calculation has **one source of truth**.

---

### ⚠️ DRY doesn't mean "remove every duplicate line"

For example:

```java
saveUser();
saveOrder();
```

and

```java
saveProduct();
saveCustomer();
```

may look structurally similar, but they could represent different business concepts.

**Don't blindly combine code just because it looks similar.**

> 🎯 **DRY = Don't duplicate knowledge/logic, not simply "don't repeat code."**

---

<a id="kiss-principle"></a>
# 3. KISS

## 💡 KISS — Keep It Simple, Stupid


> Prefer the simplest solution that correctly solves the problem.

The goal is to avoid unnecessary complexity, over-engineering, and clever code that makes the application harder to understand and maintain.

---

### ❌ Over-engineered

```java
class NumberProcessorFactory {

    NumberProcessor create() {
        return new EvenNumberProcessor();
    }
}

interface NumberProcessor {
    boolean process(int number);
}

class EvenNumberProcessor implements NumberProcessor {

    public boolean process(int number) {
        return number % 2 == 0;
    }
}
```

For a simple operation, this introduces unnecessary classes and abstractions.

### ✅ Keep It Simple

```java
boolean isEven(int number) {
    return number % 2 == 0;
}
```

---

## ⚠️ KISS Doesn't Mean "Always Write Less Code"

KISS means:

* Avoid unnecessary complexity
* Don't over-engineer
* Prefer readable solutions
* Use abstractions when they provide real value
* Choose the simplest solution that solves the actual problem

> Don't make simple problems complicated.

---

## 🧠 Other Important Design Principles

| Principle  | Full Form                     | Meaning                                                                       |
| ---------- | ----------------------------- |-------------------------------------------------------------------------------|
| **KISS**   | Keep It Simple, Stupid        | Avoid unnecessary complexity                                                  |
| **DRY**    | Don't Repeat Yourself         | Avoid duplicating the same knowledge or business logic                        |
| **YAGNI**  | You Aren't Gonna Need It      | Don't build functionality until it is actually needed                         |
| **SoC**    | Separation of Concerns        | Keep different responsibilities separated(ex: springboot layers repo,service) |
| **SOLID**  | *(see above)*                 | Five OOP design principles for maintainable and flexible code                 |

