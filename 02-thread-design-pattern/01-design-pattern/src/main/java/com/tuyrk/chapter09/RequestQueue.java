package com.tuyrk.chapter09;

import java.util.LinkedList;

/**
 * Guarded Suspension设计模式
 * 请求队列
 *
 * @author tuyrk
 */
public class RequestQueue {
    /**
     * 存放请求的队列
     */
    private final LinkedList<Request> queue = new LinkedList<>();

    /**
     * 从队列获取请求Request
     *
     * @return 请求Request
     */
    public Request getRequest() {
        synchronized (queue) {
            while (queue.size() <= 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }

            return queue.removeFirst();
        }
    }

    /**
     * 向队列放入请求Request
     *
     * @param request 请求Request
     */
    public void putRequest(Request request) {
        synchronized (queue) {
            queue.addLast(request);
            queue.notifyAll();
        }
    }
}
