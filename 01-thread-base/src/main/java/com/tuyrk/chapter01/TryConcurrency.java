package com.tuyrk.chapter01;

/**
 * 读数据库的同时去做其他事情
 */
public class TryConcurrency {
    public static void main(String[] args) {
        // 1. 例1。顺序执行，不是交替执行
        /*readFromDataBase();
        writeDataToFile();*/

        // 2. 例2。顺序执行，不是交替执行
        /*for (int i = 0; i < 100; i++) {
            println("Task i=>" + i);
        }
        for (int j = 0; j < 100; j++) {
            println("Task j=>" + j);
        }*/

        // 3. 验证启动程序后JVM会自动创建main线程
        /*try {
            Thread.sleep(1000 * 100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // 4. 交替执行Task i和j
        /*Thread t1 = new Thread("Custom-Thread") {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    println("Task i=>" + i);
                    try {
                        Thread.sleep(1000 * 1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        for (int j = 0; j < 100; j++) {
            println("Task j=>" + j);
        }*/

        // 5. 交替执行读写操作
        /*new Thread("READ-Thread") {
            @Override
            public void run() {
                readFromDataBase();
            }
        }.start();
        new Thread("WRITE-Thread") {
            @Override
            public void run() {
                writeDataToFile();
            }
        }.start();*/

        // 6. 线程不能被start()两次以及上
        Thread t1 = new Thread("READ-Thread") {
            @Override
            public void run() {
                println(Thread.currentThread().getName());
                readFromDataBase();
            }
        };
        t1.start();
        // t1.start();
        // t1.run();
    }

    /**
     * 读数据
     */
    private static void readFromDataBase() {
        // read data from database and handle it
        try {
            println("Begin read data from db.");
            Thread.sleep(1000 * 1L);
            println("Read data done and start handle it.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println("The data handle finish and successfully.");
    }

    /**
     * 写数据
     */
    private static void writeDataToFile() {
        // write data to file
        try {
            println("Begin write data to file.");
            Thread.sleep(1000 * 1L);
            println("Write data done and start handle it.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println("The data handle finish and successfully.");
    }

    private static void println(String message) {
        System.out.println(message);
    }
}
