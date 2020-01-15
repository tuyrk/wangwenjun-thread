package com.tuyrk.chapter05;

/**
 * 门。共享资源
 * SharedResource
 *
 * @author tuyrk
 */
public class Gate {
    private int counter = 0;
    private String name = "Nobody";
    private String address = "Nowhere";

    /**
     * 临界值
     * 添加this锁
     *
     * @param name    姓名
     * @param address 地址
     */
    public synchronized void pass(String name, String address) {
        /* race 竞争 */
        this.counter++;
        this.name = name;
        this.address = address;
        verify();
    }

    private void verify() {
        if (this.name.charAt(0) != this.address.charAt(0)) {
            System.out.println("***********BROKEN***********" + toString());
        }
    }

    @Override
    public String toString() {
        return "No." + counter + ":" + name + "," + address;
    }
}
