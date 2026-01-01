# 🛡️ Immutable Classes in Java

## ❓ What does immutable mean?
An **immutable class** is one whose state cannot be changed after creation.

To create it:
- Make the class `final`
- Fields should be `private` and `final`
- Initialize via constructor
- Don’t provide setters
- Use defensive copying for mutable fields

---

## 📝 How to Make a Class Immutable

### 1️⃣ Make the class `final`
Prevents inheritance and overriding, which could introduce mutability.

### 2️⃣ Do not provide setter methods
Only provide getters to avoid mutation.

### 3️⃣ Initialize all fields via constructor only
Use a constructor to set all fields at creation time.

### 4️⃣ Make all fields `private` and `final`
- `private`: Prevents outside modification
- `final`: Ensures fields are set only once

### 5️⃣ Use defensive copying for mutable fields
If a field is a mutable object (e.g., a reference to another class),
- Initialize it with a clone/copy
- Getter should return a clone/copy

---

## 🎯 Benefits of Using Immutable Classes

| Benefit        | Explanation                      |
| -------------- | -------------------------------- |
| 🧵 Thread-safe    | No synchronization needed        |
| 🤝 Safe to share  | No risk of accidental changes    |
| 🗃️ Easy caching   | Hashcode never changes           |
| 🔑 Reliable keys  | Perfect for `HashMap`, `HashSet` |
| 🧹 Cleaner design | Fewer bugs                       |

---

## ☕ Java Example: Immutable Class & Test

---

### 📦 Immutable Class Implementation
🔗 Reference : [Immutable class](../ImmutableClass)
```java
// making class as final for avoiding inheritance
public final class CreateImmutableClassInJava {
    // all the fields in the class will be private and final to avoid internal reinitialization of the field and external accessibility
    private final String name;

    // Reference object
    private final ReferenceObject referenceObject;
    private final List<String> friends;

    // initialize all fields inside the constructor
    public CreateImmutableClassInJava(String name, ReferenceObject referenceObject, List<String> friends) {
        this.name = name;
        ReferenceObject referenceObject1 = new ReferenceObject();
        referenceObject1.setRefName(referenceObject.getRefName());
        this.referenceObject = referenceObject1; // copied things from passed object and created new one and assigned that to original
        this.friends = new ArrayList<>(friends); // defensive copy if anybody changed the passed ref object later it won't change this class field ref
    }

    // for all the fields i have only the getters
    public String getName() {
        return name;
    }

    public ReferenceObject getReferenceObject() {
        ReferenceObject referenceObject1 = new ReferenceObject();
        referenceObject1.setRefName(referenceObject.getRefName());
        return referenceObject1; // defensive copy if any body get it and try to change it won't change this class ref field
    }

    public List<String> getFriends() {
        return new ArrayList<>(friends);
    }

    @Override
    public String toString() {
        return "CreateImmutableClassInJava{" +
                "name='" + name + '\'' +
                ", referenceObject=" + referenceObject +
                ", friends=" + friends +
                '}';
    }
}
```

---

### 🧩 Reference Object

```java
public class ReferenceObject {
    private String refName;

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    @Override
    public String toString() {
        return "ReferenceObject{" +
                "refName='" + refName + '\'' +
                '}';
    }
}
```

---

### 🧪 Test Immutable Class

```java
public class TestImmutableClass {
    public static void main(String[] args) {
        ReferenceObject referenceObject = new ReferenceObject();
        referenceObject.setRefName("Sid");
        ArrayList<String> friends = new ArrayList<>();
        friends.add("Raj");
        friends.add("Viju");
        CreateImmutableClassInJava createImmutableClassInJava = new CreateImmutableClassInJava("Siddu", referenceObject, friends);
        System.out.println(createImmutableClassInJava);

        // we will try to set the different value for the ref object and see is it changes (constructor test)
        // if i have used the direct assignment in the constructor it will mutate the original fields of my immutable class
        referenceObject.setRefName("Manjunath");
        friends.add("Nagaraj");
        // but i have used defensive copy so it didn't affected the fields
        System.out.println(createImmutableClassInJava);

        // now we will test for the getter
        // This will also affect my ref object if i don't use the defensive copy
        List<String> friends1 = createImmutableClassInJava.getFriends();
        ReferenceObject referenceObject1 = createImmutableClassInJava.getReferenceObject();
        friends1.add("Dyamanna");
        referenceObject1.setRefName("Basanna");
        System.out.println(createImmutableClassInJava);
    }
}
```
