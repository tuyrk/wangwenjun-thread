# 如何实现一个自己的显式锁Lock精讲上

synchronized的机制导致的一个问题：当线程A调用使用synchronized加锁的线程B时，如果线程B的运行时间很长，则调用者线程A将会进入阻塞状态，且不可打断，也不能立即返回。

## 接口：

```java
/**
 * 自定义显式锁
 */
public interface Lock {
    class TimeOutException extends Exception {
        public TimeOutException(String message) {
            super(message);
        }
    }
    
    void lock() throws InterruptedException;
    void lock(long mills) throws InterruptedException, TimeOutException;
    void unlock();
    Collection<Thread> getBlockedThread();
    int getBlockedSize();
}
```

## BooleanLock实现类

```java
public class BooleanLock implements Lock {
    /**
     * The initValue is true indicated the lock has been get.
     * The initValue is false indicated the lock is free (other thread can get this).
     */
    private boolean initValue;
    private Collection<Thread> blockedThreadCollection = new ArrayList<>();

    public BooleanLock() {
        this.initValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        // 锁已经被其他线程使用
        while (initValue) {
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }
        // 锁未被使用，抢到锁立即设置initValue的值
        this.initValue = true;
        blockedThreadCollection.remove(Thread.currentThread());
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeOutException {}

    @Override
    public synchronized void unlock() {
        // 释放锁
        this.initValue = false;
        Optional.of(Thread.currentThread().getName() + " release the lock monitor.").ifPresent(System.out::println);
        this.notifyAll();
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        // 可以直接返回，但是不安全。此处返回的是一个实例，调用者可以随意更改（null，clear()等操作）。
        return Collections.unmodifiableCollection(blockedThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreadCollection.size();
    }
}
```

## 测试调用方法

```java
public static void main(String[] args) throws InterruptedException {
    final BooleanLock booleanLock = new BooleanLock();
    Stream.of("T1", "T2", "T3", "T4").forEach(name -> {
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
        }, name).start();
    });

    // 问题：违规非法操作，导致运行混乱
    Thread.sleep(100);
    booleanLock.unlock();
}

private static void work() throws InterruptedException {
    Optional.of(Thread.currentThread().getName() + " is working...").ifPresent(System.out::println);
    Thread.sleep(10_000);
}
```

## 问题

违规非法操作，导致运行混乱。巨大的BUG

```java
public static void main(String[] args) throws InterruptedException {
    final BooleanLock booleanLock = new BooleanLock();
    ...

    // 问题：违规非法操作，导致运行混乱
    Thread.sleep(100);
    booleanLock.unlock();
}
```

