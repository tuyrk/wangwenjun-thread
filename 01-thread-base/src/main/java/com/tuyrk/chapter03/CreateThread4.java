package com.tuyrk.chapter03;

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

// java.lang.StackOverflowError    18455