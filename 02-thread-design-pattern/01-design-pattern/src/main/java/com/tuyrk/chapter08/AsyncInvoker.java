package com.tuyrk.chapter08;

/**
 * 异步调用
 *
 * @author tuyrk
 */
public class AsyncInvoker {
    public static void main(String[] args) throws InterruptedException {
        // 调用者调用任务执行线程，并造成阻塞
        FutureService futureService = new FutureService();
        Future<String> future = futureService.submit(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISH";
        });

        // 调用者线程阻塞，可以执行其他任务
        System.out.println("=====");
        System.out.println("do other thing...");
        Thread.sleep(1000);
        System.out.println("=====");

        // 调用者通过Future获取任务执行结果
        String result = future.get();
        System.out.println(result);
    }
}
