package com.tuyrk.chapter03;

/**
 * 验证：
 * JVM启动时创建的栈空间不变，由于线程的栈空间占用了很大虚拟机栈内存，所以在main主线程里，JVM能允许创建的线程数也会相应减少。
 * <p>
 * 电脑崩溃，验证失败！结论不可信。
 */
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
                    while (true) {
                    *//*try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*//*
                    }
                }).start();*/
                // 2. 修改stackSize。
                new Thread(null, () -> {
                    // 增大栈针的宽度
                    // byte[] data = new byte[1024 * 1024 * 2];
                    while (true) {
                        /*try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }
                }, "stackSizeTest", 1 << 32).start();
            }
        } catch (Error e) {
            e.printStackTrace();
        }

        System.out.println("Total create thread nums => " + counter);
    }
}

// 1. 默认stackSize。
/*
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.tuyrk.chapter03.CreateThread5.main(CreateThread5.java:28)
Total create thread nums => 4060
* */

// 2. 修改stackSize。
/*
java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at com.tuyrk.chapter03.CreateThread5.main(CreateThread5.java:40)
Total create thread nums => 4060
 * */