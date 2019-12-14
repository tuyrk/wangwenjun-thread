# 多线程与JVM内存结构的关系，虚拟机栈实验

Java内存结构-简：

- 栈（非堆）
- 堆

栈存储基本数据类型，引用类型

堆存储对象



Java内存结构

栈：

- 方法区（线程共享）
- 虚拟机栈（线程私有）
  - 栈针（若干）：局部变量表、操作栈、动态链接库、方法出口/入口
- 本地方法区C++：执行引擎、本地库接口`.so`、本地方法
- 程序计数器

堆内存

- 对象实体（栈只保存变量的引用）



不断压栈操作将会导致栈溢出，从而程序崩溃并抛出`java.lang.StackOverflowError`

- main主线程

  ```java
  /**
   * Java多线程与内存堆栈关系。虚拟机栈
   */
  public class CreateThread3 {
      private static int counter = 0;
      
      // JVM will create a thread named "main"
      public static void main(String[] args) {
          // create a JVM stack
          try {
              add(0);
          } catch (Error e) {
              e.printStackTrace();// java.lang.StackOverflowError
              System.out.println(counter);// 21325
          }
      }
      
      private static void add(int i) {
          counter++;
          add(i + 1);
      }
  }
  ```

- 用户子线程

  ```java
  public class CreateThread4 {
      private static int counter = 0;
  
      public static void main(String[] args) {
          Thread t = new Thread(new Runnable() {
              @Override
              public void run() {
                  try {
                      add(0);
                  } catch (Error e) {
                      e.printStackTrace();// java.lang.StackOverflowError
                      System.out.println(counter);// 18455
                  }
              }
  
              private void add(int i) {
                  counter++;
                  add(i + 1);
              }
          });
  
          t.start();
      }
  }
  ```

  

通过stackSize构造方法改变线程的栈内存大小，避免抛出`java.lang.StackOverflowError`。~~但是JVM启动时创建的栈空间不变，由于线程的栈空间占用了很大虚拟机栈内存，所以在main主线程里，JVM能允许创建的线程数也会相应减少。~~

```java
public class CreateThread4 {
    private static int counter = 0;

    public static void main(String[] args) {
        Thread t = new Thread(null, new Runnable() {
            @Override
            public void run() {
                try {
                    add(0);
                } catch (Error e) {
                    e.printStackTrace();
                    System.out.println(counter);
                }
            }

            private void add(int i) {
                counter++;
                add(i + 1);
            }
        }, "stackSizeTest", 1 << 24);

        t.start();
    }
}
```



扩展：

1. 编写一个抛出`java.lang.StackOverflowError`的例子

2. 编写一个抛出`java.lang.OutOfMemoryError`的例子

3. JVM之--Java内存结构

   [JVM之--Java内存结构（第一篇）](https://blog.csdn.net/wangwenjun69/article/details/9747207)

   [JVM之---Java内存结构（第二篇）](https://blog.csdn.net/wangwenjun69/article/details/9751487)

   [JVM之---Java内存结构（第三篇）](https://blog.csdn.net/wangwenjun69/article/details/9774579)

   [JVM之---Java内存分配参数（第四篇）](https://blog.csdn.net/wangwenjun69/article/details/10044575)

   