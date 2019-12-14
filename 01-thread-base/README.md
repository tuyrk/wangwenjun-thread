# 汪文君Java并发编程第一阶段（Java多线程基础知识）

## 简介
汪文君Java并发编程第一阶段（Java多线程基础知识）学习源码

## 目录

### 20191111：chapter01
多线程示例、模版类介绍

### 20191111：chapter02
多线程实现银行排队叫号（Thread、Runnable）、策略模式在Thread和Runnable使用

### 20191112：chapter03
Thread构造方法、ThreadGroup、多线程与虚拟机栈

### 20191113：chapter04
守护线程、线程ID、线程优先级

### 20191113：chapter05
join()

### 20191114：chapter06
interrupt()、isInterrupted()、interrupted()、优雅地停止线程、暴力关闭线程

### 20191125：chapter07
数据同步、synchronized代码块、synchronized方法、验证This锁、验证Class锁

### 20191130：chapter08
死锁案例

### 20191130：chapter09
线程通信（生产者-消费者,wait/notify）、多生产者-多消费者模型、wait和sleep的区别、多数据采集案例

### 20191130：chapter10
自定义显式锁、自锁自解、超时锁

### 20191207：chapter11
钩子程序、捕获线程异常、StackTrace

### 20191208：chapter12
线程组的创建、ThreadGroup API

### 20191209：chapter13
自定义线程池(任务队列)、拒绝策略、停止方法、其他因子（min、max、active）、自动扩充线程数量、闲时自动回收线程

## 课程目录
1. [课程大纲及主要内容介绍](doc/101-syllabus.md)
2. [简单介绍什么是线程](doc/102-what-is-thread.md)
3. [创建并启动线程](doc/103-create-thread.md)
4. [线程生命周期以及start方法源码剖析](doc/104-thread-lifecycle.md)
5. [采用多线程方式模拟银行排队叫号](doc/105-thread-bank-queue.md)
6. [用Runnable接口将线程的逻辑执行单元从控制中抽取出来](doc/106-runnable-bank-queue.md)
7. [策略模式在Thread和Runnable中的应用分析](doc/107-Strategy-in-thread.md)
8. [构造Thread对象你也许不知道的几件事](doc/108-construct-thread.md)
9. [多线程与JVM内存结构的关系，虚拟机栈实验](doc/109-thread-with-memory.md)
10. [Thread构造函数StackSize详细讲解](doc/110-thread-stacksize1.md)
11. [Thread构造函数StackSize详细讲解-续](doc/111-thread-stacksize2.md)
12. [Daemon线程的创建以及使用场景分析](doc/112-daemon.md)
13. [线程ID，优先级讲解](doc/113-thread-id-priority.md)
14. [Thread的join方法详细介绍，结合一个典型案例](doc/114-thread-join.md)
15. [Thread中断Interrupt方法详细讲解](doc/115-thread-interrupt.md)
16. [采用优雅的方式结束线程生命周期](doc/116-graceful-stop-thread.md)
17. [Thread API综合实战，编写ThreadService实现暴力结束线程的综合实战](doc/117-force-stop-thread.md)
18. [数据同步的引入与Synchronized的简单介绍](doc/118-sync-base.md)
19. [结合jconsole,jstack以及汇编指令认识synchronized关键字](doc/119-synchronized.md)
20. [同步代码块以及同步方法之间的区别和关系](doc/120-sync-codeblock-method.md)
21. [通过实验分析This锁的存在](doc/121-this-lock.md)
22. [通过实验分析Class锁的存在](doc/122-class-lock.md)
23. [多线程死锁分析，案例介绍](doc/123-deadlock-case.md)
24. [线程间通信快速入门，使用wait和notify进行线程间的数据通信](doc/124-wait-notify.md)
25. [多Produce多Consume之间的通讯导致出现程序假死的原因分析](doc/125-multi-prod-cons.md)
26. [多线程下的生产者消费者模型，以及详细介绍notifyAll方法](doc/126-multi-thread-notifyall.md)
27. [wait和sleep的本质区别是什么，深入分析（面试常见问题）](doc/127-wait-sleep.md)
28. [线程生产者消费者的综合实战结合Java8语法](doc/128-prod-cons-case.md)
29. [如何实现一个自己的显式锁Lock精讲上](doc/129-custom-lock.md)
30. [如何实现一个自己的显式锁Lock精讲下（让锁具备超时功能）](doc/130-custom-lock-timedout.md)
31. [如何给你的应用程序注入钩子程序，Linux下演示](doc/131-hook-processor.md)
32. [如何捕获线程运行期间的异常](doc/132-catch-thread-exception.md)
33. [ThreadGroup API介绍之一](doc/133-thread-group-api1.md)
34. [ThreadGroup API介绍之二](doc/134-thread-group-api2.md)
35. [线程池原理与自定义线程池](doc/135-thread-pool-theory.md)
36. [自定义个简单的线程池并且测试](doc/136-costum-thread-pool.md)
37. [给线程池增加拒绝策略以及停止方法](doc/137-thread-pool-discard.md)
38. [给线程池增加自动扩充线程数量，以及闲时自动回收的功能](doc/138-thread-pool-expan.md)
39. [课程结束，内容回顾，下季内容预告](doc/139-summary.md)