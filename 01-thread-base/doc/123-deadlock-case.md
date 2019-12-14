# 多线程死锁分析，案例介绍

死锁。多个锁相互交叉锁定代码

当线程T1持有资源A，线程T2持有资源B。此时线程T1想要获取资源B，并且线程T2想要获取资源A。两个线程都想要获取对方手中的资源，自己又不肯让出已有资源，一直僵持不下就形成了死锁。

程序在运行时什么事都没做（不输出日志、CPU内存等都没有发生变化），此时极有可能发生了死锁。需要查看程序运行情况。

1. 案例

   线程T1：

   ```java
   public class DeadLock {
       private OtherService otherService;
       public void setOtherService(OtherService otherService) {
           this.otherService = otherService;
       }
   
       // DeadLock的实例的锁-资源A
       private final Object LOCK = new Object();
   
       public void m1() {
           synchronized (LOCK) {
               System.out.println("********m1********");
               otherService.s1();
           }
       }
   
       public void m2() {
           synchronized (LOCK) {
               System.out.println("********m2********");
           }
       }
   }
   ```

   线程T2：

   ```java
   public class OtherService {
       private DeadLock deadLock;
       public void setDeadLock(DeadLock deadLock) {
           this.deadLock = deadLock;
       }
   
       // OtherService的实例的锁-资源B
       private final Object LOCK = new Object();
   
       public void s1() {
           synchronized (LOCK) {
               System.out.println("========s1========");
           }
       }
   
       public void s2() {
           synchronized (LOCK) {
               System.out.println("========s2========");
               deadLock.m2();
           }
       }
   }
   ```

   测试调用方法：

   ```java
   public static void main(String[] args) {
       DeadLock deadLock = new DeadLock();
       OtherService otherService = new OtherService();
       deadLock.setOtherService(otherService);
       otherService.setDeadLock(deadLock);
   
       new Thread(() -> {
           while (true) {
               deadLock.m1();
           }
       }, "T1").start();
       new Thread(() -> {
           while (true) {
               otherService.s2();
           }
       }, "T2").start();
   }
   ```

2. 查看死锁

   当程序运行时产生死锁，通过代码会很难分析出死锁在哪里产生。此时应通过工具jstack查看程序运行状态。

   1. 获取程序进程id（pid）。`jps`

      ```
      2086 DeadLockTest
      2007 KotlinCompileDaemon
      2106 Jps
      1194 
      1213 RemoteMavenServer36
      ```

   2. 查看程序运行状态。`jstack 2086`

      ```
      Found one Java-level deadlock:
      =============================
      "T2":
        waiting to lock monitor 0x00007fe848005758 (object 0x000000076adb9730, a java.lang.Object),
        which is held by "T1"
      "T1":
        waiting to lock monitor 0x00007fe8480042b8 (object 0x000000076adbd6a0, a java.lang.Object),
        which is held by "T2"
      
      Java stack information for the threads listed above:
      ===================================================
      "T2":
      	at com.tuyrk.chapter8.DeadLock.m2(DeadLock.java:28)
      	- waiting to lock <0x000000076adb9730> (a java.lang.Object)
      	at com.tuyrk.chapter8.OtherService.s2(OtherService.java:29)
      	- locked <0x000000076adbd6a0> (a java.lang.Object)
      	at com.tuyrk.chapter8.DeadLockTest.lambda$main$1(DeadLockTest.java:24)
      	at com.tuyrk.chapter8.DeadLockTest$$Lambda$2/2094548358.run(Unknown Source)
      	at java.lang.Thread.run(Thread.java:748)
      "T1":
      	at com.tuyrk.chapter8.OtherService.s1(OtherService.java:22)
      	- waiting to lock <0x000000076adbd6a0> (a java.lang.Object)
      	at com.tuyrk.chapter8.DeadLock.m1(DeadLock.java:22)
      	- locked <0x000000076adb9730> (a java.lang.Object)
      	at com.tuyrk.chapter8.DeadLockTest.lambda$main$0(DeadLockTest.java:19)
      	at com.tuyrk.chapter8.DeadLockTest$$Lambda$1/500977346.run(Unknown Source)
      	at java.lang.Thread.run(Thread.java:748)
      
      Found 1 deadlock.
      ```

      T2持有0x000000076adbd6a0，想要获取0x000000076adb9730；

      而T1持有0x000000076adb9730，想要获取0x000000076adbd6a0。

   