# Thread构造函数StackSize详细讲解

不要过分压榨应用的性能，应用服务压力建议最大达到应用性能峰值的80%。超过80%可以通过横向扩展应用以减缓压力。现在分布式应用那么普遍。



验证：JVM启动时创建的栈空间不变，由于线程的栈空间占用了很大虚拟机栈内存，所以在main主线程里，JVM能允许创建的线程数也会相应减少。

```java
public class CreateThread5 {
    private static int counter = 0;

    public static void main(String[] args) {
        try {
            // 不断循环创建Thread
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                counter++;
                // 1. 默认stackSize。
                /*new Thread(() -> {
                    // 增大栈针的宽度
                    byte[] data = new byte[1024 * 1024 * 2];
                    while (true) {}
                }).start();*/
                // 2. 修改stackSize。
                new Thread(null, () -> {
                    // 增大栈针的宽度
                    byte[] data = new byte[1024 * 1024 * 2];
                    while (true) {}
                }, "stackSizeTest", 1 << 32).start();
            }
        } catch (Error e) {
            e.printStackTrace();
        }

        System.out.println("Total create thread nums => " + counter);
    }
}
```

输出结果：

1. 默认stackSize。

   ```shell
   java.lang.OutOfMemoryError: unable to create new native thread
   	at java.lang.Thread.start0(Native Method)
   	at java.lang.Thread.start(Thread.java:717)
   	at com.tuyrk.chapter03.CreateThread5.main(CreateThread5.java:28)
   Total create thread nums => 4060
   ```

2. stackSize = 1 << 32

   ```shell
   java.lang.OutOfMemoryError: unable to create new native thread
   	at java.lang.Thread.start0(Native Method)
   	at java.lang.Thread.start(Thread.java:717)
   	at com.tuyrk.chapter03.CreateThread5.main(CreateThread5.java:28)
   Total create thread nums => 4060
   ```

结论：**有待考证。暂认不成立**。