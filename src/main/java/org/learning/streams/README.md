# Streams


## 📑 Navigation

- [Stream Overview 🚀](#stream-overview)
- [How to Initialize Streams? 🛠️](#initialize-streams)

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
🔗Reference: [initialization of streams](/src/main/java/org/learning/streams/initialization)

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
