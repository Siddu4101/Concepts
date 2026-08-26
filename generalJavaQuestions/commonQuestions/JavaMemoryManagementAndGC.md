# 🧠 Java Memory Management

---

## 📌 1. JVM Memory Overview

The JVM manages memory mainly through:

```text
                         JVM PROCESS
                              │
              ┌───────────────┴───────────────┐
              │                               │
            HEAP                         NON-HEAP / OTHER
              │                               │
       ┌──────┴──────┐                ┌───────┴────────┐
       │             │                │                │
     Young          Old          Metaspace          Code Cache
     Gen            Gen
       │
  ┌────┴────┐
 Eden   Survivor
```

### 🟢 Heap

Stores **objects and arrays**.

```java
Person p = new Person();
```

```text
Stack                         Heap
┌──────────────┐             ┌───────────────┐
│ p ───────────┼────────────►│ Person object  │
└──────────────┘             └───────────────┘
```

The heap is managed by the Garbage Collector.

### 🔵 Stack

Each thread has its **own JVM stack**.

A stack contains frames for method calls:

```text
Thread
  │
  ▼
┌───────────────┐
│ main() frame  │
├───────────────┤
│ foo() frame   │
├───────────────┤
│ bar() frame   │
└───────────────┘
```

A frame contains things such as:

* Local variables
* Primitive values
* Object references
> ⚠️ The **object itself is generally on the heap**; a local reference to it can be stored in a stack frame.

---

# 🧩 2. Where Does Java Code Go?

Consider:

```java
public class Demo {

    static int count = 10; // metaspace

    public static void main(String[] args) {
        int x = 20;

        Person p = new Person("Tom");
    }
}
```

Conceptually:

```text
STACK
┌────────────────────────┐
│ main() frame           │
│                        │
│ x = 20                 │
│ p ─────────────────────┼─────────┐
└────────────────────────┘         │
                                   ▼
HEAP                         ┌───────────────┐
                             │ Person        │
                             │ name ─────────┼──────┐
                             └───────────────┘      │
                                                   ▼
                                           String "Tom"
                                           / String Pool
```

Static var, Class metadata, runtime constant-pool information, JIT-generated code and other JVM implementation data are managed outside the ordinary Java object heap; the exact physical organization is JVM-implementation dependent.

---

# 🧵 3. String Pool

Java maintains a **String Intern Pool** so identical interned strings can be reused.

```java
String s1 = "Hello";
String s2 = "Hello";
```

Conceptually:

```text
STACK

s1 ───────────────┐
                  │        ___________
s2 ───────────────┼──────►| "Hello"    |
                          |String Pool |
                           ------------
```

Therefore:

```java
s1 == s2       // true
s1.equals(s2)  // true
```

The pool avoids creating duplicate String objects for identical literals. String objects are immutable, which makes this sharing possible.

---

# 🆕 4. `new String()` vs String Literal

```java
String a = "Hello";

String b = new String("Hello");
```

Conceptually:

```text
String Pool
┌───────────────┐
│ "Hello"       │◄──────── a
└───────────────┘
       ▲
       │
       │ used by constructor
       │
Heap
┌────────────────┐
│ new String()   │◄──────── b
│ value="Hello"  │
└────────────────┘
```

So:

```java
a == b        // false
a.equals(b)   // true
```

`new String("Hello")` explicitly creates another String object.

---

# 🔗 5. `intern()`

`intern()` asks the JVM for the **canonical pooled String**.

```java
String a = new String("Hello");

String b = a.intern();

System.out.println(b == "Hello"); // true
```

Think of:

```text
new String("Hello")
        │
        ▼
   Heap String
        │
        │ intern()
        ▼
 String Pool
   "Hello"
```

### Why use `intern()`?

When you have many duplicate strings:

```text
"ABC"
"ABC"
"ABC"
"ABC"
"ABC"
...
```

interning can allow them to share the canonical String object.

⚠️ Don't blindly intern everything. Excessive interning can increase memory pressure and lookup overhead.

---

# 🗑️ 6. Can GC Remove Strings From the String Pool?

**Yes, pooled String objects are subject to normal GC reachability rules in modern Java.**

If an interned String is no longer reachable, it can eventually be reclaimed.

```text
Before GC

Stack
  │
  ▼
"Hello" ─────► String Pool
```

After the last strong reference disappears:

```text
Stack
  │
  X

String Pool
┌───────────────┐
│ "Hello"       │  ← unreachable
└───────────────┘
          │
          ▼
         GC
          │
          X
```

The important distinction is that the **String Pool itself isn't a magical GC-proof memory area**. Modern JVMs manage pooled String objects under normal heap/GC rules.

---

# 🏗️ 7. Object Allocation

When you write:

```java
Person p = new Person();
```

the simplified flow is:

```text
new Person()
     │
     ▼
Does JVM have enough allocation space?
     │
     ▼
Allocate object
     │
     ▼
Initialize object
     │
     ▼
Return reference
     │
     ▼
     p
```

For generational collectors, newly allocated objects generally start in the **Young Generation / Eden**, although exact allocation behavior is collector and JVM implementation dependent.

---

# 🌱 8. Young Generation

A traditional generational heap looks like:

```text
                 HEAP
                  │
        ┌─────────┴─────────┐
        │                   │
      YOUNG                 OLD
        │
   ┌────┼─────┐
   │    │     │
 Eden  S0     S1
```

### Eden

Most newly created objects begin here.

```java
Person p = new Person();
```

Conceptually:

```text
Eden
┌──────────────────────────┐
│ Person                   │
│ Order                    │
│ String                   │
│ ...                      │
└──────────────────────────┘
```

---

# ♻️ 9. Minor / Young GC

When Eden becomes sufficiently full:

```text
Eden becomes full
       │
       ▼
 Young GC
       │
       ▼
Find live objects
       │
       ├──── Dead ─────► Reclaim
       │
       └──── Live ─────► Survivor
```

Example:

```text
Before GC

Eden
┌────────────────────────┐
│ A  X  B  X  C  X  D   │
└────────────────────────┘
     ↑     ↑     ↑
    dead  dead  dead


After GC

Survivor
┌────────────────────────┐
│ A   B   C   D          │
└────────────────────────┘
```

The exact mechanics differ between collectors.

---

# 🎂 10. Object Aging

Objects that survive collections may be promoted toward the Old Generation.

```text
        Eden
          │
       GC │
          ▼
     Survivor
          │
       GC │
          ▼
     Survivor
          │
       age
          ▼
       Old Gen
```

The idea:

> **Short-lived objects → Young Generation**

> **Long-lived objects → Old Generation**

This generational hypothesis is based on the observation that many objects die young.

---

# 💀 11. How Does GC Know an Object Is Dead?

GC doesn't simply check:

```java
reference == null
```

It determines **reachability**.

Starting from GC roots:

```text
GC ROOTS
  │
  ├── Local variables
  ├── Active thread references
  ├── Static references
  ├── JNI references
  └── JVM/runtime references
          │
          ▼
     Reachable objects
          │
          ▼
        LIVE
```

Anything not reachable through the collector's root traversal can become eligible for reclamation.

---

# 🔄 12. Basic GC Flow

```text
          ┌─────────────────────┐
          │ Object Allocation   │
          └──────────┬──────────┘
                     ▼
                  Eden
                     │
              Eden gets full
                     │
                     ▼
                Young GC
                     │
              ┌──────┴──────┐
              │             │
            Dead           Live
              │             │
              ▼             ▼
           Reclaim       Survivor
                            │
                         survives
                            │
                            ▼
                       Old Generation
                            │
                     Old Gen pressure
                            │
                            ▼
                    Major/Old collection
                            │
                            ▼
                     Reclaim unreachable
                            │
                            ▼
                         Memory
                          freed
```

> ⚠️ This is a **conceptual flow**. Modern collectors such as G1, ZGC and Shenandoah use more sophisticated concurrent and region-based algorithms.

---

# 🧹 13. Mark → Sweep → Compact

A simplified GC algorithm:

### 1️⃣ Mark

Find reachable objects.

```text
A ──► B ──► C

D       E

A,B,C = reachable
D,E   = unreachable
```

### 2️⃣ Sweep

Remove unreachable objects.

```text
Before:

[A][B][X][C][X][D]

After sweep:

[A][B][ ][C][ ][D]
```

### 3️⃣ Compact

Move live objects together.

```text
Before:

[A][B][ ][C][ ][D]

After:

[A][B][C][D][ ][ ]
```

This reduces fragmentation.

---

# ⚡ 14. Modern GC

Java provides multiple garbage collectors.

| Collector   | Main Idea                                                                                      |
| ----------- |------------------------------------------------------------------------------------------------|
| Serial GC   | Simple, single-threaded, all other application threads are stopped (JAVA <= 7)                 |
| Parallel GC | Parallel throughput-oriented GC, uses multi threads                                            |
| G1 GC       | Region-based, balanced latency/throughput default one, does sweep and compact both (JAVA >= 7) |
| ZGC         | Very low pause times                                                                           |
| Shenandoah  | Low-pause concurrent collection                                                                |

The selected collector depends on the JDK/version and JVM options.

For modern JDKs, **G1 is the common default collector**, but always verify with the actual JDK/runtime configuration rather than assuming the default across every Java version.

Check:

```bash
java -XX:+PrintCommandLineFlags -version
```

---

# 🧠 15. G1 GC — Important Interview Concept

G1 doesn't require thinking of the heap simply as:

```text
Young | Old
```

Instead, the heap is divided into **regions**:

```text
┌────┬────┬────┬────┬────┬────┐
│ E  │ E  │ S  │ O  │ O  │ E  │
├────┼────┼────┼────┼────┼────┤
│ O  │ E  │ O  │ E  │ S  │ O  │
└────┴────┴────┴────┴────┴────┘
```

Each region can represent Eden, Survivor or Old usage at a particular time.

G1 attempts to collect regions with the most reclaimable space first.

---

# 📍 16. Where Is Everything Stored?

| Item                   | Typical Location                                                            |
| ---------------------- | --------------------------------------------------------------------------- |
| Local primitive        | Stack frame                                                                 |
| Local object reference | Stack frame                                                                 |
| Object                 | Heap                                                                        |
| Array                  | Heap                                                                        |
| String literal object  | String Pool / Heap                                                          |
| Interned String        | String Pool / Heap                                                          |
| Class metadata         | Metaspace / JVM implementation                                              |
| Static fields          | Associated with class metadata/runtime structures; implementation-dependent |
| Method bytecode        | Class metadata / class-related memory                                       |
| JIT compiled code      | Code Cache                                                                  |
| Thread stack           | Per-thread native/JVM stack                                                 |
| Native allocations     | Native memory                                                               |

> ⚠️ Avoid saying **"everything is either stack or heap."** The JVM uses several memory areas and implementation-specific native memory.

---

# 🧪 17. Simple Example

```java
public class MemoryDemo {

    static int count = 10; /*metaspace*/

    public static void main(String[] args) {

        int x = 20;

        String s1 = "Hello";
        String s2 = new String("Hello");

        Person p = new Person();
    }
}
```

Conceptually:

```text
STACK
┌─────────────────────────┐
│ main()                  │
│                         │
│ x = 20                  │
│ s1 ─────────────────┐   │
│ s2 ───────────────┐ │   │
│ p ─────────────┐  │ │   │
└────────────────│──│─│───┘
                 │  │ │
                 │  │ │
                 ▼  │ ▼
STRING POOL          │
┌───────────────┐    │
│ "Hello"       │◄───┘
└───────────────┘
                     │
HEAP                 │
┌────────────────┐   │
│ String object  │◄──┘
│ "Hello"        │
└────────────────┘

┌────────────────┐
│ Person object  │◄── p
└────────────────┘
```

Therefore:

```java
s1 == s2        // false
s1.equals(s2)   // true
```

---

# 🔥 18. Important Interview Points

### `String`

```java
String a = "Java";
String b = "Java";

a == b           // true
```

Because both reference the same pooled String.

---

### `new String()`

```java
String a = "Java";
String b = new String("Java");

a == b           // false
```

Different String objects.

---

### `intern()`

```java
String a = new String("Java");

String b = a.intern();

b == "Java"      // true
```

`intern()` returns the canonical pooled String.

---

### Garbage Collection

```java
Person p = new Person();

p = null;
```

The `Person` object becomes **eligible for GC** if no other reachable reference points to it.

It does **not** mean:

```text
p = null
   ↓
object immediately deleted
```

Instead:

```text
unreachable
    ↓
eligible for GC
    ↓
GC decides when/how to reclaim
```

---

# 🧩 19. Key Mental Model

Remember this:

```text
                 JAVA APPLICATION
                         │
                         ▼
                 ┌──────────────┐
                 │ JVM MEMORY   │
                 └──────┬───────┘
                        │
          ┌─────────────┼──────────────┐
          ▼             ▼              ▼
       STACK           HEAP         NON-HEAP
          │             │              │
       References    Objects       Metaspace
       Primitives       │          Code Cache
                        │
                   ┌────┴────┐
                   │         │
                 Young      Old
                   │
               Eden/Survivor
           
                -------------    
                    
                GC ROOTS
                    │
                    ▼
              Reachability
                    │
             ┌──────┴──────┐
             ▼             ▼
           LIVE           DEAD
             │             │
             │             ▼
             │          Reclaim
             │
             ▼
           Survive
             │
             ▼
          Promote
             │
             ▼
          Old Gen
```

---

# 🎯 20. One-Minute Interview Answer

> **Java memory is broadly divided into heap, stacks and non-heap/native areas. Each thread has its own stack containing method frames, local variables and references, while objects and arrays are generally allocated on the heap. The heap is managed by the Garbage Collector and, with generational collectors, objects are typically allocated in the young generation and long-lived objects may be promoted to the old generation.**
>
> **String literals are maintained through the String Intern Pool, which is associated with the heap in modern Java. `intern()` returns the canonical pooled String. Garbage collection is based on reachability from GC roots rather than simply checking whether a reference variable is null. Unreachable objects become eligible for reclamation.**
>
> **Modern JVMs provide collectors such as Serial, Parallel, G1, ZGC and Shenandoah, each making different trade-offs between throughput, pause time and memory overhead.**

---

## 🧠 Cheat Sheet

```text
Stack
 └── Per thread
 └── Method frames
 └── Local variables
 └── References
 └── Primitives

Heap
 └── Objects
 └── Arrays
 └── String Pool
 └── Young Generation
     ├── Eden
     └── Survivor
 └── Old Generation

Metaspace
 └── Class metadata

Code Cache
 └── JIT compiled native code

GC
 └── Find reachable objects
 └── Reclaim unreachable objects
 └── May move/compact objects
 └── May promote surviving objects
```

### ⭐ Remember

**Reference ≠ Object**

```text
Reference → Object
```

**`null` reference ≠ immediate GC**

```text
null
 ↓
possibly unreachable
 ↓
eligible for GC
 ↓
GC eventually reclaims
```

**String Pool ≠ Stack**

```text
Reference → Stack
String object → Heap/String Pool
```

**Metaspace ≠ Heap**

```text
Objects       → Heap
Class metadata → Metaspace
```

**GC ≠ `free()`**

Java applications normally don't explicitly free individual objects; the GC determines when unreachable objects can be reclaimed.
