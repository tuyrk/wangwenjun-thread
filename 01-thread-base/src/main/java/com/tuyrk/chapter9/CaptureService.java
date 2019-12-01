package com.tuyrk.chapter9;

import java.util.*;
import java.util.stream.Stream;

/**
 * 数据采集功能：利用多个线程采集多台服务器运行状态信息。
 * 当服务器数量较少时，可以采取一个线程采集一台服务器；
 * 但是服务器数量非常大时，将不可能采取这种方式。
 * 可以开启一定数量的线程采集完成后再采集其他服务器，即运行的线程始终保持着稳定数量。
 * <p>
 * 10台机器，运行5个线程
 *
 * @author tuyrk
 */
public class CaptureService {

    private static final LinkedList<Control> CONTROLS = new LinkedList<>();

    private static final int MAX_WORKER = 5;

    public static void main(String[] args) {
        List<Thread> worker = new ArrayList<>();
        Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10")
                .map(CaptureService::createCaptureService)
                .forEach(t -> {
                    t.start();
                    worker.add(t);
                });

        worker.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Optional.of("All of capture work finished.").ifPresent(System.out::println);
    }

    public static Thread createCaptureService(String name) {
        return new Thread(() -> {
            // Optional可以防止NPE空指针异常
            Optional.of("The worker [" + Thread.currentThread().getName() + "] BEGIN capture data.").ifPresent(System.out::println);
            synchronized (CONTROLS) {
                while (CONTROLS.size() > MAX_WORKER) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                CONTROLS.addLast(new Control());
            }

            Optional.of("The worker [" + Thread.currentThread().getName() + "] is WORKING...").ifPresent(System.out::println);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (CONTROLS) {
                Optional.of("The worker [" + Thread.currentThread().getName() + "] END capture data.").ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }
        }, name);

    }

    private static class Control {
    }
}
