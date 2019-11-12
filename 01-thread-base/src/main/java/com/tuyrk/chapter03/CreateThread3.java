package com.tuyrk.chapter03;

/**
 * Java多线程与内存堆栈关系。虚拟机栈
 */
public class CreateThread3 {
    private int i = 0;
    private byte[] bytes = new byte[1024];
    private static int counter = 0;

    // JVM will create a thread named "main"
    public static void main(String[] args) {
        // create a JVM stack
        /*int j = 0;
        int[] arr = new int[1024];*/

        try {
            add(0);
        } catch (Error e) {
            e.printStackTrace();
            System.out.println(counter);
        }
    }

    private static void add(int i) {
        counter++;
        add(i + 1);
    }
}

// java.lang.StackOverflowError    21325