package com.tuyrk.chapter08;

/**
 * 异步调用
 *
 * @author tuyrk
 */
public class AsyncInvoker {
    public static void main(String[] args) throws InterruptedException {
        /*一、调用者调用任务执行线程，通过凭证轮询任务是否执行完成*/
        /*// 1. 调用者调用任务执行线程，并造成阻塞
        FutureService futureService = new FutureService();
        Future<String> future = futureService.submit(() -> {
            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISH";
        });

        // 2. 调用者线程阻塞，可以执行其他任务
        System.out.println("=====");
        System.out.println("do other thing...");
        Thread.sleep(1000);
        System.out.println("=====");

        // 3. 调用者通过Future获取任务执行结果
        String result = future.get();
        System.out.println(result);*/


        /*二、调用者调用任务执行线程，任务执行完成自动通知调用者*/
        // 1. 调用者调用任务执行线程，并造成阻塞
        FutureService futureService = new FutureService();
        futureService.submit(() -> {
            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISH";
        }, System.out::println);

        // 2. 调用者线程阻塞，可以执行其他任务
        System.out.println("===========");
        System.out.println(" do other thing...");
        Thread.sleep(1000);
        System.out.println("===========");
    }
}
