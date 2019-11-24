package com.tuyrk.chapter06;

/**
 * 通过小技巧实现暴力关闭线程
 * 思路：把任务执行线程定义为守护线程，当调用线程结束，则任务执行线程也会结束
 *
 * @author tuyrk
 */
public class ThreadService {
    // 调用线程
    private Thread executeThread;

    private boolean finished = false;

    /**
     * 执行线程
     *
     * @param task 任务
     */
    public void execute(Runnable task) {
        this.executeThread = new Thread(() -> {
            // 任务执行线程
            Thread runner = new Thread(task);
            runner.setDaemon(true);

            runner.start();

            try {
                runner.join();
                finished = true;
            } catch (InterruptedException e) {
            }
        });
        this.executeThread.start();
    }

    /**
     * 关闭线程
     *
     * @param mills 线程最长执行时间（单位：毫秒）
     */
    public void shutdown(long mills) {
        long currentTime = System.currentTimeMillis();
        while (!finished) {
            if (System.currentTimeMillis() - currentTime >= mills) {
                System.out.println("任务超时，需要结束线程！");
                executeThread.interrupt();
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("执行线程被打断！");
                break;
            }
        }

        finished = false;
    }
}
