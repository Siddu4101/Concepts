# 📚 Java OOP Concepts Interview Guide

## 🎯 Overview
- **Class**: Blueprint for creating objects
- **Object**: Instance of a class

---

## 🏛️ Four Pillars of OOP

### 1️⃣ **Encapsulation**
Binding properties and behaviors in a class with data hiding

### 2️⃣ **Inheritance**
Borrowing properties and behavior from parent classes or interfaces

| Type | Example |
|------|---------|
| Single-level | `B extends A` |
| Multi-level | `C extends B` and `B extends A` |
| Hierarchical | `B, C extends A` |
| Multiple | `C implements A, B` |
| Hybrid | Combination of above |

**❌ Diamond Problem**
- **Issue**: Multiple inheritance of classes causes ambiguity (Java doesn't allow it)
  - Class A has `work()` → Classes B & C override it → Class D extends both B & C → Which implementation does D use?
- **Solution**: Use multiple interface implementation with abstract methods or default method resolution

### 3️⃣ **Abstraction**
Hiding implementation details and showing only essential features

**Two approaches:**
- **Interface**: Contract methods (abstract) with implementation in implementing classes
- **Abstract Class**: Similar to interface with ability to have concrete methods

### 4️⃣ **Polymorphism**
Same method name with different behavior based on parameters or overriding

| Type | When | Example |
|------|------|---------|
| **Compile-time** (Method Overloading) | Compiler decides | `add(int, int)` vs `add(int, int, int)` |
| **Runtime** (Method Overriding) | JVM decides | Interface reference calls correct implementation |

---

## 🔒 Final Keyword

| Target | Effect | Behavior |
|--------|--------|----------|
| `final` variable | Lock value/reference | Can be assigned only once |
| `final` reference | Lock reference | Reference unchanged, object may change |
| `final` method | Lock implementation | Cannot be overridden |
| `final` class | Lock inheritance | Cannot be extended |
