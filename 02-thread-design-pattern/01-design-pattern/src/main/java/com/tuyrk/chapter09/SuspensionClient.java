package com.tuyrk.chapter09;

/**
 * Guarded Suspension设计模式
 * 测试
 *
 * @author tuyrk
 */
public class SuspensionClient {
    public static void main(String[] args) throws InterruptedException {
        RequestQueue queue = new RequestQueue();
        new ClientThread(queue, "Alex").start();
        ServerThread serverThread = new ServerThread(queue);
        serverThread.start();
        Thread.sleep(10_000L);
        serverThread.close();
    }
}
