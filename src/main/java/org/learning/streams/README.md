# Streams


## 📑 Navigation

- [Stream Overview 🚀](#stream-overview)
- [How to Initialize Streams? 🛠️](#initialize-streams)
- [Intermediate Operations 🧩](#intermediate-operations)

<details>
<summary id="stream-overview"><strong>Stream Overview 🚀</strong></summary>

## What are Streams?
Streams, introduced in Java 8, provide a functional and declarative way to process collections of data. They support operations like `filter`, `map`, `sort`, `reduce`, and more, making data processing concise and expressive.

### Types of Stream Operations
1. **Intermediate Operations** (return another Stream):
   - These operations transform the incoming data but do not process it until a terminal operation is invoked.
   - Examples: `filter`, `map`, `distinct`, etc.

2. **Terminal Operations** (produce result or side-effect):
   - These operations trigger the actual processing of the stream and produce a result or side-effect.
   - Examples: `forEach`, `reduce`, `allMatch`, etc.

> **Note:** Some operations are _short-circuiting_, meaning they stop processing once a condition is met:
> - Intermediate: `limit`
> - Terminal: `anyMatch`, `noneMatch`, `allMatch`, `findFirst`, `findAny`

## Why use Streams? 🤔
- Functional programming style
- Combine multiple operations in sequence
- Easy parallel processing (multi-threading) without extra configuration
- Lazy evaluation (operations are performed only when a terminal operation is called)
- Reduces boilerplate code
- Supports lambdas, method references, and constructor references

</details>


<details>
<summary id="initialize-streams"><strong>How to Initialize Streams? 🛠️</strong></summary>

🔗Reference code: [initialization of streams](./initialization)

There are several ways to create streams in Java:

### 1. Via Collections (`.stream()`)
Add the `stream()` method to a collection to obtain a stream:

```java
// List
List<Integer> listNumbers = List.of(1, 2, 3, 4);
listNumbers.stream();

// Map
Map<String, String> mapValues = Map.of("k1", "v1", "k2", "v2");
mapValues.entrySet().stream();
```

### 2. From Arrays (`Arrays.stream()`)
#### a. Non-primitive array
```java
String[] names = {"Sid", "Raj", "Viju"};
Stream<String> namesStream = Arrays.stream(names);
```
#### b. Primitive array
```java
int[] numbers = new int[]{1,2,3,4};
IntStream numberIntStream = Arrays.stream(numbers);
```
> **Note:**
> - `IntStream` is specific to integers. Similarly, there are `LongStream` and `DoubleStream`.
> - `float` falls under `DoubleStream`, and `byte`, `short`, `char` under `IntStream`. There is no stream for `boolean`.

### 3. From Sequence of Values (`Stream.of(...)`, `IntStream.of(...)`)
#### a. Non-primitive values
```java
Stream<String> namesStreamFromValues = Stream.of("Sid", "Raj", "Viju");
```
> For non-primitive, use `Stream.ofNullable()` to safely process null values:
```java
Stream<Object> nullableStream = Stream.ofNullable(null); // Safe, won't throw NPE when it is single item while Stream.of(null) usage throws 
```
#### b. Primitive values
```java
IntStream intStreamFromValues = IntStream.of(1, 2, 3, 4);
```

### 4. Empty Stream
```java
Stream<String> empty = Stream.empty(); // Handles nulls safely, no NPE
```

### 5. Infinite Streams ♾️
#### a. Using `generate`
```java
Stream<Double> generateRandomDoubles = Stream.generate(Math::random).limit(10); // Use limit to avoid infinite run
```
#### b. Using `iterate`
```java
// i. Simple iterate
Stream<Integer> iterateEvenNumbers = Stream.iterate(0, x -> x + 2).limit(11); // Use limit to avoid infinite run

// ii. Iterate with predicate (to stop generation)
Stream<Integer> iterateWithPredicate = Stream.iterate(0, x -> x < 5, x -> x + 1); // Middle param is stop predicate
```

### 6. Range and RangeClosed for Primitive Stream Generation
#### a. `range` (excludes second value)
```java
IntStream rangeExcludingSecondVal = IntStream.range(0, 11); // Prints 0 to 10
```
#### b. `rangeClosed` (includes second value)
```java
IntStream rangeClosedToIncludeSecondVal = IntStream.rangeClosed(1, 11); // Prints 1 to 11
```

</details>

<details>
<summary id="intermediate-operations"><strong>Intermediate Operations 🧩</strong></summary>

🔗Reference code: [intermediate operations](./intermediateoperations/)

## **Intermediate operations**
are lazy—they do not execute until a terminal operation is called, and they always produce another stream as output. 💤

---

### 1. `filter` 🕵️‍♂️
Filters elements based on a predicate and returns a stream of elements that satisfy the condition.

```java
IntStream.range(1, 11)
    .filter(num -> num % 2 == 0)
    .forEach(System.out::println); // even numbers between 1-10
```

### 2. `map` 🧮
Transforms each element in the stream to another form.

```java
IntStream.rangeClosed(1, 10)
    .map(num -> num * num)
    .forEach(System.out::println); // squares from 1 to 10
```
> **ℹ️ Note:**
> 
> There is another function called `mapToObj` which allows type conversion if you need a different stream type in between. 
> 
> - Using `map` on an `IntStream` fails for conversion to a different type:

```java
// ❌ This fails because map on IntStream produces another IntStream, not a Stream<String>
IntStream.rangeClosed(1, 10)
    .map(num -> String.valueOf(num))
    .forEach(System.out::println); // Compilation error
```

> - Using `mapToObj` works fine:

```java
// ✅ This works because mapToObj converts each int to a String, producing a Stream<String>
IntStream.rangeClosed(1, 10)
    .mapToObj(num -> String.valueOf(num))
    .forEach(System.out::println); //prints 1-10 
```

### 3. `flatMap` 🗂️
Converts nested lists to a single stream to process each item.

```java
List.of(List.of(1, 2, 3), List.of(4, 5, 6))
    .stream()
    .flatMap(Collection::stream)
    .forEach(System.out::println); // Output: 1,2,3,4,5,6
```

### 4. `distinct` 🔎
Returns distinct elements from the stream. For custom objects, `.equals()` is used to determine uniqueness.

```java
IntStream.of(1, 2, 3, 4, 2, 1)
    .distinct()
    .forEach(System.out::println); // 1,2,3,4
```

### 5. `sorted` 📊
Sorts the stream elements. Uses natural ordering for primitives; for custom objects, provide a comparator.

```java
List<Integer> integers = List.of(12, 43, 34, 23, 45, 26);
integers.stream().sorted().forEach(System.out::println); // ASC: 12 23 26 34 43 45
integers.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println); // DESC: 45 43 34 26 23 12

// For custom objects
List<User> users = List.of(new User(23, "Sid"), new User(38, "Mohan"), new User(12, "Ram"));
users.stream().sorted(Comparator.comparingInt(User::id)).forEach(System.out::println); // ASC
// Output: User[id=12, name=Ram] User[id=23, name=Sid] User[id=38, name=Mohan]
users.stream().sorted(Comparator.comparingInt(User::id).reversed()).forEach(System.out::println); // DESC
// Output: User[id=38, name=Mohan] User[id=23, name=Sid] User[id=12, name=Ram]
```

### 6. `limit` ⏸️
Short-circuits the stream after a given number of elements.

```java
integers.stream().limit(2).forEach(System.out::println); // 12 43
```

### 7. `skip` ⏭️
Skips the first N elements in the stream.

```java
integers.stream().skip(2).forEach(System.out::println); // 34 23 45 26
```

### 8. `peek` 👀
Used for debugging; allows you to see elements as they flow through the pipeline. Does not modify elements.

```java
integers.stream().filter(num -> num % 2 == 0).peek(System.out::println).count(); // 12 34 26
// For reference types, changes in peek do not affect the stream:
integers.stream().filter(num -> num % 2 == 0).peek(element -> element *= 10).forEach(System.out::println); // 12 34 26
```

### 9. `mapToInt`, `mapToLong`, `mapToDouble` 🔢
Converts objects to primitive streams for performance and access to primitive-specific methods like `sum`, `average`, etc.

```java
IntStream intStreamPrimitive = integers.stream().mapToInt(num -> num * 10); // 120 430 340 230 450 260
LongStream longStreamPrimitive = integers.stream().mapToLong(num -> num * 10); // 120 430 340 230 450 260
DoubleStream doubleStreamPrimitive = integers.stream().mapToDouble(num -> num * 10); // 120.0 430.0 340.0 230.0 450.0 260.0
```

### 10. `boxed` 📦
Converts primitive streams to wrapper types.

```java
Stream<Integer> boxedIntegers = IntStream.of(1, 2, 3, 4).boxed(); // 1,2,3,4
```

### 11. `takeWhile` ✋
Processes elements until the predicate fails (early stop). Best used with ordered streams.

```java
List.of(1, 2, 3, 4, 1, 2).stream().takeWhile(num -> num < 4).forEach(System.out::println); // 1 2 3
// If using filter, it would print: 1 2 3 1 2
```

### 12. `dropWhile` 🏃‍♂️
Skips elements while the predicate is true, then processes the rest.

```java
List.of(1, 2, 3, 4, 1, 2).stream().dropWhile(num -> num < 4).forEach(System.out::println); // 4 1 2
```

### 13. `unordered` 🔀
Hints that the stream does not need to maintain order, improving performance in parallel operations.
There are some operations which respects the order but if we know order is not imp we can improve the performance by declaring it
this can be used in places like `parallel`, `distinct`, `limit`, `takeWhile` etc..


```java
integers.stream().unordered().parallel().forEach(System.out::println); //23 26 45 12 43 34 here it not actually useful but just a usage example but u can see the result is unorderd
   
```

### 14. `parallel` & `sequential` 🧵
> - **`parallel()`** 🚀: Enables multi-threaded, unordered processing for time-efficient and CPU-bound tasks.
> - **`sequential()`** 🧑‍💻: Switches back to single-threaded, ordered processing.
> - You can combine both in a pipeline to optimize performance as needed.
> - Even with `parallel()`, if the terminal operation or collector is ordered, the result will be ordered. For guaranteed order, use `forEachOrdered()`.

```java
integers.stream()
    .parallel()
    .map(x -> x + 1)
    .sequential()
    .map(x -> x * 10)
    .forEach(System.out::println); // the things before sequential runs parallel and after that it runs in sequential mode
```
</details>
