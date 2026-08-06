# 🌱 Spring Boot Transactions (`@Transactional`)

---

## 📌 What is a Transaction?

A **transaction** is a group of database operations that execute as a **single unit of work**.

It follows the **ACID** principles:

* **A**tomicity – All operations succeed or all fail.
* **C**onsistency – Keeps data in a valid state.
* **I**solation – Concurrent transactions don't interfere incorrectly.
* **D**urability – Committed changes are permanent.

---

## 🚀 Why use `@Transactional`?

Without a transaction:

```java
public void transferMoney() {
    withdraw();
    deposit(); // Exception occurs
}
```

Result:

* ✅ Money deducted
* ❌ Money not deposited

Database becomes inconsistent.

With `@Transactional`:

```java
@Transactional
public void transferMoney() {
    withdraw();
    deposit();
}
```

If any operation fails:

* 🔄 Transaction is rolled back.
* ✅ Database remains consistent.

---

## ⚙️ How Spring Handles Transactions

Spring creates a **proxy** around your service.

```
Client
   │
   ▼
Spring Proxy
   │
Begin Transaction
   │
Business Logic
   │
Commit / Rollback
```

The proxy:

1. Begins a transaction.
2. Executes the method.
3. Commits on success.
4. Rolls back on failure.

---

## 🔄 Commit vs Rollback

```java
@Transactional
public void createOrder() {
    orderRepository.save(order);
    paymentRepository.save(payment);
    inventoryRepository.updateStock();
}
```

### ✅ Success

```
Save Order
Save Payment
Update Stock

COMMIT
```

### ❌ Failure

```
Save Order
Save Payment
Exception

ROLLBACK
```

---

## 🔁 Rollback Rules

By default, Spring rolls back for:

* ✅ `RuntimeException`
* ✅ `Error`

Example:

```java
throw new NullPointerException();
```

It **does not** roll back for checked exceptions by default.

```java
throw new IOException();
```

To rollback on checked exceptions:

```java
@Transactional(rollbackFor = Exception.class)
```

---

## 🔒 Method Visibility

| Method                         | Transaction Works?    |
| ------------------------------ | --------------------- |
| ✅ Public                       | Yes                   |
| ⚠️ Protected / Package-private | Depends on proxy type |
| ❌ Private                      | No                    |

> `@Transactional` on a private method is ignored. The application still starts normally.

---

## 🔄 Self Invocation

```java
@Service
public class UserService {

    public void methodA() {
        methodB(); // No transaction
    }

    @Transactional
    public void methodB() {
    }
}
```

`methodB()` is called using `this.methodB()`, bypassing the Spring proxy.

Result: **No transaction is created.**

---

## 📚 Propagation

Defines how transactions behave when one transactional method calls another.

### Default: `REQUIRED`

```java
@Transactional(propagation = Propagation.REQUIRED)
```

* Join existing transaction if one exists.
* Otherwise create a new transaction.

---

## 📖 Read-Only Transactions

```java
@Transactional(readOnly = true)
public List<User> getUsers() {
    return repository.findAll();
}
```

Benefits:

* 🚀 Reduces Hibernate dirty checking. (if u have a transaction open u read from repository and update on java object it won't run the update for that modification on db without u do explicitly)
* 📖 Indicates the method only reads data.
* ⚡ Can enable database optimizations.

> `readOnly = true` is **not** a guarantee that updates are impossible. It is mainly an optimization hint.

---

# 🧠 Persistence Context & Dirty Checking

One of the most important JPA concepts.

```java
@Transactional
public void updateUser() {

    User user = repository.findById(1).get();

    user.setName("Tom");
}
```

No `save()` is called.

Still, Hibernate executes:

```sql
UPDATE user
SET name = 'Tom'
WHERE id = 1;
```

### Why?

When an entity is loaded inside a transaction, it becomes a **Managed Entity**.

Hibernate stores it in the **Persistence Context**.

```
Database
    │
findById()
    │
    ▼
Managed Entity
    │
setName("Tom")
    │
Dirty Checking
    │
Commit
    ▼
UPDATE Query
```

At commit time, Hibernate compares:

* Original entity (snapshot)
* Current entity

If changes are found, it automatically generates the `UPDATE` query.

This process is called **Dirty Checking**.

---

## ❌ When Auto Update Doesn't Happen

### No Transaction

```java
public void updateUser() {
    User user = repository.findById(1).get();
    user.setName("Tom");
}
```

The object is no longer managed after the repository call.

No automatic update occurs.

---

### Detached Entity

```java
User user = repository.findById(1).get();

// Transaction ends

user.setName("Tom");
```

The entity is detached.

Hibernate is no longer tracking it.

No update is sent to the database unless the entity is reattached.

---

## 📝 Summary

| Topic               | Key Point                                               |
| ------------------- | ------------------------------------------------------- |
| `@Transactional`    | Groups multiple DB operations into one atomic unit      |
| Commit              | All operations succeed                                  |
| Rollback            | Any failure rolls back the transaction                  |
| Default Rollback    | `RuntimeException` and `Error`                          |
| Checked Exceptions  | Use `rollbackFor` if rollback is required               |
| Default Propagation | `REQUIRED`                                              |
| Private Methods     | `@Transactional` is ignored                             |
| Self Invocation     | Bypasses Spring proxy                                   |
| `readOnly = true`   | Optimization hint for read operations                   |
| Dirty Checking      | Hibernate automatically detects entity changes          |
| `save()` Required?  | ❌ Not for managed entities inside an active transaction |

---

## 💡 Interview One-Liner

> **`@Transactional` ensures that a group of database operations executes as a single unit of work. Spring manages transactions using proxies, while Hibernate tracks managed entities through the persistence context and automatically synchronizes changes to the database using dirty checking when the transaction commits.**
