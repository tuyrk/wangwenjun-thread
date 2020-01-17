package com.tuyrk.chapter06;

import java.util.Random;

/**
 * 读写锁分离，写线程
 *
 * @author tuyrk
 */
public class WriteWorker extends Thread {
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private final SharedData data;
    private final String filler;

    private int index = 0;

    public WriteWorker(SharedData data, String filler) {
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char c = nextChat();
                data.write(c);
                Thread.sleep(RANDOM.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private char nextChat() {
        char c = filler.charAt(index);
        index++;
        if (index >= filler.length()) {
            index = 0;
        }
        return c;
    }
}
