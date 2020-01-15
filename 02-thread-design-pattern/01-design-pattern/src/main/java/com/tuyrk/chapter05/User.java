package com.tuyrk.chapter05;

/**
 * 人。线程
 *
 * @author tuyrk
 */
public class User extends Thread {
    private final String myName;
    private final String myAddress;
    private final Gate gate;

    public User(String myName, String myAddress, Gate gate) {
        this.myName = myName;
        this.myAddress = myAddress;
        this.gate = gate;
    }

    @Override
    public void run() {
        System.out.println(myName + " BEGIN");
        // 人不断地通过这个门，也即线程不断调用共享资源
        while (true) {
            this.gate.pass(myName, myAddress);
        }
    }
}
