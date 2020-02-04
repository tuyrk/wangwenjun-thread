package com.tuyrk.chapter09;

import java.util.Random;

/**
 * Guarded Suspension设计模式
 * 接收请求的客户端线程
 *
 * @author tuyrk
 */
public class ServerThread extends Thread {
    /**
     * 存储请求的队列
     */
    private final RequestQueue queue;

    /**
     * 随机数变量
     */
    private final Random random;

    /**
     * 控制线程是否结束
     */
    private volatile boolean flag = true;

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        // 三个地方可以中断：
        // 1. 执行完成时，修改flag
        while (flag) {
            // 2. getRequest陷入wait状态
            Request request = queue.getRequest();
            if (null == request) {
                System.out.println("Received the empty request.");
                continue;
            }
            System.out.println("Server -> " + request.getValue());
            try {
                // 3. sleep状态
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /**
     * 修改标志变量，结束线程
     */
    public void close() {
        this.flag = false;
        this.interrupt();
    }
}
