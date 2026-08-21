
# 🧵 Daemon Threads in Java

## 📌 What is a Daemon Thread?

A **Daemon Thread** is a background thread that provides support or services to **user threads**.

The JVM does **not** wait for daemon threads to finish. When all user threads complete, the JVM terminates the remaining daemon threads.

---

## ⚙️ Creating a Daemon Thread

Use `setDaemon(true)` **before** calling `start()`.

```java
@Slf4j
class DaemonThread {

    public static void main(String[] args) {
        /*Daemon thread will be terminated if no user thread is alive irrespective of it's current state
         * And this is not true for the non-daemon threads*/
        Thread dt2 = new Thread(() -> {
            while (true) {
                log.info("Hey i am daemon thread in infinite loop but will terminate if no user thread present");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
        });

        /*creation of daemon thread need to setDaemon(true) before starting the thread
         * if u set this flag after start it will throw IllegalThreadState and continue as user thread without failure
         * */
        dt2.setDaemon(true);
        dt2.start();
        log.info("This line to keep daemon alive for sometime");
    }
}

```

---

## 🔄 How It Works

```text
JVM Starts
    │
    ├── 👤 User Thread → main()
    │
    └── 👻 Daemon Thread → Background Task
              │
              ▼
       main() finishes
              │
              ▼
   No User Threads Remaining
              │
              ▼
       JVM Terminates 🚫
       Daemon Thread Stops
```

---

## ⚠️ Important Rules

* ✅ By default, threads are **User Threads**.
* 👻 Use `setDaemon(true)` to make a thread a daemon.
* ⏳ `setDaemon(true)` must be called **before** `start()`.
* ❌ Calling it after `start()` throws `IllegalThreadStateException`.
* 🚫 JVM does **not** wait for daemon threads to complete.
* 🔍 Check using `isDaemon()`.

```java
System.out.println(thread.isDaemon());
```

---

## 🆚 User Thread vs Daemon Thread

| Feature                  | 👤 User Thread        | 👻 Daemon Thread        |
| ------------------------ | --------------------- | ----------------------- |
| Keeps JVM alive          | ✅ Yes                 | ❌ No                    |
| JVM waits for completion | ✅ Yes                 | ❌ No                    |
| Default thread type      | ✅ Yes                 | ❌ No                    |
| Purpose                  | Main application work | Background/support work |

---

## 💡 Common Use Cases

* 🗑️ Garbage Collection
* 📊 Monitoring
* 🧹 Cleanup tasks
* 💾 Background auto-saving
* 🔄 Cache maintenance

---