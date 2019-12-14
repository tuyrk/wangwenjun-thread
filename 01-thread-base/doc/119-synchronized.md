# 结合jconsole,jstack以及汇编指令认识synchronized关键字

上一节使用的synchronized到底是什么呢？

例子：在动物园检票的时候（前提：只有一台检票机，游客没有排队），每次只能有一位游客通过检票机进入动物园。游客类比多个线程，检票机类比synchronized。此时synchronized内部为单线程，**串行化运行程序代码**

目的：让synchronized代码块里边的代码串行化运行，让共享数据串行被操作。

代码：

```java
public class SynchronizedTest {
    // 代码规范：final命名的变量需要使用大写
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        // 任务
        Runnable runnable = () -> {
            synchronized (LOCK) {
                Thread.sleep(500_000);
            }
        };

        // 创建并启动线程
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        t1.start();t2.start();t3.start();
    }
}
```

## jconsole工具

1. 在终端输入命令：`jconsole`。打开Java监视和管理控制台，并选择名为SynchronizedTest的本地进程

   <img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g9996491lpj30z80u04am.jpg" alt="Java监视和管理控制台" style="zoom:40%;" />

2. 点击线程，查看Thread0、Thread1、Thread2

   <img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g999atar2vj30s40nyq8k.jpg" alt="线程Thread信息" style="zoom:50%;" />

   Thread-0：状态: TIMED_WAITING

   Thread-1：状态: java.lang.Object@20a5a1a4上的BLOCKED, 拥有者: Thread-0

   Thread-2：状态: java.lang.Object@20a5a1a4上的BLOCKED, 拥有者: Thread-0



## jstack工具

1. 在终端输入命令：`jps`

   ```shell
   1488 Launcher
   1489 SynchronizedTest
   1491 Jps
   500 
   570 RemoteMavenServer36
   814 KotlinCompileDaemon
   ```

2. 在终端输入命令：`jstack 1489`

   ```shell
   Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.221-b11 mixed mode):
   "Thread-2" #21 prio=5 os_prio=31 tid=0x00007f968c0aa800 nid=0x9f03 waiting for monitor entry [0x0000700008441000]
      java.lang.Thread.State: BLOCKED (on object monitor)
   	at com.tuyrk.chapter07.SynchronizedTest.lambda$main$0(SynchronizedTest.java:18)
   	- waiting to lock <0x000000076adb5948> (a java.lang.Object)
   	at com.tuyrk.chapter07.SynchronizedTest$$Lambda$1/500977346.run(Unknown Source)
   	at java.lang.Thread.run(Thread.java:748)
   
   "Thread-1" #20 prio=5 os_prio=31 tid=0x00007f968c0a9800 nid=0x6a03 waiting for monitor entry [0x000070000833e000]
      java.lang.Thread.State: BLOCKED (on object monitor)
   	at com.tuyrk.chapter07.SynchronizedTest.lambda$main$0(SynchronizedTest.java:18)
   	- waiting to lock <0x000000076adb5948> (a java.lang.Object)
   	at com.tuyrk.chapter07.SynchronizedTest$$Lambda$1/500977346.run(Unknown Source)
   	at java.lang.Thread.run(Thread.java:748)
   
   "Thread-0" #19 prio=5 os_prio=31 tid=0x00007f968c0a9000 nid=0x6903 waiting on condition [0x000070000823b000]
      java.lang.Thread.State: TIMED_WAITING (sleeping)
   	at java.lang.Thread.sleep(Native Method)
   	at com.tuyrk.chapter07.SynchronizedTest.lambda$main$0(SynchronizedTest.java:18)
   	- locked <0x000000076adb5948> (a java.lang.Object)
   	at com.tuyrk.chapter07.SynchronizedTest$$Lambda$1/500977346.run(Unknown Source)
   	at java.lang.Thread.run(Thread.java:748)
   
   "Monitor Ctrl-Break" #5 daemon prio=5 os_prio=31 tid=0x00007f9690127000 nid=0x3c03 runnable [0x000070000730e000]
      java.lang.Thread.State: RUNNABLE
   ```

   线程锁LOCK即为MONITOR（监视器）



## 汇编指令

1. 在终端输入命令：`javap -c 01-thread-base/target/classes/com/tuyrk/chapter07/TicketWindowRunnable.class`

   ```shell
   Compiled from "TicketWindowRunnable.java"
   public class com.tuyrk.chapter07.TicketWindowRunnable implements java.lang.Runnable {
     public com.tuyrk.chapter07.TicketWindowRunnable();
       Code:
          0: aload_0
          1: invokespecial #1                  // Method java/lang/Object."<init>":()V
          4: aload_0
          5: iconst_1
          6: putfield      #2                  // Field index:I
          9: aload_0
         10: new           #3                  // class java/lang/Object
         13: dup
         14: invokespecial #1                  // Method java/lang/Object."<init>":()V
         17: putfield      #4                  // Field MONITOR:Ljava/lang/Object;
         20: return
   
     public void run();
       Code:
          0: aload_0
          1: getfield      #4                  // Field MONITOR:Ljava/lang/Object;
          4: dup
          5: astore_1
          6: monitorenter
          7: aload_0
          8: getfield      #2                  // Field index:I
         11: sipush        500
         14: if_icmple     22
         17: aload_1
         18: monitorexit
         19: goto          93
         22: ldc2_w        #6                  // long 5l
         25: invokestatic  #8                  // Method java/lang/Thread.sleep:(J)V
         28: goto          36
         31: astore_2
         32: aload_2
         33: invokevirtual #10                 // Method java/lang/InterruptedException.printStackTrace:()V
         36: getstatic     #11                 // Field java/lang/System.out:Ljava/io/PrintStream;
         39: new           #12                 // class java/lang/StringBuilder
         42: dup
         43: invokespecial #13                 // Method java/lang/StringBuilder."<init>":()V
         46: invokestatic  #14                 // Method java/lang/Thread.currentThread:()Ljava/lang/Thread;
         49: invokevirtual #15                 // Method java/lang/Thread.getName:()Ljava/lang/String;
         52: invokevirtual #16                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
         55: ldc           #17                 // String  的号码是：
         57: invokevirtual #16                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
         60: aload_0
         61: dup
         62: getfield      #2                  // Field index:I
         65: dup_x1
         66: iconst_1
         67: iadd
         68: putfield      #2                  // Field index:I
         71: invokevirtual #18                 // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
         74: invokevirtual #19                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
         77: invokevirtual #20                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         80: aload_1
         81: monitorexit
         82: goto          90
         85: astore_3
         86: aload_1
         87: monitorexit
         88: aload_3
         89: athrow
         90: goto          0
         93: return
       Exception table:
          from    to  target type
             22    28    31   Class java/lang/InterruptedException
              7    19    85   any
             22    82    85   any
             85    88    85   any
   }
   ```

   getfield、putfield

   monitorenter、monitorexit



```shell
javap --help
```

```shell
用法: javap <options> <classes>
其中, 可能的选项包括:
  -help  --help  -?        输出此用法消息
  -version                 版本信息
  -v  -verbose             输出附加信息
  -l                       输出行号和本地变量表
  -public                  仅显示公共类和成员
  -protected               显示受保护的/公共类和成员
  -package                 显示程序包/受保护的/公共类
                           和成员 (默认)
  -p  -private             显示所有类和成员
  -c                       对代码进行反汇编
  -s                       输出内部类型签名
  -sysinfo                 显示正在处理的类的
                           系统信息 (路径, 大小, 日期, MD5 散列)
  -constants               显示最终常量
  -classpath <path>        指定查找用户类文件的位置
  -cp <path>               指定查找用户类文件的位置
  -bootclasspath <path>    覆盖引导类文件的位置
```



