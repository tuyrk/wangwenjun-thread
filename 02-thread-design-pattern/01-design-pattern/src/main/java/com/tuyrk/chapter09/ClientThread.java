package com.tuyrk.chapter09;

import java.util.Random;

/**
 * Guarded Suspension设计模式
 * 发起请求的客户端线程
 *
 * @author tuyrk
 */
public class ClientThread extends Thread {
    /**
     * 存储请求的队列
     */
    private final RequestQueue queue;

    /**
     * 发送值
     */
    private final String sendValue;

    /**
     * 随机数变量
     */
    private final Random random;

    public ClientThread(RequestQueue queue, String sendValue) {
        this.queue = queue;
        this.sendValue = sendValue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Client -> request " + sendValue);
            queue.putRequest(new Request(sendValue));
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
