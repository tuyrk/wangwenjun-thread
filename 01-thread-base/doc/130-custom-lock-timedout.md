# 如何实现一个自己的显式锁Lock精讲下（让锁具备超时功能）

## 上一节中的问题：违规非法操作，导致运行混乱。巨大的BUG

```java
public static void main(String[] args) throws InterruptedException {
    final BooleanLock booleanLock = new BooleanLock();
    
    new Thread(() -> {
        try {
            booleanLock.lock();
            Optional.of(Thread.currentThread().getName() + " have the lock Monitor.").ifPresent(System.out::println);
            work();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            booleanLock.unlock();
        }
	  }, "T1");

    // 问题：违规非法操作，导致运行混乱
    Thread.sleep(100);
    booleanLock.unlock();
}
```

锁booleanLock由线程T1中加锁`lock()`，就只能由T1解锁`unlock()`。

解决：

```java
private Thread currentThread;

@Override
public synchronized void lock() throws InterruptedException {
    ...
    this.currentThread = Thread.currentThread();
}

@Override
public synchronized void unlock() {
    // 释放锁
    if (Thread.currentThread() == currentThread) {
        ...
    }
}
```



synchronized机制导致的一个问题：synchronized不能被打断，导致其他线程抢不到锁。

```java
public static void main(String[] args) throws InterruptedException {
    new Thread(SynchronizedProblem::run, "T1").start();
    Thread.sleep(1000);
    Thread t2 = new Thread(SynchronizedProblem::run, "T2");
    t2.start();
    
    Thread.sleep(2000);
    t2.interrupt();
    System.out.println(t2.isInterrupted());// true，但是并没有中断t2
}

private synchronized static void run() {
    System.out.println(Thread.currentThread().getName());
    while (true) { }
}
```

`t2.isInterrupted()`返回值为true，但是并没有中断t2，此时就需要让锁具备超时功能，即`void lock(long mills)`，mills时间拿不到锁就会超时，同时抛出`TimeOutException`异常。

## 让锁具备超时功能

调用的方法一直阻塞，但是还有其他工作需要执行，此时放弃调用方法需要使用`void lock(long mills)`。等待锁指定时间，一旦时间超过就抛出`TimeOutException`

```java
@Override
public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
    if (mills <= 0) {
        lock();
    }

    long hasRemaining = mills;
    long endTime = System.currentTimeMillis() + mills;
    while (initValue) {
        if (hasRemaining <= 0) {
            throw new TimeOutException("Time Out");
        }
        blockedThreadCollection.add(Thread.currentThread());
        this.wait(mills);
        hasRemaining = endTime - System.currentTimeMillis();
    }
    this.initValue = true;
    this.currentThread = Thread.currentThread();
}
```

`void wait()`需要synchronized，需要添加一个Monitor锁

Java5之后JUC并发包提供了相同的功能。由大神Doug Lea开发，使Java性能更加接近于C/C++。