# 📚 Java Collections Framework (Compact Notes)

Java Collections Framework is a set of **interfaces, classes, and algorithms** to store and process groups of objects efficiently.

Before collections, arrays were commonly used but had limits:
- fixed size
- costly insert/delete in the middle
- limited built-in utility methods

## 🧭 Hierarchy Reference

<p align="center">
  <img src="/generalJavaQuestions/commonQuestions/CollectionFramework.png" alt="collection framework"></img>
</p>

- `(I)` = Interface
- `(C)` = Class
- `--` = Implements
- `-` = Extends

## 🧩 Core Types

### 1) `Iterable` (I)
- Base interface that provides `iterator()` and enables enhanced `for-each` traversal.

### 2) `Collection` (I)
- Root interface for most collections.
- Common methods: `add`, `remove`, `addAll`, `clear`, `isEmpty`, `size`, `contains`.

### 3) `List` (I) 📋
- Ordered collection, duplicates allowed, index-based access.
- Typical complexity (e.g., `ArrayList`): access `O(1)`, insert/delete (middle) `O(n)`.

**Implementations**
- `ArrayList` (C): resizable array, fast random access.
- `LinkedList` (C): doubly linked list, efficient insert/delete but lookup takes time.
- `Vector` (C): legacy synchronized list (thread-safe, usually slower than `ArrayList`).
- `Stack` (C): legacy LIFO (extends `Vector`); prefer `Deque` + `ArrayDeque`.

### 4) `Set` (I) 🧪
- No duplicates.
- Order not guaranteed (except ordered/sorted variants).

**Implementations**
- `HashSet` (C): backed by `HashMap`, allows one `null`, no order.
- `LinkedHashSet` (C): insertion order maintained (via linked structure).
- `TreeSet` (C): sorted set, backed by `TreeMap` (Red-Black Tree), `O(log n)` for add/remove/contains.

### 5) `Queue` (I) 🚶
- Typically follows FIFO.

**Implementations**
- `LinkedList` (C): doubly linked list, supports queue ops (`offer`, `poll`, `peek`).
- `PriorityQueue` (C): binary heap (default min-heap), priority-based ordering.
- `ArrayDeque` (C): circular array; works as queue and stack (`offerFirst/Last`, `pollFirst/Last`).

### 6) `Map` (I) 🗺️
- Stores key-value pairs.
- Not part of `Collection`/`Iterable` hierarchy.
- `HashMap` average ops are `O(1)` (`put/get/remove`), worst-case can degrade.

**Implementations**
- `HashMap` (C): no order, allows one `null` key and multiple `null` values.
- `LinkedHashMap` (C): maintains insertion order, allows one `null` key.
- `TreeMap` (C): key-sorted, Red-Black Tree, `O(log n)`, no `null` key.
- `Hashtable` (C): legacy synchronized map, no `null` key/value.
- `ConcurrentHashMap` (C): concurrent-friendly map, no `null` key/value.

## ⚙️ Internal Storage Cheat Sheet

| Collection      | Internal Storage                      |
| --------------- | ------------------------------------- |
| `ArrayList`     | Dynamic Array                         |
| `LinkedList`    | Doubly Linked List                    |
| `HashSet`       | `HashMap` (Array + Linked List/Tree)  |
| `LinkedHashSet` | `LinkedHashMap`                       |
| `TreeSet`       | `TreeMap` (Red-Black Tree)            |
| `ArrayDeque`    | Circular Array                        |
| `PriorityQueue` | Binary Heap (Array)                   |
| `HashMap`       | Array + Linked List/Red-Black Tree    |
| `LinkedHashMap` | `HashMap` + Doubly Linked List        |
| `TreeMap`       | Red-Black Tree                        |
