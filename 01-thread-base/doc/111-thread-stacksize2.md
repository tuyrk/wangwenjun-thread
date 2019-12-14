# Thread构造函数StackSize详细讲解-续

> Thread(ThreadGroup group, Runnable target, String name, long stackSize)

默认的stackSize是多大呢？

- JDK源码中stackSize默认为0，代表该变量值被忽略。把传入的stackSize赋值给类变量，然后在代码中未找到引用，可能是在JVM中被引用了。



构造Thread的时候传入stackSize代表着该线程占用的stack的大小。如果没有指定stackSize的大小，默认是0，0代表着会忽略该参数，该参数会被JNI函数去使用。需要注意：该参数在某些平台（操作系统）可能无效。通过JVM参数显式设置：`-Xss10M`

