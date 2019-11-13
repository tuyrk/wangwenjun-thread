package com.tuyrk.chapter05;

import lombok.Data;

/**
 * 采集服务器节点的信息的例子。
 * <p>
 * 问题：多个线程如何得到唯一的采集结束时间？
 */
public class ThreadJoin3 {
    public static void main(String[] args) throws InterruptedException {
        long startTimestamp = System.currentTimeMillis();

        // 假设有三台机器，开启三个线程。
        Thread m1 = new Thread(new CaptureRunnable("M1", 10_000L));
        Thread m2 = new Thread(new CaptureRunnable("M2", 30_000L));
        Thread m3 = new Thread(new CaptureRunnable("M3", 15_000L));

        m1.start();
        m2.start();
        m3.start();
        m1.join();
        m2.join();
        m3.join();

        long endTimestamp = System.currentTimeMillis();

        System.out.printf("Save data begin timestamp is %s, end timestamp is %s\n", startTimestamp, endTimestamp);
        System.out.printf("Spend time is %s", endTimestamp - startTimestamp);
    }
}

/**
 * 采集服务器节点的任务。
 */
@Data
class CaptureRunnable implements Runnable {
    // 机器节点的名称
    private String machineName;
    // 采集花费时间
    private long spendTime;

    public CaptureRunnable(String machineName, long spendTime) {
        this.machineName = machineName;
        this.spendTime = spendTime;
    }

    @Override
    public void run() {
        // do the really capture data.
        try {
            Thread.sleep(spendTime);
            System.out.printf(machineName + " completed data capture at timestamp [%s] and successful.\n", System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return machineName + " finish.";
    }
}