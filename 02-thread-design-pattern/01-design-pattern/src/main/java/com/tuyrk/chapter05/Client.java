package com.tuyrk.chapter05;

/**
 * 创造门和人，并且使人通过门
 * 线程不断调用共享资源
 *
 * @author tuyrk
 */
public class Client {
    public static void main(String[] args) {
        Gate gate = new Gate();
        User bj = new User("Baobao", "Beijing", gate);
        User sh = new User("Shanglao", "Shanghai", gate);
        User gz = new User("Guanglao", "Guangzhou", gate);
        bj.start();
        sh.start();
        gz.start();
    }
}
