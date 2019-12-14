# Daemon线程的创建以及使用场景分析

Runnable不是线程，它只是一个接口，只是一个任务执行单元。它的作用是把线程控制和业务逻辑分开。真正代表线程的只有Thread。

ThreadGroup的好处：可以统一管理Thread线程。



在JDK1.7之后可以将数据`10000`写为`10_000`



1. main线程此时逻辑单元已经结束，为什么还没有退出程序呢？

   因为此时ThreadGroup中还有线程Thread-0处于active状态。

   ```java
   public class DeamonThread {
       public static void main(String[] args) throws InterruptedException {
           // new
           Thread t = new Thread() {
               @Override
               public void run() {
                   System.out.println(Thread.currentThread().getName() + " running");
                   Thread.sleep(1_000_000);
                   System.out.println(Thread.currentThread().getName() + " done");
               }
           };
           // runnable -> running| -> blocked -> dead
           t.start();
   
           System.out.println(Thread.currentThread().getName());
       }
   }
   ```

2. `t.setDaemon(true);`在main结束后自动结束所有线程

   守护线程Daemon有什么作用呢？

   > 例：建立网络连接。
   > 从A到B建立了一个长连接，长连接在一定间隔时间会发送心跳包以证明连接是可用的。比如A为客户端，B为服务器，A不断向B发送心跳包，B向A返回心跳响应。
   > 当前工作线程创建了一个连接后，要维护这个长连接，但是当前工作线程的业务逻辑、发送数据等操作与维护连接并没有关系，此时就可以创建一个守护线程daemonThread来进行心跳检测工作。
   > 当工作线程已经结束工作而死亡，非守护线程并不会自动死亡，而此时再去进行长连接心跳检测并没有任何意义，所以将其设置为守护线程可以在工作线程死亡时自动死亡。



思考：

1. 在非守护线程A里创建守护线程B，当线程A结束后，线程B会自动结束么？

   线程B会自动结束

2. 在非守护线程A里创建非守护线程B，当线程A结束后，线程B会自动结束么，JVM可以正常退出么？

   线程B不会自动结束，仍然在运行。

3. 在守护线程A里创建守护线程B，当线程A结束后，线程B会自动结束么？

   会。

4. 在守护线程A里创建非守护线程B，当线程A结束后，线程B会自动结束么，JVM可以正常退出么？

   会。

   ```java
   Thread thread = new Thread(() -> {
       Thread innerThread = new Thread(() -> {
           while (true) {
               System.out.println("innerThread do something for health check.");
               Thread.sleep(1_000);
           }
       }, "innerThread");
   
       // innerThread.setDaemon(true);
       innerThread.start();
   
       Thread.sleep(1_000);
       System.out.println("thread finish done.");
   }, "thread");
   
   // innerThread.setDaemon(true);
   thread.start();
   ```

总结：

1. 线程默认为非守护线程。其实main线程也就是非守护线程。
2. 当仅运行的所有线程都是守护程序线程时，Java虚拟机将退出。 

注意：必须在线程`start()`启动之前调用`setDaemon()`方法。

