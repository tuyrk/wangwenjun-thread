# 通过实验分析This锁的存在

> this锁就是通过一个实例作为MONITOR，然后进行加锁。

1. 同时访问。

   ```java
   public synchronized void m1() {}
   ```

   ```java
   public void m2() {}
   ```

2. 顺序访问。两个线程运行方法时抢占同一个this锁。

   一个线程必须等待另一个线程访问的方法执行完成才能访问。

   ```java
   public synchronized void m1() {}
   ```

   ```java
   public synchronized void m2() {}
   ```

3. 同时访问。两个线程抢占的锁不一样。m1为this锁；m2为LOCK锁。

   ```java
   public synchronized void m1() {}
   ```

   ```java
   private final Object LOCK = new Object();
   public void m2() {
       synchronized (LOCK) {}
   }
   ```

4. 顺序访问。两个线程抢占同一个锁，即LOCK

   ```java
   private final Object LOCK = new Object();
   ```

   ```java
   public void m1() {
       synchronized (LOCK) {}
   }
   ```

   ```java
   public void m2() {
       synchronized (LOCK) {}
   }
   ```

   