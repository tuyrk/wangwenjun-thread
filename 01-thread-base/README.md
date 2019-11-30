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
线程通信（生产者-消费者,wait/notify）

## 课程目录
1. [课程大纲及主要内容介绍](doc/101-syllabus.md)
2. [简单介绍什么是线程](doc/102-what-is-thread.md)
3. [创建并启动线程](doc/103-create-thread.md)
4. [线程生命周期以及start方法源码剖析](doc/104-thread-lifecycle.md)
5. [采用多线程方式模拟银行排队叫号]()
6. [用Runnable接口将线程的逻辑执行单元从控制中抽取出来]()
7. [策略模式在Thread和Runnable中的应用分析]()
8. [构造Thread对象你也许不知道的几件事]()
9. [多线程与JVM内存结构的关系，虚拟机栈实验]()
10. [Thread构造函数StackSize详细讲解]()
11. [Thread构造函数StackSize详细讲解-续]()
12. [Daemon线程的创建以及使用场景分析]()
13. [线程ID，优先级讲解]()
14. [Thread的join方法详细介绍，结合一个典型案例]()
15. [Thread中断Interrupt方法详细讲解]()
16. [采用优雅的方式结束线程生命周期]()
17. [Thread API综合实战，编写ThreadService实现暴力结束线程的综合实战]()
18. [数据同步的引入与Synchronized的简单介绍]()
19. [结合jconsole,jstack以及汇编指令认识synchronized关键字]()
20. [同步代码块以及同步方法之间的区别和关系]()
21. [通过实验分析This锁的存在]()
22. [通过实验分析Class锁的存在]()
23. [多线程死锁分析，案例介绍]()
24. [线程间通信快速入门，使用wait和notify进行线程间的数据通信]()
25. [多Produce多Consume之间的通讯导致出现程序假死的原因分析]()
26. [多线程下的生产者消费者模型，以及详细介绍notifyAll方法]()
27. [wait和sleep的本质区别是什么，深入分析（面试常见问题）]()
28. [线程生产者消费者的综合实战结合Java8语法]()
29. [如何实现一个自己的显式锁Lock精讲上]()
30. [如何实现一个自己的显式锁Lock精讲下（让锁具备超时功能）]()
31. [如何给你的应用程序注入钩子程序，Linux下演示]()
32. [如何捕获线程运行期间的异常]()
33. [ThreadGroup API介绍之一]()
34. [ThreadGroup API介绍之二]()
35. [线程池原理与自定义线程池]()
36. [自定义个简单的线程池并且测试]()
37. [给线程池增加拒绝策略以及停止方法]()
38. [给线程池增加自动扩充线程数量，以及闲时自动回收的功能]()
39. [课程结束，内容回顾，下季内容预告]()