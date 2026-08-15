# 🚀Java Multithreading

---

## 🧩 Program, Process, and Thread

### 📄 Program

| Aspect | Program |
| --- | --- |
| **What is it?** | A set of instructions/code |
| **State** | Passive / on disk |
| **Example** | `.exe`, `.class`, `.jar` |
| **When does it become active?** | When the OS/runtime loads and executes it, creating a process |

### ⚙️ Process vs 🧵 Thread

| Aspect | ⚙️ Process | 🧵 Thread |
| --- | --- | --- |
| **Definition** | Running instance of a program | The smallest unit of execution within a process |
| **Managed by** | OS | OS + JVM/runtime in Java |
| **Execution** | Contains threads that execute instructions | Directly executes instructions |
| **Stack** | Contains stacks for its threads | Has its **own stack** |
| **Communication** | Uses IPC to communicate with other processes | Uses shared memory + synchronization |
| **Isolation** | Strong isolation from other processes | Little isolation from other threads in the same process |
| **Creation cost** | Relatively expensive | Relatively lightweight |
| **Context switching** | Generally more expensive | Generally cheaper |
| **Failure** | Process failure can terminate all its threads | A serious thread failure can affect the whole process |
| **Memory sharing** | ❌ Not normally shared with another process | ✅ Shared with threads in the same process |
| **Java example** | JVM running your application | `Thread` / `Runnable` |
| **Example** | `java MyApp` -> JVM process | `main`, `worker-1`, `worker-2` |
| **Number** | One program can have multiple processes running | One process can have multiple threads |

### 🗺️ Visual Representation

```text
                                PROGRAM 1
                                  │
                    ┌─────────────┴─────────────┐
                    │                           │
                    ▼                           ▼
               PROCESS 1                   PROCESS 2
          ┌─────────────────┐         ┌─────────────────┐
          │                 │         │                 │
          │  SHARED MEMORY  │         │  SHARED MEMORY  │
          │                 │         │                 │
          │ Heap / Objects  │         │ Heap / Objects  │
          │ Static Variables│         │ Static Variables│
          │                 │         │                 │
          │  ┌──────────┐   │         │  ┌──────────┐   │
          │  │ Thread 1 │   │         │  │ Thread 1 │   │
          │  │          │   │         │  │          │   │
          │  │  Stack   │   │         │  │  Stack   │   │
          │  └──────────┘   │         │  └──────────┘   │
          │                 │         │                 │
          │  ┌──────────┐   │         │  ┌──────────┐   │
          │  │ Thread 2 │   │         │  │ Thread 2 │   │
          │  │          │   │         │  │          │   │
          │  │  Stack   │   │         │  │  Stack   │   │
          │  └──────────┘   │         │  └──────────┘   │
          │                 │         │                 │
          └─────────────────┘         └─────────────────┘
                    │                           │
                    │                           │
                    │      INTER-PROCESS        │
                    └────── COMMUNICATION ──────┘
                           (IPC)
                    
                    
                 INTER-THREAD COMMUNICATION
                 ────────────────────────────
                 
                 Thread 1  ◄──── Shared Memory ────►  Thread 2
                    │                                   │
                    └──── Synchronization ──────────────┘
                       wait / notify / locks / etc.

```

## ❓How they fit in system? CPU, Cores & Threads


```text
              ☕ Java Application
                       │
                       ▼
              🧵 Java Threads
                       │
                ┌──────┴──────┐
                ▼             ▼
        Platform Threads   Virtual Threads
                │             │
                │        JVM / Carrier Threads
                │             │
                └──────┬──────┘
                       ▼
                🧠 OS Scheduler
                       │
              ┌────────┴────────┐
              ▼                 ▼
           🟢 P-Cores        🔵 E-Cores
              │                 │
              ▼                 ▼
        Hardware Threads  Hardware Threads
```
---

## 🧵 Hardware Thread

A core can sometimes expose multiple **hardware execution contexts** using:

* Intel → **Hyper-Threading**
* AMD → **SMT**

Example:

```text
Core
├── Hardware Thread 1
└── Hardware Thread 2
```

So:

```text
4 Cores × 2 HW Threads
        ↓
8 Logical Processors
```
The hardware threads share many resources of the same physical core.

---

## 💻  Software / Platform Thread

A software thread is created and managed by the **OS/runtime**.

Java example:

```java
Thread t = new Thread(() -> {
    // task
});

t.start();
```

Conceptually:

```text
Java Thread
     ↓
OS Scheduler
     ↓
CPU Core
     ↓
Hardware Thread
```

The OS decides **when and where** a thread runs.

## 🎯 Key Takeaways

| Concept                | Meaning                           |
| ---------------------- | --------------------------------- |
| 🖥️ **Processor**      | Entire physical CPU               |
| 🧠 **Core**            | Physical processing unit          |
| 🧵 **Hardware Thread** | Execution context provided by CPU |
| 💻 **Software Thread** | Thread managed by OS/runtime      |
| 🪶 **Virtual Thread**  | Lightweight JVM-managed thread    |


> **More cores → more physical parallelism.**
> **More software/virtual threads → more concurrent tasks, but not more CPU power.**

---

---
## 🪶 Java Virtual Threads & Carrier Threads


## 🧩 1. The Basic Idea

```text
🪶 Virtual Thread
       │
       │ mounted on
       ▼
🚗 Carrier Thread
       │
       ▼
⚙️ OS Scheduler
       │
       ▼
🧠 CPU Core
```

### ⭐ Remember

> **Virtual Thread = Task**
>
> **Carrier Thread = Platform thread that executes the task**

---

## 🪶 2. Virtual Thread

A **virtual thread** is a lightweight Java thread managed by the JVM.

```java
Thread.startVirtualThread(() -> {
    databaseCall();
});
```

You can create a very large number of virtual threads:

```text
🪶 VT-1
🪶 VT-2
🪶 VT-3
🪶 VT-4
   ...
🪶 VT-100,000
```

They are designed especially for applications with many **I/O-bound tasks**.

---

## 🚗 3. Carrier Thread

A **carrier thread** is a **platform/OS thread** used by the JVM to execute a virtual thread.

```text
Virtual Thread
      ↓
Carrier Thread
      ↓
OS Scheduler
      ↓
CPU
```

Unlike virtual threads, carrier threads are backed by real OS threads and are therefore much more expensive.

---

## 🔄 4. Mounting & Unmounting

A virtual thread is **mounted** on a carrier when it is executing.

```text
🪶 Virtual Thread
       │
       ▼
🚗 Carrier Thread
       │
       ▼
🧠 CPU
```

If the virtual thread performs a blocking operation such as waiting for I/O:

```text
🪶 Virtual Thread
       │
       ▼
📡 Database / Network
       │
       ▼
      ⏳ WAIT
```

The JVM can **unmount** the virtual thread from the carrier.

```text
🚗 Carrier Thread
       │
       └── 🪶 Virtual Thread → ⏳ Waiting
```

The carrier can then execute another virtual thread:

```text
🚗 Carrier Thread
       │
       └── 🪶 Virtual Thread B → 🏃 Running
```

When Virtual Thread A is ready:

```text
🪶 Virtual Thread A
       │
       ▼
Mounted onto an available carrier
       │
       ▼
▶️ Continues execution
```

---

## 📊 5. Platform Threads vs Virtual Threads

### Traditional Platform Threads

```text
Java Thread
     │
     ▼
OS / Platform Thread
     │
     ▼
CPU
```

Roughly:

```text
100 Java Threads
      ↓
~100 OS Threads
```

### Virtual Threads

```text
100,000 Virtual Threads
          │
          ▼
    JVM Scheduling
          │
          ▼
   Carrier Threads
          │
          ▼
      OS Scheduler
          │
          ▼
         CPU
```

The JVM multiplexes many virtual threads over a much smaller number of carrier/platform threads.

---

## 🎯 6. Why Virtual Threads?

Virtual threads are useful when your application has many tasks that spend time **waiting**.

For example:

```text
Request
   ↓
Database Call
   ↓
⏳ Waiting
   ↓
Process Result
   ↓
HTTP Call
   ↓
⏳ Waiting
```

Instead of keeping an expensive platform thread occupied during waiting, the virtual thread can be suspended while the carrier executes other work.

### ✅ Particularly useful for

* 🌐 HTTP requests
* 🗄️ Database operations
* 📡 Network calls
* 📂 I/O operations
* 🔄 High-concurrency applications

---

## ⚠️ 7. Important: Virtual Threads Don't Create CPU Power

Having:

```text
100,000 Virtual Threads
```

does **NOT** mean:

```text
100,000 tasks execute simultaneously
```

The actual CPU execution is still limited by your hardware:

```text
🪶 Virtual Threads
       ↓
🚗 Carrier Threads
       ↓
⚙️ OS Scheduler
       ↓
🧠 CPU Cores
```

> **Virtual threads increase concurrency, not the number of CPU cores.**

---

## 🔥 8. Key Differences

|               | 🪶 Virtual Thread    | 🚗 Carrier Thread    |
| ------------- | -------------------- | -------------------- |
| Type          | JVM-managed thread   | Platform/OS thread   |
| Cost          | Very lightweight     | Relatively expensive |
| OS thread     | ❌ No                 | ✅ Yes                |
| Number        | Very large           | Relatively limited   |
| CPU execution | Runs through carrier | Executes on CPU      |
| Mount/unmount | ✅ Yes                | —                    |
| Best benefit  | High concurrency     | Actual execution     |

---
> 🪶 **Virtual Thread:** "I have work to do."

> 🚗 **Carrier Thread:** "I'll provide the platform thread needed to execute that work."

---

## ⭐ One-Line Summary

```text
Virtual Threads
      ↓
Many lightweight concurrent tasks
      ↓
Mounted/unmounted on Carrier Threads
      ↓
Carrier Threads execute on CPU
```

* **🪶 Virtual threads = concurrency**
* **🚗 Carrier threads = execution mechanism**
* **🧠 CPU cores = actual hardware parallelism**


---

---

## 📌 What is Multithreading why it is needed?
**Multithreading** means performing multiple tasks in parallel using **threads**. A thread is the smallest unit of an application that can perform a specific task. Instead of executing tasks sequentially, you can create multiple threads to handle different operations simultaneously.

## Why ?

### 1. Parallel processing

Suppose your application has 3 tasks:
```text
Task A → Download file
Task B → Process data
Task C → Send email
```
With single thread each task runs sequentially 
```text
Main Thread
    │
    ├── Task A 
    │                    
    ├── Task B           
    │                    
    └── Task C           
```

With multi threading all can run parallel
```text
                 PROCESS
                    │
        ┌───────────┼───────────┐
        ↓           ↓           ↓
     Thread 1    Thread 2    Thread 3
        │           │           │
      Task A       Task B      Task C
```
### 2. Better CPU utilization 

Avoids the cpu idle time

### 3. Handles multiple user request for web like application

```text
                 Spring Boot
                     │
       ┌─────────────┼─────────────┐
       ↓             ↓             ↓
   Thread 1      Thread 2      Thread 3
       │             │             │
   Request A     Request B     Request C
```

## ⚠️ Issues with Multithreading

> It's important not to think: **"More threads = more performance."**
>
> Not necessarily.

### 🧠 Why Threads Add Complexity

```text
Multiple Threads
↓
Shared Data
↓
Race Conditions
↓
Need Synchronization
↓
Locks
↓
Potential Deadlocks
```

### 🔧 Sources of Overhead

* 🧵 Creating threads
* 🗓️ Scheduling threads
* 🔄 Context switching
* 🔐 Synchronization
* 🧠 Memory used by thread stacks

> So we use multithreading when it provides a benefit, not simply because we can.

---

---

# 🔄 Concurrency vs ⚡ Parallelism

## 🔄 Concurrency

**Concurrency** means multiple tasks are **in progress during the same time period**, but they don't necessarily execute at the exact same moment.

```text
Single Core
─────────────────────────────
Time →

Thread 1: ████      ████
Thread 2:     ████      ████
             ↑
        CPU switches
        between threads
```

### Example

A single CPU core handles:

```text
Thread 1 → Download file → ⏳ Waiting
Thread 2 → Process data
Thread 1 → Continue download
```

👉 Useful especially for **I/O-bound tasks** such as database calls, network requests, and file operations.

---

## ⚡ Parallelism

**Parallelism** means multiple tasks are **actually executing at the same time**, typically on different CPU cores.

```text
Multiple Cores
─────────────────────────────
Core 1 → Thread 1 ███████████
Core 2 → Thread 2 ███████████
Core 3 → Thread 3 ███████████
              ↓
       Same time ⚡
```

### Example

```text
Core 1 → Calculate Task A
Core 2 → Calculate Task B
Core 3 → Calculate Task C
```

👉 Useful especially for **CPU-intensive tasks**.

---

## 🆚 Quick Difference

|            | 🔄 Concurrency                   | ⚡ Parallelism                                                     |
| ---------- | -------------------------------- | ----------------------------------------------------------------- |
| Meaning    | Tasks make progress together     | Tasks execute simultaneously                                      |
| Cores      | Can work on a single core        | Requires multiple execution units for true simultaneous execution |
| Focus      | Managing multiple tasks          | Executing multiple tasks                                          |
| Common use | I/O-bound work                   | CPU-bound work                                                    |
| Example    | Multiple requests waiting for DB | Multiple calculations on different cores                          |

### 🧠 Remember

> **Concurrency = dealing with multiple tasks at once.**
> 
> **Parallelism = doing multiple tasks at the same time.**

---